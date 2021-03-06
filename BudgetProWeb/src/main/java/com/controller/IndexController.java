/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.Category;
import com.model.HoldTransaction;
import com.model.Mortgage;
import com.model.Transaction;
import com.model.User;
import com.service.HoldTransactionService;
import com.service.TransactionService;
import com.service.UserService;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private HoldTransactionService holdService;

    @RequestMapping(value = "/")
    public ModelAndView home() {

        ModelAndView login = new ModelAndView("login");
        login.addObject("user", new User());
        return login;
    }

    @RequestMapping(value = "/dashboard")
    public ModelAndView homescreen() {

        User user = userService.getUser(Main.getAccountnumber());
        double[] stats = transactionService.getTotalOutAndIn(user);
        double outgoing = stats[0];
        double incoming = stats[1];

        ModelAndView dashboardView = new ModelAndView("dashboard");
        dashboardView.addObject("lastDate", transactionService.getLastDate(user));
        dashboardView.addObject("outgoing", outgoing);
        dashboardView.addObject("incoming", incoming);
        dashboardView.addObject("transactionList", transactionService.getRecentTransactions(user));
        dashboardView.addObject("user", user);

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
        int lastCalculatedMonth = currentUser.getLastMonthCalculated();
        List<HoldTransaction> holdings = new ArrayList<>();

        while (lastCalculatedMonth != currentMonth) {
            int calculatedMonth = lastCalculatedMonth + 1;
            if (calculatedMonth == 13) {
                calculatedMonth = 1;
            }
            DateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Transaction t : currentUser.getTransactions()) {
                if (t.getRepeating() > 0) {
                    try {
                        Calendar tranDate = Calendar.getInstance();
                        tranDate.setTime(dateForm.parse(t.getDatum()));
                        Calendar newDate = (Calendar) tranDate.clone();
                        newDate.set(Calendar.SECOND, 0);
                        newDate.set(Calendar.HOUR_OF_DAY, 0);
                        newDate.set(Calendar.MINUTE, 0);
                        newDate.set(Calendar.MONTH, calculatedMonth);
                        Calendar checkDate = Calendar.getInstance();
                        checkDate.set(Calendar.MONTH, calculatedMonth);
                        checkDate.set(Calendar.DAY_OF_MONTH, checkDate.getActualMaximum(Calendar.DAY_OF_MONTH));

                        if (tranDate.getTime().before(checkDate.getTime())) {
                            String description = t.getDescription();
                            description += "\nHerhaling maand " + (calculatedMonth + 1);
                            switch (t.getRepeating()) {
                                case 12:
                                    holdings.add(new HoldTransaction(t.getIncoming(),
                                            t.getOutgoing(), description, dateForm.format(newDate.getTime()),
                                            t.getCategory(), currentUser));
                                    break;
                                case 4:
                                    int i = tranDate.get(Calendar.MONTH);
                                    for (int j = 0; j < 4; j++) {
                                        if (i == calculatedMonth) {
                                            holdings.add(new HoldTransaction(t.getIncoming(),
                                                    t.getOutgoing(), description, dateForm.format(newDate.getTime()),
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
                                                    t.getOutgoing(), description, dateForm.format(newDate),
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
                                                t.getOutgoing(), description, dateForm.format(newDate.getTime()),
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
            for (Mortgage m : userService.getUser(Main.getAccountnumber()).getMortgages()) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.MONTH, calculatedMonth);
                String description = m.getDescription();
                Category cat = Main.getCategory("Hypotheek", false);
                double interest;
                double total;
                double redemption;
                double premie;
                switch (m.getKind()) {
                    case "Aflossingsvrij":
                        //Interest
                        interest = m.calcInterest();
                        description += "\nRente hypotheek '" + m.getName() + "'"
                                + "\nMaand " + (calculatedMonth);
                        holdings.add(new HoldTransaction(0, interest, description,
                                dateForm.format(cal.getTime()), cat, currentUser));
                        break;
                    case "Annuiteit":
                        //Annuity
                        description += "\nAnnuiteit hypotheek '" + m.getName() + "'"
                                + "\nMaand " + (calculatedMonth);
                        total = m.getAnnuity();
                        holdings.add(new HoldTransaction(0, total, description,
                                dateForm.format(cal.getTime()), cat, currentUser));
                        break;
                    case "Lineair":
//                        //Redemptiom
//                        redemption = m.getRedemption();
//                        String temp = description + "\nAflossing hypotheek '" + m.getName() + "'"
//                                + "\nMaand " + calculatedMonth + 1;
//                        holdings.add(new HoldTransaction(0, redemption, temp,
//                                dateForm.format(cal.getTime()), cat, currentUser));
//                        //Interest
//                        description += "\nRente hypotheek '" + m.getName() + "'"
//                                + "\nMaand " + calculatedMonth + 1;
//                        interest = m.calcInterest();
//                        holdings.add(new HoldTransaction(0, interest, description,
//                                dateForm.format(cal.getTime()), cat, currentUser));

                        //Redemption + Interest
                        interest = m.calcInterest();
                        redemption = m.getRedemption();
                        total = setDecimal(interest + redemption);
                        description += "Rente + Aflossing '" + m.getName() + "'"
                                + "\nMaand " + (calculatedMonth)
                                + "\nRente: \u20ac" + interest
                                + "\nAflossing: \u20ac" + redemption;
                        holdings.add(new HoldTransaction(0, total, description,
                                dateForm.format(cal.getTime()), cat, currentUser));
                        break;
                    case "Spaar":
                        total = m.getAnnuity();
                        //Interest 
                        interest = m.calcInterest();
                        //Premie
                        premie = setDecimal(total - interest);
                        description += "\nPremie hypotheek '" + m.getName() + "'"
                                + "\nMaand " + (calculatedMonth)
                                + "\nRente: \u20ac" + interest
                                + "\nPremie: \u20ac" + premie;
                        holdings.add(new HoldTransaction(0, total, description,
                                dateForm.format(cal.getTime()), cat, currentUser));
                        break;
                }
            }

            lastCalculatedMonth++;
            if (lastCalculatedMonth == 12) {
                lastCalculatedMonth = 0;
            }
            currentUser.setLastMonthCalculated(lastCalculatedMonth);
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

    private double setDecimal(double number) {
        try {
            DecimalFormat deciForm = new DecimalFormat("0.00");
            deciForm.setRoundingMode(RoundingMode.HALF_UP);
            deciForm.parse(Double.toString(number));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return number;
    }
}
