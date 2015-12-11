/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.editor.CategoryEditor;
import com.model.Category;
import com.model.Transaction;
import com.service.TransactionService;
import com.service.UserService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Controller
@RequestMapping(value = "/transaction")
public class TransactionController {

    private static String backupDate;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CategoryEditor categoryEditor;
    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, this.categoryEditor);
    }

    @RequestMapping(value = "/list")
    public ModelAndView getList() {
        ModelAndView transList = new ModelAndView("transactions");

        List<Category> cats = Main.getCurrentUser().getCategories();
        List<Category> incoming = new ArrayList<>();
        List<Category> outgoing = new ArrayList<>();

        for (Category cat : cats) {
            if (cat.isIncoming()) {
                incoming.add(cat);
                continue;
            }
            outgoing.add(cat);
        }

        transList.addObject("user", Main.getCurrentUser());
        transList.addObject("incomingCat", incoming);
        transList.addObject("outgoingCat", outgoing);
        transList.addObject("formTitle", "Nieuwe transactie");
        transList.addObject("transaction", new Transaction());
        backupDate = null;

        return transList;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addTransaction(@ModelAttribute("transaction") Transaction transaction) {
        transaction.setUser(Main.getCurrentUser());

        if (transactionService.getTransaction(transaction.getId()) != null) {
            String date = transaction.getDatum();
            String time = backupDate.split(" ")[1];
            date += " " + time;
            transaction.setDatum(date);
            transactionService.updateTransaction(transaction);
            backupDate = null;
        } else {
            String date = transaction.getDatum();
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            date += " " + Integer.toString(hour) + ":" + Integer.toString(minute)
                    + ":" + Integer.toString(second);

            transaction.setDatum(date);
            transactionService.addTransaction(transaction);
        }

        return new ModelAndView("redirect:/transaction/list");
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editTransaction(@PathVariable Integer id) {

        ModelAndView transList = new ModelAndView("transactions");
        Transaction transaction = transactionService.getTransaction(id);
        backupDate = transaction.getDatum();
        transaction.setDatum(backupDate.split(" ")[0]);

        List<Category> cats = Main.getCurrentUser().getCategories();
        List<Category> incoming = new ArrayList<>();
        List<Category> outgoing = new ArrayList<>();

        for (Category cat : cats) {
            if (cat.isIncoming()) {
                incoming.add(cat);
                continue;
            }
            outgoing.add(cat);
        }

        transList.addObject("user", Main.getCurrentUser());
        transList.addObject("incomingCat", incoming);
        transList.addObject("outgoingCat", outgoing);
        transList.addObject("formTitle", "Transactie Details");
        transList.addObject("transaction", transaction);

        return transList;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView deleteTransaction(@PathVariable Integer id) {

        transactionService.deleteTransaction(id);

        return new ModelAndView("redirect:/transaction/list");
    }
}
