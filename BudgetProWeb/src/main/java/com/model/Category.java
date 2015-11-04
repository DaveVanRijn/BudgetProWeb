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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Entity(name = "category")
public class Category implements Serializable{
    
    @Id
    @GenericGenerator(name = "category", strategy = "increment")
    @GeneratedValue(generator = "category")
    private int id;
    private String name;
    private boolean incoming;

    public Category() {
        super();
    }

    public Category(String name, boolean income) {
        this.name = name;
        this.incoming = income;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return incoming;
    }

    public void setIncome(boolean income) {
        this.incoming = income;
    }
    
}
