/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Entity(name = "hypotheek")
public class Hypotheek {
    
    @Id
    @GenericGenerator(name = "hypotheek", strategy = "increment")
    @GeneratedValue(generator = "hypotheek")
    private int id;
    private String kind;
    private double redemption;
    private double residualDebt;
    private double interest;
    private String description;
    private double annuity;
    
}
