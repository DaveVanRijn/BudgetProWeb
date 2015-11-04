/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Entity(name = "user")
public class User implements Serializable{
    
    @Id
    private long accountnumber;
    private String firstname;
    private String infix;
    private String lastname;
    private double balance;
    private int lastMonthCalculated;
    
    public User(){
        super();
    }
    
    public User(String firstname, String infix, String lastname, 
            int accountnumber, double balance){
        this.firstname = firstname;
        this.infix = infix;
        this.lastname = lastname;
        this.accountnumber = accountnumber;
        this.balance = balance;
        lastMonthCalculated = Calendar.getInstance().get(Calendar.MONTH);
    }
    
    public String getFirstname(){
        return firstname;
    }
    
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    
    public String getInfix(){
        return infix;
    }
    
    public void setInfix(String infix){
        this.infix = infix;
    }
    
    public String getLastname(){
        return lastname;
    }
    
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    
    public long getAccountnumber(){
        return accountnumber;
    }
    
    public void setAccountnumber(long accountnumber){
        this.accountnumber = accountnumber;
    }
    
    public double getBalance(){
        return balance;
    }
    
    public void setBalance(double balance){
        this.balance = balance;
    }
    
    public int getLastMonthCalculated(){
        return lastMonthCalculated;
    }
    
    public void setLastMonthCalculated(int lastMonthCalculated){
        this.lastMonthCalculated = lastMonthCalculated;
    }
}
