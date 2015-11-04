/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

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
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    
    @RequestMapping(value = "/start")
    public ModelAndView dashboard(){
        double[] stats = transactionService.getTotalOutAndIn();
        
        double outgoing = stats[0];
        double incoming = stats[1];
        
        ModelAndView dashboardView = new ModelAndView();
        dashboardView.addObject("outgoing", outgoing);
        dashboardView.addObject("incoming", incoming);
        
        return dashboardView;
        
    }
}
