/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Controller
public class IndexController {
    
    @RequestMapping(value = "/")
    public ModelAndView home() throws IOException {
        
        ModelAndView homeView = new ModelAndView("index");
        return homeView;
    }
}
