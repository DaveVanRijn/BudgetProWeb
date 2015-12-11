/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Entity(name = "user")
public class User implements Serializable {

    @Id
    private long accountnumber;
    private String firstname;
    private String infix;
    private String lastname;
    private double balance;
    private int lastMonthCalculated;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("datum DESC")
    private List<Transaction> transactions;
    
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<HoldTransaction> holdings;
    
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categories;
    
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Mortgage> mortgages;
    
    
    public User() {
        super();
    }

    public User(String firstname, String infix, String lastname,
            int accountnumber, double balance) {
        this.firstname = firstname;
        this.infix = infix;
        this.lastname = lastname;
        this.accountnumber = accountnumber;
        this.balance = balance;
        lastMonthCalculated = Calendar.getInstance().get(Calendar.MONTH);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(long accountnumber) {
        this.accountnumber = accountnumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getLastMonthCalculated() {
        return lastMonthCalculated;
    }

    public void setLastMonthCalculated(int lastMonthCalculated) {
        this.lastMonthCalculated = lastMonthCalculated;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public void addTransaction(Transaction tran){
        this.transactions.add(tran);
    }
    
    public void removeTransaction(Transaction tran){
        this.transactions.remove(tran);
    }
    
    public void updateTransaction(Transaction trans){
        for(Transaction s : getTransactions()){
            if(s.getId() == trans.getId()){
                s.setCategory(trans.getCategory());
                s.setDatum(trans.getDatum());
                s.setDescription(trans.getDescription());
                s.setIncoming(trans.getIncoming());
                s.setOutgoing(trans.getOutgoing());
                s.setRepeating(trans.getRepeating());
                break;
            }
        }
    }

    public List<HoldTransaction> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<HoldTransaction> holdings) {
        this.holdings = holdings;
    }
    
    public void addHolding(HoldTransaction hold){
        holdings.add(hold);
    }
    
    public void removeHolding(HoldTransaction hold){
        holdings.remove(hold);
    }

    public List<Category> getCategories() {
        if(categories != null) return categories;
        return new ArrayList<>();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public void addCategory(Category cat){
        categories.add(cat);
    }
    
    public void removeCategory(Category cat){
        categories.remove(cat);
    }

    public List<Mortgage> getMortgages() {
        return mortgages;
    }

    public void setMortgages(List<Mortgage> mortgages) {
        this.mortgages = mortgages;
    }
    
    public void addMortgage(Mortgage mort){
        mortgages.add(mort);
    }
    
    public void removeMortgage(Mortgage mort){
        mortgages.remove(mort);
    }
}
