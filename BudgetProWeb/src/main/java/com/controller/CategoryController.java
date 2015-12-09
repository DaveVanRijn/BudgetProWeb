/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.Category;
import com.service.CategoryService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/category")
public class CategoryController {

    private final String newCategory = "Nieuwe Categorie";
    private final String editCategory = "Categorie Wijzigen";

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list")
    public ModelAndView categoryList() {
        ModelAndView view = new ModelAndView("categories");

        view.addObject("user", userService.getUser(Main.getAccountnumber()));
        view.addObject("formTitle", newCategory);
        view.addObject("category", new Category());

        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addCategory(@ModelAttribute("category") Category category) {
        category.setUser(Main.getCurrentUser());

        if (categoryService.getCategory(category.getId()) == null) {
            if (categoryService.getCategory(category.getName(), category.isIncoming()) == null) {
                categoryService.addCategory(category);
            } else {
                ModelAndView view = new ModelAndView("categories");
                view.addObject("user", userService.getUser(Main.getAccountnumber()));
                view.addObject("formTitle", newCategory);
                view.addObject("category", category);
                view.addObject("message", "Er bestaat al een categorie met deze naam!");
                return view;
            }
        } else {
            if (categoryService.getCategory(category.getName(), category.isIncoming()) == null) {
                categoryService.updateCategory(category);
            } else {
                ModelAndView view = new ModelAndView("categories");
                view.addObject("user", userService.getUser(Main.getAccountnumber()));
                view.addObject("formTitle", editCategory);
                view.addObject("category", category);
                view.addObject("message", "Er bestaat al een categorie met deze naam!");
                return view;
            }
        }

        return new ModelAndView("redirect:/category/list");
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editCategory(@PathVariable Integer id) {

        ModelAndView view = new ModelAndView("categories");
        
        Category category = categoryService.getCategory(id);
        view.addObject("user", userService.getUser(Main.getAccountnumber()));
        view.addObject("formTitle", editCategory);
        view.addObject("category", category);

        return view;
    }

    @RequestMapping(value = "delete/{id}")
    public ModelAndView deleteCategory(@PathVariable Integer id) {

        userService.getUser(Main.getAccountnumber()).removeCategory(categoryService.getCategory(id));
        categoryService.deleteCategory(id);

        return new ModelAndView("redirect:/category/list");
    }
}
