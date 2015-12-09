/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.model.Category;
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
public class CategoryService {

    @Autowired
    private SessionFactory sessionFactory;
    private String hql;
    private Query query;

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
            getCurrentSession().update(updateCat);
        }
    }

    public void deleteCategory(int id) {
        Category cat = getCategory(id);
        if (cat != null) {
            getCurrentSession().delete(cat);
        }
    }
}
