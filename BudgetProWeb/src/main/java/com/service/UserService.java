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
    SessionFactory sessionFactory;
    private String hql;
    private Query query;
    
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public List<User> getAllUsers(){
        hql = "from user";
        query = getCurrentSession().createQuery(hql);
        return (List<User>) query.list();
    }
    
    public User getUser(long accountnumber){
        hql = "from user u where u.accountnumber = :accountnumber";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("accountnumber", accountnumber);
        List<User> list = query.list();
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }
    
    public void addUser(User user){
        if(user != null){
            getCurrentSession().save(user);
        }
    }
    
    public void updateUser(User user){
        User updateUser = getUser(user.getAccountnumber());
        
        updateUser.setBalance(user.getBalance());
        updateUser.setFirstname(user.getFirstname());
        updateUser.setInfix(user.getInfix());
        updateUser.setLastname(user.getLastname());
        
        getCurrentSession().update(updateUser);
    }
    
    public void deleteUser(long accountnumber){
        User user = getUser(accountnumber);
        if(user != null){
            getCurrentSession().delete(user);
        }
    }
    
}
