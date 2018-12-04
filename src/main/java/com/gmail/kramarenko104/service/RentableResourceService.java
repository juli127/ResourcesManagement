package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.RentableResource;
import com.gmail.kramarenko104.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;

public class RentableResourceService extends ResourceCommonService {

    EntityManager em;
    UserActionsLogService log;

    public RentableResourceService(){}

    public RentableResourceService(UserActionsLogService log, EntityManager em){
        super(log, em);
        this.em = em;
        this.log = log;
    }

    public void addResourceToInventory(User user, RentableResource resource, int amount) {
        super.addResource(resource, amount);
        String description = "Admin added " + amount +
                " of RentableResource " + resource.toString() +
                ". Total count now: " + resource.getLeftAmount();
        log.recordUserAction(user, new Date(), description);
    }

    public void rentResource(User user, RentableResource resource, int rentAmount){
        int tookAmount = super.getResource(resource, rentAmount);
        String description = "User " + user.toString() +
                ((tookAmount > 0) ? " rented ":" couldn't rent ") +
                rentAmount + " of " + resource.toString()+
                ((resource.getLeftAmount() == 0) ? ".... NOTHING LEFT !!! NEED TO BUY?": "");
        log.recordUserAction(user, new Date(), description);
    }

    public RentableResource getRentableResourceById(int id) {
        em.getTransaction().begin();
        try {
            String queryText = "select e from RentableResource e where e.id = :id";
            TypedQuery<RentableResource> query = em.createQuery(queryText, RentableResource.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.getTransaction().rollback();
        }
    }

}
