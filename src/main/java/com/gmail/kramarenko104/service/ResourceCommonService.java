package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.Resource;
import javax.persistence.EntityManager;

public abstract class ResourceCommonService {

    EntityManager em;
    UserActionsLogService log;

    public ResourceCommonService(){}

    public ResourceCommonService(UserActionsLogService log, EntityManager em){
        this.em = em;
        this.log = log;
    }
    public int getResource(Resource resource, int takeAmount){
        em.getTransaction().begin();
        int tookAmount = resource.takeResource(takeAmount);
        if (tookAmount > 0) {
            em.merge(resource);
        }
        em.getTransaction().commit();
        return tookAmount;
    }

    public void addResource(Resource resource, int addAmount){
        em.getTransaction().begin();
        resource.addResource(addAmount);
        em.merge(resource);
        em.getTransaction().commit();
    }
}
