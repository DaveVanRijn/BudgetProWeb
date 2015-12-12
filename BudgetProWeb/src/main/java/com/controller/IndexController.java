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
import com.service.HoldTransactionService;
import com.service.TransactionService;
import com.service.UserService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @Autowired
    private HoldTransactionService holdService;

    @RequestMapping(value = "/")
    public ModelAndView home() throws IOException {

        ModelAndView login = new ModelAndView("login");
        login.addObject("user", new User());
        return login;
    }

    @RequestMapping(value = "/dashboard")
    public ModelAndView homescreen() {

        double[] stats = transactionService.getTotalOutAndIn(userService.getUser(Main.getAccountnumber()));
        double outgoing = stats[0];
        double incoming = stats[1];

        ModelAndView dashboardView = new ModelAndView("dashboard");
        dashboardView.addObject("outgoing", outgoing);
        dashboardView.addObject("incoming", incoming);
        dashboardView.addObject("transactionList", transactionService.getRecentTransactions(userService.getUser(Main.getAccountnumber())));
        dashboardView.addObject("user", userService.getUser(Main.getAccountnumber()));

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

    private ModelAndView checkRepeaters() {

        User currentUser = userService.getUser(Main.getAccountnumber());
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int calculatedMonth = currentUser.getLastMonthCalculated();
        List<HoldTransaction> holdings = new ArrayList<>();

        while (calculatedMonth != currentMonth) {
            for (Transaction t : currentUser.getTransactions()) {
                if (t.getRepeating() > 0) {
                    try {
                        DateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                        DateFormat form = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar tranDate = Calendar.getInstance();
                        tranDate.setTime(dateForm.parse(t.getDatum()));
                        Calendar newDate = (Calendar) tranDate.clone();
                        newDate.set(Calendar.SECOND, 0);
                        newDate.set(Calendar.HOUR_OF_DAY, 0);
                        newDate.set(Calendar.MINUTE, 0);
                        newDate.set(Calendar.MONTH, calculatedMonth + 1);
                        Calendar checkDate = Calendar.getInstance();
                        checkDate.set(Calendar.MONTH, calculatedMonth);
                        checkDate.set(Calendar.DAY_OF_MONTH, checkDate.getActualMaximum(Calendar.DAY_OF_MONTH));

                        if (tranDate.getTime().before(checkDate.getTime())) {
                            switch (t.getRepeating()) {
                                case 12:
                                    holdings.add(new HoldTransaction(t.getIncoming(),
                                            t.getOutgoing(), t.getDescription(), dateForm.format(newDate.getTime()),
                                            t.getCategory(), currentUser));
                                    break;
                                case 4:
                                    int i = tranDate.get(Calendar.MONTH);
                                    for (int j = 0; j < 4; j++) {
                                        if (i == calculatedMonth) {
                                            holdings.add(new HoldTransaction(t.getIncoming(),
                                                    t.getOutgoing(), t.getDescription(), dateForm.format(newDate.getTime()),
                                                    t.getCategory(), currentUser));
                                            break;
                                        }
                                        i += 3;
                                        if (i > 11) {
                                            i -= 11;
                                        }
                                    }
                                    break;
                                case 2:
                                    i = tranDate.get(Calendar.MONTH);
                                    for (int j = 0; j < 2; j++) {
                                        if (i == calculatedMonth) {
                                            holdings.add(new HoldTransaction(t.getIncoming(),
                                                    t.getOutgoing(), t.getDescription(), dateForm.format(newDate),
                                                    t.getCategory(), currentUser));
                                            break;
                                        }
                                        i += 6;
                                        if (i > 11) {
                                            i -= 11;
                                        }
                                    }
                                    break;
                                case 1:
                                    if (calculatedMonth == tranDate.get(Calendar.MONTH)) {
                                        holdings.add(new HoldTransaction(t.getIncoming(),
                                                t.getOutgoing(), t.getDescription(), dateForm.format(newDate.getTime()),
                                                t.getCategory(), currentUser));
                                    }
                                    break;
                            }
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            calculatedMonth++;
            if (calculatedMonth == 12) {
                calculatedMonth = 0;
            }
            currentUser.setLastMonthCalculated(calculatedMonth);
            userService.updateUser(currentUser);
        }

        if (!holdings.isEmpty()) {
            holdService.saveAllHoldTransactions(holdings);
        }

        return processHoldings();
    }

    private ModelAndView processHoldings() {

        boolean lastDayInMonth = false;
        DateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        User currentUser = userService.getUser(Main.getAccountnumber());
        List<HoldTransaction> holdings = currentUser.getHoldings();
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (currentDay == Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) {
            lastDayInMonth = true;
        }

        try {
            for (HoldTransaction h : holdings) {
                if (lastDayInMonth) {
                    transactionService.addTransaction(transactionService.holdToTransaction(h));
                    holdService.deleteHoldTransaction(h.getId());
                } else {
                    Calendar date = Calendar.getInstance();
                    date.setTime(dateForm.parse(h.getDatum()));
                    if (date.get(Calendar.DAY_OF_MONTH) <= currentDay) {
                        transactionService.addTransaction(transactionService.holdToTransaction(h));
                        holdService.deleteHoldTransaction(h.getId());
                    }
                }
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return new ModelAndView("redirect:/dashboard");
    }
}
