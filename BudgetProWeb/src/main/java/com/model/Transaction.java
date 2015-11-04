/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

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
    private String kind; //vast/variabel
    private double incoming;
    private double outgoing;
    private String description;
    private Date datum;
    private int repeating;
    
    @OneToMany(mappedBy = "transaction")
    private List<HoldTransaction> holding;
    
    @ManyToOne
    private User user;

    public Transaction() {
        super();
    }

    public Transaction(String kind, double incoming, double outgoing, String description, Date datum, int repeating) {
        this.kind = kind;
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.description = description;
        this.datum = datum;
        this.repeating = repeating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getRepeating() {
        return repeating;
    }

    public void setRepeating(int repeating) {
        this.repeating = repeating;
    }

    public List<HoldTransaction> getHolding() {
        return holding;
    }

    public void setHolding(List<HoldTransaction> holding) {
        this.holding = holding;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
