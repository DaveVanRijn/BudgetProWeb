/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.model.Mortgage;
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
public class MortgageService {
    
    @Autowired
    private SessionFactory sessionFactory;
    private String hql;
    private Query query;
    
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public List<Mortgage> getMortgages(){
        hql = "from mortgage";
        query = getCurrentSession().createQuery(hql);
        return query.list();
    }
    
    public Mortgage getMortgage(int id){
        hql = "from mortgage m where m.id = :id";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        List<Mortgage> list = query.list();
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    
    public void addMortgage(Mortgage mortgage){
        getCurrentSession().save(mortgage);
    }
    
    public void updateMortgage(Mortgage mort){
        Mortgage updateMort = getMortgage(mort.getId());
        if(updateMort != null){
            updateMort.setAnnuity(mort.getAnnuity());
            updateMort.setDescription(mort.getDescription());
            updateMort.setInterest(mort.getInterest());
            updateMort.setKind(mort.getKind());
            updateMort.setRedemption(mort.getRedemption());
            updateMort.setResidualDebt(mort.getResidualDebt());
            updateMort.setUser(mort.getUser());
            getCurrentSession().update(updateMort);
        }
    }
    
    public void deleteMortgage(int id){
        Mortgage mort = getMortgage(id);
        if(mort != null){
            mort.setUser(null);
            getCurrentSession().delete(mort);
        }
    }
}
