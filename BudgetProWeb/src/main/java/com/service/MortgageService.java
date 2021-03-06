/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import System.Main;
import com.model.Mortgage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
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
public class MortgageService {

    @Autowired
    private SessionFactory sessionFactory;
    private String hql;
    private Query query;

    @Autowired
    private UserService userService;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Mortgage> getMortgages() {
        hql = "from mortgage";
        query = getCurrentSession().createQuery(hql);
        return query.list();
    }

    public Mortgage getMortgage(int id) {
        hql = "from mortgage m where m.id = :id";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        List<Mortgage> list = query.list();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public void addMortgage(Mortgage mortgage) {
        mortgage.setAnnuity(setDecimal(mortgage.getAnnuity()));
        mortgage.setRedemption(setDecimal(mortgage.getRedemption()));
        mortgage.setResidualDebt(setDecimal(mortgage.getResidualDebt()));

        getCurrentSession().save(mortgage);
    }

    public void updateMortgage(Mortgage mort) {
        mort.setAnnuity(setDecimal(mort.getAnnuity()));
        mort.setRedemption(setDecimal(mort.getRedemption()));
        mort.setResidualDebt(setDecimal(mort.getResidualDebt()));

        Mortgage updateMort = getMortgage(mort.getId());
        if (updateMort != null) {
            updateMort.setName(mort.getName());
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

    public void deleteMortgage(int id) {
        Mortgage mort = getMortgage(id);
        if (mort != null) {
            userService.getUser(Main.getAccountnumber()).removeMortgage(mort);
        }
    }
    
    public int getNextId(){
        Criteria crit = getCurrentSession().createCriteria(Mortgage.class);
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
