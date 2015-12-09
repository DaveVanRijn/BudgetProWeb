/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.model.HoldTransaction;
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
public class HoldTransactionService {
    
    @Autowired
    private SessionFactory sessionFactory;
    private String hql;
    private Query query;
    
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public List<HoldTransaction> getHoldTransactions(){
        hql = "from holdtransaction";
        query = getCurrentSession().createQuery(hql);
        return query.list();
    }
    
    public HoldTransaction getHoldTransaction(int id){
        hql = "from holdtransaction where h.id = :id";
        query = getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        List<HoldTransaction> list = query.list();
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    
    public void updateHoldTransaction(HoldTransaction hold){
        HoldTransaction updateHold = getHoldTransaction(hold.getId());
        if(updateHold != null){
            updateHold.setCategory(hold.getCategory());
            updateHold.setDate(hold.getDate());
            updateHold.setDescription(hold.getDescription());
            updateHold.setIncoming(hold.getIncoming());
            updateHold.setOutgoing(hold.getOutgoing());
            updateHold.setRepeating(hold.getRepeating());
            getCurrentSession().update(updateHold);
        }
    }
    
    public void deleteHoldTransaction(int id){
        HoldTransaction hold = getHoldTransaction(id);
        if(hold != null){
            getCurrentSession().delete(hold);
        }
    }
}
