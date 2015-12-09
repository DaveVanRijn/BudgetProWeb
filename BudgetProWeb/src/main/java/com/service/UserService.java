/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

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
public class UserService {

    @Autowired
    private SessionFactory sessionFactory;
    private String hql;
    private Query query;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Get all users from the database
     *
     * @return List of users
     */
    public List<User> getAllUsers() {
        hql = "from user";
        query = getCurrentSession().createQuery(hql);
        return (List<User>) query.list();
    }

    /**
     * Get the user with specified accountnumber
     *
     * @param accountnumber
     * @return User
     */
    public User getUser(long accountnumber) {
        hql = "from user u where u.accountnumber = :accountnumber";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("accountnumber", accountnumber);
        List<User> list = query.list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Add a new user to the database
     *
     * @param user
     */
    public void addUser(User user) {
        if (user != null) {
            getCurrentSession().save(user);
        }
    }

    /**
     * Update an excisting user
     * @param user
     */
    public void updateUser(User user) {
        User updateUser = getUser(user.getAccountnumber());

        if (updateUser != null) {
            updateUser.setBalance(user.getBalance());
            updateUser.setFirstname(user.getFirstname());
            updateUser.setInfix(user.getInfix());
            updateUser.setLastname(user.getLastname());

            getCurrentSession().update(updateUser);
        }
    }

    /**
     * Delete the user with specified accountnumber
     * @param accountnumber 
     */
    public void deleteUser(long accountnumber) {
        User user = getUser(accountnumber);
        if (user != null) {
            getCurrentSession().delete(user);
        }
    }

}
