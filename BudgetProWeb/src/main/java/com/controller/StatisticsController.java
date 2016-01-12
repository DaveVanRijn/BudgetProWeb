/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.Category;
import com.model.Transaction;
import com.model.User;
import com.service.CategoryService;
import com.service.TransactionService;
import com.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {
    
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/page")
    public ModelAndView statistiscsPage(){
        ModelAndView view = new ModelAndView("statistics");
        List<List<Transaction>> lists = new ArrayList<>();
        User current = userService.getUser(Main.getAccountnumber());
        for(Category c : current.getCategories()) {
            lists.add(transactionService.getByCategory(c));
        }
        
        return null;
    }
}
