/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.service.TransactionService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/start")
    public ModelAndView dashboard() {
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
}
