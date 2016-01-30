/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import com.model.Category;
import com.model.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Main {
    
    private static User currentUser;
    private static Date lastDate;
    
    public static User getCurrentUser(){
        return currentUser;
    }
    
    public static void setCurrentUser(User user){
        currentUser = user;
    }
    
    public static void resetCurrentUser(){
        currentUser = null;
    }
    
    public static long getAccountnumber(){
        return getCurrentUser().getAccountnumber();
    }
    
    public static Category getCategory(String name, boolean incoming){
        for(Category c : currentUser.getCategories()){
            if(c.getName().equals(name) && c.isIncoming() == incoming){
                return c;
            }
        }
        return null;
    }
    
    public static void setLastDate(String date){
        try {
            DateFormat form = new SimpleDateFormat("dd-MM-yyyy");
            lastDate = form.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getLastDate(){
        DateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        return form.format(lastDate);
    }
    
}




