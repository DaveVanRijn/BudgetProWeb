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

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Entity(name = "holdtransaction")
public class HoldTransaction implements Serializable{
    
    @Id
    @GenericGenerator(name = "holdtransaction", strategy = "increment")
    @GeneratedValue(generator = "holdtransaction")
    private int id;
    
    @ManyToOne
    private Transaction transaction;
}
