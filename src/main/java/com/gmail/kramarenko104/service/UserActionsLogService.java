package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.User;
import com.gmail.kramarenko104.entity.UserActionLogRecord;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class UserActionsLogService {

    private static EntityManagerFactory emf = null;

    static {
        emf = Persistence.createEntityManagerFactory("persistenceUnits.resourcesManagement");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserActionLogRecord recordUserAction(User user, Date actionDate, String description){

        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        UserActionLogRecord record  = new UserActionLogRecord();
        record.setActionDate(actionDate);
        record.setDescription(description);
        em.merge(record);

        em.getTransaction().commit();
        return record;
    }

}
