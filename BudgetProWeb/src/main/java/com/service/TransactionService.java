/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import System.Main;
import com.model.Transaction;
import com.model.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
@Service
@Repository
@Transactional
public class TransactionService {
    
    @Autowired
    private SessionFactory sessionFactory;
    private String hql;
    private Query query;
    
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public List<Transaction> getTransactions(){
        hql = "from transaction";
        query = getCurrentSession().createQuery(hql);
        return query.list();
    }
    
    public Transaction getTransaction(int id){
        hql = "from transaction t where t.id = :id";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        List<Transaction> list = query.list();
        if(list.isEmpty()) return null;
        return list.get(0);
    }
    
    public void updateTransaction(Transaction transaction){
        Transaction updateTransaction = getTransaction(transaction.getId());
        
        updateTransaction.setDatum(transaction.getDatum());
        updateTransaction.setDescription(transaction.getDescription());
        updateTransaction.setIncoming(transaction.getIncoming());
        updateTransaction.setKind(transaction.getKind());
        updateTransaction.setOutgoing(transaction.getOutgoing());
        updateTransaction.setRepeating(transaction.getRepeating());
        
        getCurrentSession().update(updateTransaction);
    }
    
    public void deleteTransaction(Transaction transaction){
        getCurrentSession().delete(transaction);
    }
    
    public double[] getTotalOutAndIn(){
        User user = Main.getCurrentUser();
        hql = "from transaction t where t.user = :user";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("user", user);
        List<Transaction> list = query.list();
        
        double outgoing = 0;
        double incoming = 0;
        for(Transaction t : list){
            outgoing += t.getOutgoing();
            incoming += t.getIncoming();
        }
        
        return new double[]{outgoing, incoming};
    }
    
    public List<Transaction> getRecentTransactions(){
        User user = Main.getCurrentUser();
        hql = "from transaction t where t.user = :user order by t.datum";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("user", user);
        query.setMaxResults(5);
        
        return query.list();
    }
}
