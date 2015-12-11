/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.HoldTransaction;
import com.model.Transaction;
import com.model.User;
import com.service.TransactionService;
import com.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/")
    public ModelAndView home() throws IOException {

        ModelAndView login = new ModelAndView("login");
        login.addObject("user", new User());
        return login;
    }

    @RequestMapping(value = "/dashboard")
    public ModelAndView homescreen() {
        
        double[] stats = transactionService.getTotalOutAndIn(Main.getCurrentUser());
        double outgoing = stats[0];
        double incoming = stats[1];
        
        ModelAndView dashboardView = new ModelAndView("dashboard");
        dashboardView.addObject("outgoing", outgoing);
        dashboardView.addObject("incoming", incoming);
        dashboardView.addObject("transactionList", transactionService.getRecentTransactions(Main.getCurrentUser()));
        dashboardView.addObject("user", Main.getCurrentUser());

        return dashboardView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") User user) throws IOException {
        User loginUser = userService.getUser(user.getAccountnumber());

        if (loginUser != null) {
            Main.setCurrentUser(loginUser);

            return checkRepeaters();
        }

        ModelAndView login = new ModelAndView("login");
        String message = "Onbekend rekeningnummer!";
        login.addObject("message", message);
        login.addObject("accountnumber", user.getAccountnumber());
        login.addObject("user", new User());

        return login;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage() throws IOException {
        ModelAndView registerView = new ModelAndView("register");
        registerView.addObject("user", new User());

        return registerView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("user") User user) throws IOException {
        if (userService.getUser(user.getAccountnumber()) == null) {
            user.setLastMonthCalculated(Calendar.getInstance().get(Calendar.MONTH));
            userService.addUser(user);
            return login(user);
        }
        ModelAndView registerView = new ModelAndView("register");
        String message = "Dit rekeningnummer wordt al gebruikt!";
        registerView.addObject("message", message);
        registerView.addObject("user", user);
        return registerView;
    }
    
    public ModelAndView checkRepeaters(){
        
        User currentUser = userService.getUser(Main.getAccountnumber());
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int calculatedMonth = currentUser.getLastMonthCalculated();
        List<HoldTransaction> holdings = new ArrayList<>();
        
        while(calculatedMonth != currentMonth){
            for(Transaction t : currentUser.getTransactions()){
                
                switch(t.getRepeating()){
                    case 12:
                        holdings.add(new HoldTransaction(t.getIncoming(), 
                                t.getOutgoing(), t.getDescription(), t.getDatum(), 
                                t.getCategory().getName(), currentUser));
                        break;
                    case 4:
                       // for(int i = calculated)
                        break;
                    case 2:
                        
                        break;
                    case 1:
                        
                        break;
                }
            }
            
            calculatedMonth++;
            if(calculatedMonth == 12) calculatedMonth = 0;
        }
        
        return new ModelAndView("redirect:/dashboard");
    }
}
