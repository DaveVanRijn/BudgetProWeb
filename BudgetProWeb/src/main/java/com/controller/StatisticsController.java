/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.CatStat;
import System.Main;
import com.model.Category;
import com.model.Transaction;
import com.model.User;
import com.service.CategoryService;
import com.service.TransactionService;
import com.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ModelAndView statisticsPage() {
        ModelAndView view = new ModelAndView("statistics");
        User user = userService.getUser(Main.getAccountnumber());
        Map<Category, Double> map = categoryService.getCategoryStats();
        double[] totals = transactionService.getTotalOutAndIn(user);
        double out = totals[0];
        double in = totals[1];
        List<CatStat> incoming = new ArrayList<>();
        List<CatStat> outgoing = new ArrayList<>();
        StringBuilder inName = new StringBuilder();
        StringBuilder outName = new StringBuilder();
        StringBuilder inValue = new StringBuilder();
        StringBuilder outValue = new StringBuilder();

        for (Map.Entry<Category, Double> entry : map.entrySet()) {
            if (entry.getKey().isIncoming()) {
                incoming.add(new CatStat(entry.getKey(), entry.getValue()));
                if(inName.length() > 0){
                    inName.append(",");
                }
                if(inValue.length() > 0){
                    inValue.append(",");
                }
                inName.append("\"");
                inName.append(entry.getKey().getName());
                inName.append("\"");
                inValue.append(Double.toString(entry.getValue()));
            } else {
                outgoing.add(new CatStat(entry.getKey(), entry.getValue()));
                if(outName.length() > 0){
                    outName.append(",");
                }
                if(outValue.length() > 0){
                    outValue.append(",");
                }
                outName.append("\"");
                outName.append(entry.getKey().getName());
                outName.append("\"");
                outValue.append(entry.getValue());
            }
        }
        
        Collections.sort(incoming, Collections.reverseOrder());
        Collections.sort(outgoing, Collections.reverseOrder());

        view.addObject("lastDate", transactionService.getLastDate(user));
        view.addObject("inName", inName.toString());
        view.addObject("inValue", inValue.toString());
        view.addObject("outName", outName.toString());
        view.addObject("outValue", outValue.toString());
        view.addObject("incoming", incoming);
        view.addObject("outgoing", outgoing);
        view.addObject("totalIn", in);
        view.addObject("totalOut", out);
        view.addObject("user", user);

        return view;
    }
    
    @RequestMapping(value = "/details/{id}")
    public ModelAndView details(@PathVariable("id") int id){
        Category cat = categoryService.getCategory(id);
        List<Transaction> transactions = transactionService.getByCategory(cat, userService.getUser(Main.getAccountnumber()));
        
        ModelAndView view = statisticsPage();
        view.addObject("category", cat);
        view.addObject("transactions", transactions);
        
        return view;
    }
}
