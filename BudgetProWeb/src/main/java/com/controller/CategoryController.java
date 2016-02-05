/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.Category;
import com.model.User;
import com.service.CategoryService;
import com.service.TransactionService;
import com.service.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/category")
public class CategoryController {

    private final String newCategory = "Nieuwe Categorie";
    private final String editCategory = "Categorie Wijzigen";

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/list")
    public ModelAndView categoryList() {
        ModelAndView view = new ModelAndView("categories");
        User user = userService.getUser(Main.getAccountnumber());
        view.addObject("lastDate", transactionService.getLastDate(user));
        view.addObject("user", user);
        view.addObject("formTitle", newCategory);
        view.addObject("category", new Category());

        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addCategory(@RequestBody String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        int id = Integer.parseInt(obj.get("id").toString());
        String name = obj.get("name").toString();
        boolean incoming = (boolean) obj.get("incoming");
        boolean isNew;

        obj = new JSONObject();
        if (categoryService.getCategory(id) == null) {
            if (categoryService.getCategory(name, incoming, userService.getUser(Main.getAccountnumber())) == null) {
                Category cat = new Category(name, incoming);
                cat.setUser(userService.getUser(Main.getAccountnumber()));
                categoryService.addCategory(cat);
                obj.put("id", categoryService.getNextId() - 1);
                isNew = true;
            } else {
                return null;
            }
        } else {
            Category cat = categoryService.getCategory(id);
            cat.setName(name);
            cat.setIncoming(incoming);
            categoryService.updateCategory(cat);
            isNew = false;
            obj.put("id", id);
        }
        obj.put("isNew", isNew);
        obj.put("name", name);
        obj.put("incoming", incoming);
        return obj.toJSONString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody void deleteCategory(@RequestParam("id") int id) {
        categoryService.deleteCategory(id);
    }
    
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public @ResponseBody String getDetails(@RequestParam("id") int id){
        Category cat = categoryService.getCategory(id);
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", cat.getName());
        json.put("incoming", cat.isIncoming());
        
        return json.toJSONString();
    }
}
