/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
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
@Entity(name = "holdtransaction")
public class HoldTransaction implements Serializable {

    @Id
    @GenericGenerator(name = "holdtransaction", strategy = "increment")
    @GeneratedValue(generator = "holdtransaction")
    private int id;
    private double incoming;
    private double outgoing;
    private String description;
    private String datum;
    private String category;
    
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private User user;

    public HoldTransaction() {
        super();
    }

    public HoldTransaction(double incoming, double outgoing, String description, 
            String datum, String category, User user) {
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.description = description;
        this.datum = datum;
        this.category = category;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIncoming() {
        return incoming;
    }

    public void setIncoming(double incoming) {
        this.incoming = incoming;
    }

    public double getOutgoing() {
        return outgoing;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
