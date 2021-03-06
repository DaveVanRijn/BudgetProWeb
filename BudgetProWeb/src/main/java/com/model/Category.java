/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private User user;
    
    @OneToMany(mappedBy="category")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Transaction> transactions;

    public Category() {
        super();
        this.incoming = true;
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

    public boolean isIncoming() {
        return incoming;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public void addTransaction(Transaction trans){
        this.transactions.add(trans);
    }
    
    public void removeTransaction(Transaction trans){
        this.transactions.remove(trans);
    }
    
}
