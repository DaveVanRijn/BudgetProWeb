/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import System.Main;
import com.model.Category;
import com.model.Transaction;
import com.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CategoryService {

    @Autowired
    private SessionFactory sessionFactory;
    private String hql;
    private Query query;
    
    @Autowired
    private UserService userService;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Category> getCategories() {
        hql = "from category";
        query = getCurrentSession().createQuery(hql);
        return query.list();
    }

    public Category getCategory(int id) {
        hql = "from category c where c.id = :id";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        List<Category> list = query.list();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public Category getCategory(String name, boolean incoming, User user){
        hql = "from category c where c.name = :name and c.incoming = :incoming and c.user = :user";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("name", name);
        query.setParameter("incoming", incoming);
        query.setParameter("user", user);
        
        List<Category> list = query.list();
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public void addCategory(Category cat) {
        if (cat != null) {
            getCurrentSession().save(cat);
        }
    }

    public void updateCategory(Category cat) {
        Category updateCat = getCategory(cat.getId());
        if (updateCat != null) {
            updateCat.setIncoming(cat.isIncoming());
            updateCat.setName(cat.getName());
            updateCat.setUser(cat.getUser());
            getCurrentSession().update(updateCat);
        }
    }

    public void deleteCategory(int id) {
        Category cat = getCategory(id);
        if (cat != null) {
            userService.getUser(Main.getAccountnumber()).removeCategory(cat);
        }
    }

    public Map<Category, Double> getCategoryStats() {
        Map<Category, Double> catMap = new HashMap<>();
        User current = userService.getUser(Main.getAccountnumber());
        List<Category> categories = current.getCategories();
        for(Category c : categories){
            catMap.put(c, 0.0);
        }
        
        List<Transaction> transactions = current.getTransactions();
        for (Transaction t : transactions) {
            Category c = t.getCategory();
            if(catMap.containsKey(c)){
                double curValue = catMap.get(c);
                double newValue;
                if(c.isIncoming()){
                    newValue = curValue + t.getIncoming();
                } else {
                    newValue = curValue + t.getOutgoing();
                }
                catMap.put(c, newValue);
            } else {
                double value;
                if(c.isIncoming()){
                    value = t.getIncoming();
                } else {
                    value = t.getOutgoing();
                }
                catMap.put(c, value);
            }
        }
        
        return catMap;
    }
    
    public int getNextId() {
        Criteria crit = getCurrentSession().createCriteria(Category.class);
        crit.setProjection(Projections.max("id"));
        return ((Integer) crit.uniqueResult()) + 1;
    }
}
