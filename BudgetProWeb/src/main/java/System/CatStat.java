/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import com.model.Category;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class CatStat implements Comparable<CatStat>{
    
    private Category cat;
    private double amount;
    
    public CatStat(Category cat, double amount){
        this.cat = cat;
        this.amount = amount;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(CatStat o) {
        return Double.compare(amount, o.getAmount());
    }
    
}
