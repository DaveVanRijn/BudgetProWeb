/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import System.Main;
import com.model.Mortgage;
import com.model.User;
import com.service.MortgageService;
import com.service.TransactionService;
import com.service.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/mortgage")
public class MortgageController {
    
    private final String newMortgage = "Nieuwe Hypotheek";
    private final String editMortgage = "Hypotheek Details";
    
    @Autowired
    private MortgageService mortgageService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    
    @RequestMapping(value = "/list")
    public ModelAndView listMortgages(){
        ModelAndView view = new ModelAndView("mortgages");
        User user = userService.getUser(Main.getAccountnumber());
        view.addObject("lastDate", transactionService.getLastDate(user));
        view.addObject("user", user);
        view.addObject("formTitle", newMortgage);
        view.addObject("mortgage", new Mortgage());
        
        return view;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String add(@RequestBody String data){
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(data);
            
            int id = Integer.parseInt(json.get("id").toString());
            String name = json.get("name").toString();
            String kind = json.get("kind").toString();
            String description = json.get("description").toString();
            double redemption = getDouble(json.get("redemption").toString());
            double residual = getDouble(json.get("residual").toString());
            double interest = getDouble(json.get("interest").toString());
            double annuïty = getDouble(json.get("annu").toString());
            
            Mortgage mortgage = new Mortgage(kind, name, redemption, residual, interest, description, annuïty);
            mortgage.setUser(userService.getUser(Main.getAccountnumber()));
            mortgage.setId(id);
            
            boolean isNew;
            json = new JSONObject();
            if(mortgageService.getMortgage(id) == null){
                mortgageService.addMortgage(mortgage);
                json.put("id", mortgageService.getNextId() - 1);
                isNew = true;
            } else {
                mortgageService.updateMortgage(mortgage);
                json.put("id", mortgage.getId());
                isNew = false;
            }
            
            json.put("isNew", isNew);
            json.put("name", mortgage.getName());
            json.put("kind", mortgage.getKind());
            json.put("residual", mortgage.getResidualDebt());
            json.put("interest", mortgage.getInterest());
            
            return json.toJSONString();
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void delete(@RequestParam("id") int id){
        mortgageService.deleteMortgage(id);
    }
    
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public @ResponseBody String details(@RequestParam("id") int id){
        Mortgage mortgage = mortgageService.getMortgage(id);
        JSONObject json = new JSONObject();
        json.put("id", mortgage.getId());
        json.put("name", mortgage.getName());
        json.put("kind", mortgage.getKind());
        json.put("description", mortgage.getDescription());
        json.put("redemption", mortgage.getRedemption());
        json.put("residual", mortgage.getResidualDebt());
        json.put("interest", mortgage.getInterest());
        json.put("annuïty", mortgage.getAnnuity());
        
        return json.toJSONString();
    }
    
    private Double getDouble(String string){
        if(string.equals("")){
            return 0.0;
        }
        return Double.parseDouble(string);
    }
}
