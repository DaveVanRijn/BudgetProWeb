/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import System.Main;
import com.model.Transaction;
import com.model.User;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    @Autowired
    private UserService userService;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Add a new transaction to the database
     *
     * @param trans the transaction
     */
    public void addTransaction(Transaction trans) {
        getCurrentSession().save(trans);
    }

    /**
     * Get all transactions from the database
     *
     * @return List of transactions
     */
    public List<Transaction> getTransactions() {
        hql = "from transaction";
        query = getCurrentSession().createQuery(hql);
        return query.list();
    }

    /**
     * Get a transaction with specified id
     *
     * @param id
     * @return Transaction
     */
    public Transaction getTransaction(int id) {
        hql = "from transaction t where t.id = :id";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        List<Transaction> list = query.list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Update an excisting transaction
     *
     * @param transaction The transaction to be deleted
     */
    public void updateTransaction(Transaction transaction) {
        Transaction updateTransaction = getTransaction(transaction.getId());
        if (updateTransaction != null) {
            updateTransaction.setDatum(transaction.getDatum());
            updateTransaction.setDescription(transaction.getDescription());
            updateTransaction.setIncoming(transaction.getIncoming());
            updateTransaction.setOutgoing(transaction.getOutgoing());
            updateTransaction.setRepeating(transaction.getRepeating());
            updateTransaction.setUser(transaction.getUser());
            getCurrentSession().update(updateTransaction);
        }
    }

    /**
     * Delete the transacion with specified id
     *
     * @param id
     */
    public void deleteTransaction(int id) {
        Transaction tran = getTransaction(id);
        if (tran != null) {
            Main.getCurrentUser().removeTransaction(tran);
        }
    }

    /**
     * Get total incoming and outgoing of current user
     *
     * @param user
     * @return Array containing total income and total outgoing
     */
    public double[] getTotalOutAndIn(User user) {
        List<Transaction> list = user.getTransactions();

        double outgoing = 0;
        double incoming = 0;
        for (Transaction t : list) {
            outgoing += t.getOutgoing();
            incoming += t.getIncoming();
        }
        return new double[]{outgoing, incoming};
    }

    /**
     * Get the 5 most recent transactions of the specified user
     *
     * @param user
     * @return List of 5 recent transactions
     */
    public List<Transaction> getRecentTransactions(User user) {
        List<Transaction> transactions = user.getTransactions();
        List<Transaction> temp = new ArrayList<>();
        temp.addAll(transactions);
        while (temp.size() > 5) {
            temp.remove(0);
        }
        return temp;
    }
}
