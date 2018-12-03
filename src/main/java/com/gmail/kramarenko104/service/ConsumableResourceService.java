package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.ConsumableResource;
import com.gmail.kramarenko104.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class ConsumableResourceService extends ResourceCommonService {

    EntityManager em;
    UserActionsLogService log;

    public ConsumableResourceService(){}

    public ConsumableResourceService(UserActionsLogService log, EntityManager em){
        super(log, em);
        this.em = em;
        this.log = log;
    }

    public void replenishResource(User user, ConsumableResource resource, int amount){

        em.getTransaction().begin();
        resource.setLeftAmount(resource.getLeftAmount() + amount);
        em.merge(resource);
        em.getTransaction().commit();

        String description = "Admin added " + amount + " of ConsumableResource " + resource.toString();
        log.recordUserAction(user, new Date(), description);
    }

    public void consumeResource(User user, ConsumableResource resource, int consumeAmount){
        em.getTransaction().begin();
        int tookAmount = resource.takeResource(consumeAmount);
        if (tookAmount > 0) {
            em.merge(resource);
        }
        em.getTransaction().commit();

        String description = "User " + user.toString() +
                ((tookAmount > 0) ? " consumed ":" couldn't consume ") +
                consumeAmount + " of " + resource.toString();
        log.recordUserAction(user, new Date(), description);
    }

    public ConsumableResource getConsumableResourcebyID(int id) {
        ConsumableResource gotRes  = null;
        em.getTransaction().begin();
        try {
            gotRes = em.find(ConsumableResource.class, id);
            em.getTransaction().commit();
            return gotRes;
        } finally {
            em.getTransaction().rollback();
        }
    }

    /////////////////////////////////////////////////////////////////
    public List<ConsumableResource> listResourcesForConsumption(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ConsumableResource> cq = cb.createQuery(ConsumableResource.class);

        Root e = cq.from(ConsumableResource.class);
        // TODO

        em.getTransaction().begin();
        try {
            return em.createQuery(cq).getResultList();
        } finally {
            em.getTransaction().rollback();
        }
    }

    public List<ConsumableResource> listConsumedResources(){
        // TODO
        return null;
    }




}
