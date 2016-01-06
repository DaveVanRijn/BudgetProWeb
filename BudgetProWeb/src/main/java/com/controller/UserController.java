/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.User;
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
@RequestMapping(value = "/user")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView userPage(){
        ModelAndView view = new ModelAndView("user");
        
        view.addObject("user", userService.getUser(Main.getAccountnumber()));
        return view;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user") User user){
        ModelAndView view = new ModelAndView("redirect:/user/");
        
        userService.updateUser(user);
        Main.setCurrentUser(userService.getUser(user.getAccountnumber()));
        view.addObject("user", userService.getUser(Main.getAccountnumber()));
        return view;
    }
    
    @RequestMapping(value = "/delete/{id}")
    public ModelAndView deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        
        return logout();
    }
    
    @RequestMapping(value = "/logout")
    public ModelAndView logout(){
        Main.setCurrentUser(null);
        
        return new ModelAndView("redirect:/");
    }
}
