/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.Mortgage;
import com.service.MortgageService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Controller
@RequestMapping(value = "/mortgage")
public class MortgageController {
    
    private final String newMortgage = "Nieuwe Hypotheek";
    private final String editMortgage = "Hypotheek Details";
    
    @Autowired
    private MortgageService mortgageService;
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/list")
    public ModelAndView listMortgages(){
        ModelAndView view = new ModelAndView("mortgages");
        
        view.addObject("user", userService.getUser(Main.getAccountnumber()));
        view.addObject("formTitle", newMortgage);
        view.addObject("mortgage", new Mortgage());
        
        return view;
    }
    
    @RequestMapping(value = "/add")
    public ModelAndView addMortgage(@ModelAttribute("mortgage") Mortgage mortgage){
        mortgage.setUser(Main.getCurrentUser());
        
        if(mortgageService.getMortgage(mortgage.getId()) == null){
            mortgageService.addMortgage(mortgage);
        } else {
            mortgageService.updateMortgage(mortgage);
        }
        
        return new ModelAndView("redirect:/mortgage/list");
    }
    
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editMortgage(@PathVariable Integer id){
        
        ModelAndView view = new ModelAndView("mortgages");
        view.addObject("user", userService.getUser(Main.getAccountnumber()));
        view.addObject("formTitle", editMortgage);
        view.addObject("mortgage", mortgageService.getMortgage(id));
        
        return view;
    }
    
    @RequestMapping(value = "/delete/{id}")
    public ModelAndView deleteMortgage(@PathVariable Integer id){
        
        mortgageService.deleteMortgage(id);
        
        return new ModelAndView("redirect:/mortgage/list");
    }
}
