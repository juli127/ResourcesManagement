package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.ConsumableResource;
import com.gmail.kramarenko104.entity.User;
import org.springframework.transaction.annotation.Propagation;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class ConsumableResourceService {

    private static EntityManagerFactory emf = null;

    static {
        emf = Persistence.createEntityManagerFactory("persistenceUnits.resourcesManagement");
    }

    UserActionsLogService log;

    public ConsumableResourceService(UserActionsLogService log) {
        this.log = log;
    }

    public void replenishResource(User user, ConsumableResource resource, int amount){
        String description = "Admin is going to add " + amount + " of ConsumableResource " + resource.toString();
        log.recordUserAction(user, new Date(), description);

        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        resource.setLeftAmount(resource.getLeftAmount() + amount);
        em.merge(resource);
        em.getTransaction().commit();

        description = "Admin added " + amount + " of ConsumableResource " + resource.toString();
        log.recordUserAction(user, new Date(), description);
    }

    public void consumeResource(User user, ConsumableResource resource, int amount){
        String description = "User " + user.toString() + " tries to consume " + amount + " of " + resource.toString();
        log.recordUserAction(user, new Date(), description);

        boolean success = false;
        final EntityManager em = emf.createEntityManager();
//        int idResource = resource.getId();
//        ConsumableResource changedResource = getConsumableResource(idResource);
        em.getTransaction().begin();
        int left = resource.getLeftAmount();
        if (left >= amount){
            resource.setConsumed(resource.getConsumed() + amount);
            resource.setLeftAmount(left - amount);
            success = true;
        }
        em.merge(resource);
        em.getTransaction().commit();

        description = "User " + user.toString() + (success?" consumed ":" couldn't consume ") + amount + " of " + resource.toString();
        log.recordUserAction(user, new Date(), description);
    }

    public ConsumableResource getConsumableResource(int id) {
        final EntityManager em = emf.createEntityManager();
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

        final EntityManager em = emf.createEntityManager();

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
