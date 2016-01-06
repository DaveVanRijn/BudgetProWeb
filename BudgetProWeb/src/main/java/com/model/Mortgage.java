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
@Entity(name = "mortgage")
public class Mortgage implements Serializable{
    
    @Id
    @GenericGenerator(name = "mortgage", strategy = "increment")
    @GeneratedValue(generator = "mortgage")
    private int id;
    private String name;
    private String kind;
    private double redemption;
    private double residualDebt;
    private double interest;
    private String description;
    private double annuity;
    
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private User user;

    public Mortgage() {
        super();
    }

    public Mortgage(String kind, String name, double redemption, double residualDebt, double interest, String description, double annuity) {
        this.kind = kind;
        this.name = name;
        this.redemption = redemption;
        this.residualDebt = residualDebt;
        this.interest = interest;
        this.description = description;
        this.annuity = annuity;
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
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public double getRedemption() {
        return redemption;
    }

    public void setRedemption(double redemption) {
        this.redemption = redemption;
    }

    public double getResidualDebt() {
        return residualDebt;
    }

    public void setResidualDebt(double residualDebt) {
        this.residualDebt = residualDebt;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAnnuity() {
        return annuity;
    }

    public void setAnnuity(double annuity) {
        this.annuity = annuity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public double calcInterest(){
        double monthlyInterest = (interest / 12) / 100;
        return residualDebt * monthlyInterest;
    }
    
}
