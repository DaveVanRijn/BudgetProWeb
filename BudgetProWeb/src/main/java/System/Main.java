/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import com.model.User;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Main {
    
    private static User currentUser;
    private static long currentAccountnumber;
    
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
        return currentAccountnumber;
    }
}




