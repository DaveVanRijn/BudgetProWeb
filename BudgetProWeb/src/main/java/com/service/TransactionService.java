/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import System.Main;
import com.model.Category;
import com.model.HoldTransaction;
import com.model.Transaction;
import com.model.User;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
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
        trans.setIncoming(setDecimal(trans.getIncoming()));
        trans.setOutgoing(setDecimal(trans.getOutgoing()));

        getCurrentSession().save(trans);
        User current = userService.getUser(Main.getAccountnumber());
        current.setBalance(current.getBalance() + trans.getIncoming() - trans.getOutgoing());
        userService.updateUser(current);
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

    public List<Transaction> getLessTransactions(User user, int results) {
        hql = "from transaction t where t.user = :user and t.repeating = :repeating order by t.datum DESC";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("user", user);
        query.setParameter("repeating", 0);
        query.setMaxResults(results);
        return query.list();
    }

    public List<Transaction> getRepeatingTransactions(User user) {
        hql = "from transaction t where t.user = :user and t.repeating > :repeating order by t.datum DESC";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("user", user);
        query.setParameter("repeating", 0);
        query.setMaxResults(50);
        return query.list();
    }
    
    public List<Transaction> getRepeatedTransactions(User user){
        hql = "from transaction t where t.user = :user and t.repeating = :repeating order by t.datum DESC";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("user", user);
        query.setParameter("repeating", -1);
        query.setMaxResults(100);
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
        transaction.setIncoming(setDecimal(transaction.getIncoming()));
        transaction.setOutgoing(setDecimal(transaction.getOutgoing()));

        Transaction updateTransaction = getTransaction(transaction.getId());
        if (updateTransaction != null) {
            User current = userService.getUser(Main.getAccountnumber());
            double oriIncoming = updateTransaction.getIncoming();
            double oriOutgoing = updateTransaction.getOutgoing();

            updateTransaction.setDatum(transaction.getDatum());
            updateTransaction.setDescription(transaction.getDescription());
            updateTransaction.setIncoming(transaction.getIncoming());
            updateTransaction.setOutgoing(transaction.getOutgoing());
            updateTransaction.setRepeating(transaction.getRepeating());
            updateTransaction.setUser(transaction.getUser());
            updateTransaction.setCategory(transaction.getCategory());
            getCurrentSession().update(updateTransaction);

            current.setBalance(current.getBalance() - oriIncoming + oriOutgoing
                    + transaction.getIncoming() - transaction.getOutgoing());
            userService.updateUser(current);
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
            User current = userService.getUser(Main.getAccountnumber());
            if (tran.getRepeating() <= 0) {
                current.setBalance(current.getBalance() - tran.getIncoming() + tran.getOutgoing());
            }
            current.removeTransaction(tran);
            userService.updateUser(current);
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
        return new double[]{setDecimal(outgoing), setDecimal(incoming)};
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
        for (Transaction t : transactions) {
            if (t.getRepeating() == 0) {
                temp.add(t);
                if (temp.size() == 5) {
                    return temp;
                }
            }
        }
        return temp;
    }

    public Transaction holdToTransaction(HoldTransaction hold) {

        Transaction tran = new Transaction();
        tran.setCategory(hold.getCategory());
        tran.setDatum(hold.getDatum());
        tran.setDescription(hold.getDescription());
        tran.setIncoming(setDecimal(hold.getIncoming()));
        tran.setOutgoing(setDecimal(hold.getOutgoing()));
        tran.setRepeating(-1);
        tran.setUser(hold.getUser());

        return tran;
    }

    public List<Transaction> getByCategory(Category cat, User user) {
        hql = "from transaction t where t.category = :cat and t.user = :user";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("cat", cat);
        query.setParameter("user", user);
        return query.list();
    }

    public String getLastDate(User user) {
        DateFormat form = new SimpleDateFormat("dd-M-yyyy"),
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Transaction t : user.getTransactions()) {
            if (t.getRepeating() == 0) {
                try {
                    return form.format(date.parse(t.getDatum()));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return "n.v.t.";
    }

    public int getNextId() {
        Criteria crit = getCurrentSession().createCriteria(Transaction.class);
        crit.setProjection(Projections.max("id"));
        return ((Integer) crit.uniqueResult()) + 1;
    }

    private double setDecimal(double number) {
        try {
            DecimalFormat deciForm = new DecimalFormat("0.00");
            deciForm.setRoundingMode(RoundingMode.HALF_UP);
            deciForm.parse(Double.toString(number));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return number;
    }
}
