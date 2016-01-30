/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Entity(name = "transaction")
public class Transaction implements Serializable{
    
    @Id
    @GenericGenerator(name = "transaction", strategy = "increment")
    @GeneratedValue(generator = "transaction")
    private int id;
    private double incoming;
    private double outgoing;
    private String description;
    private String datum;
    private int repeating;
    
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private User user;
    
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Category category;

    public Transaction() {
        super();
        DateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        this.datum = dateForm.format(date);
    }
    
    public Transaction(int id){
        super();
        this.id = id;
        DateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        this.datum = dateForm.format(date); 
    }

    public Transaction(double incoming, double outgoing, String description, 
            String datum, int repeating, Category category) {
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.description = description;
        this.datum = datum;
        this.repeating = repeating;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIncoming() {
        return setDecimal(incoming);
    }

    public void setIncoming(double incoming) {
        this.incoming = incoming;
    }

    public double getOutgoing() {
        return setDecimal(outgoing);
    }

    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
    
    public String getDateOnly(){
        DateFormat toDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat toString = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return toString.format(toDate.parse(datum));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getRepeating() {
        return repeating;
    }

    public void setRepeating(int repeating) {
        this.repeating = repeating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    private double setDecimal(double number) {
        try {
            DecimalFormat deciForm = new DecimalFormat("0.00");
            deciForm.setRoundingMode(RoundingMode.HALF_UP);
            deciForm.parse(Double.toString(number));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return number;
    }
}
