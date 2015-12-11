/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import com.model.Transaction;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Flow {
    
    public static void addTransaction(Transaction transaction){
        if(transaction.getRepeating() > 0){
            //Repeating transaction
        } else {
            //One time only transaction
            
        }
    }
}
