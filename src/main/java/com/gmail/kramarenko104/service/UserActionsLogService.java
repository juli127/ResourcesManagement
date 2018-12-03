package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.User;
import com.gmail.kramarenko104.entity.UserActionLogRecord;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class UserActionsLogService {

    EntityManager em;

    public UserActionsLogService(EntityManager em){
        this.em = em;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserActionLogRecord recordUserAction(User user, Date actionDate, String description){

        em.getTransaction().begin();

        UserActionLogRecord record  = new UserActionLogRecord();
        record.setUser(user);
        record.setActionDate(actionDate);
        record.setDescription(description);
        em.merge(record);
        em.flush();
        em.getTransaction().commit();
        return record;
    }

}
