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
import com.model.User;
import com.service.CategoryService;
import com.service.TransactionService;
import com.service.UserService;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, this.categoryEditor);
    }

    @RequestMapping(value = "/list")
    public ModelAndView getList() {
        ModelAndView transList = new ModelAndView("transactions");

        User user = userService.getUser(Main.getAccountnumber());
        List<Transaction> tranList = transactionService.getLessTransactions(user, 100);
        List<Transaction> repeaters = transactionService.getRepeatingTransactions(user);
        List<Category> cats = userService.getUser(Main.getAccountnumber()).getCategories();
        List<Category> incoming = new ArrayList<>();
        List<Category> outgoing = new ArrayList<>();

        for (Category cat : cats) {
            if (cat.isIncoming()) {
                incoming.add(cat);
                continue;
            }
            outgoing.add(cat);
        }

        transList.addObject("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        transList.addObject("lastDate", transactionService.getLastDate(user));
        transList.addObject("user", user);
        transList.addObject("transactionList", tranList);
        transList.addObject("repeatingList", repeaters);
        transList.addObject("incomingCat", incoming);
        transList.addObject("outgoingCat", outgoing);
        transList.addObject("formTitle", "Nieuwe transactie");
        backupDate = null;

        return transList;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addTransaction(@RequestBody String json) {
        String returnJson = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(json);
            int id = Integer.parseInt(object.get("id").toString());
            Category category = categoryService.getCategory(Integer.parseInt(object.get("category").toString()));
            double incoming = Double.parseDouble(object.get("incoming").toString());
            double outgoing = Double.parseDouble(object.get("outgoing").toString());
            String description = object.get("description").toString();
            int repeating = Integer.parseInt(object.get("repeating").toString());
            String datum = object.get("date").toString();
            Transaction transaction = new Transaction(incoming, outgoing,
                    description, datum, repeating, category);
            transaction.setId(id);
            transaction.setUser(userService.getUser(Main.getAccountnumber()));

            boolean isNew;
            if (transactionService.getTransaction(transaction.getId()) != null) {
                String date = transaction.getDatum();
                String time = backupDate.split(" ")[1];
                date += " " + time;
                transaction.setDatum(date);
                transactionService.updateTransaction(transaction);
                backupDate = null;
                isNew = false;
            } else {
                String date = transaction.getDatum();
                Calendar cal = Calendar.getInstance();
                DateFormat timeForm = new SimpleDateFormat("kk:mm:ss");
                date += " " + timeForm.format(cal.getTime());

                transaction.setDatum(date);
                transactionService.addTransaction(transaction);
                isNew = true;
            }

            object = new JSONObject();
            object.put("add", isNew);
            object.put("id", transactionService.getNextId() - 1);
            object.put("repeating", (transaction.getRepeating() > 0));
            object.put("incoming", transaction.getIncoming());
            object.put("outgoing", transaction.getOutgoing());
            object.put("date", transaction.getDatum());
            object.put("category", transaction.getCategory().getName());
            object.put("balance", (userService.getUser(Main.getAccountnumber())).getBalance());
            return object.toJSONString();
        } catch (ParseException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnJson;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public @ResponseBody
    String editTransaction(@RequestParam("id") Integer id) {

        Transaction transaction = transactionService.getTransaction(id);
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("category", transaction.getCategory().getId());
        json.put("incoming", transaction.getIncoming());
        json.put("outgoing", transaction.getOutgoing());
        json.put("description", transaction.getDescription());
        json.put("repeating", transaction.getRepeating());
        json.put("date", transaction.getDateOnly());

        return json.toJSONString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    String deleteTransaction(@RequestParam("id") Integer id) {

        DecimalFormatSymbols symb = new DecimalFormatSymbols();
        symb.setDecimalSeparator('.');
        transactionService.deleteTransaction(id);
        DecimalFormat form = new DecimalFormat("0.00");
        form.setDecimalFormatSymbols(symb);

        return form.format((userService.getUser(Main.getAccountnumber()).getBalance()));
    }
}
