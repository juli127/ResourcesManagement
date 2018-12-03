package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.RentableResource;
import com.gmail.kramarenko104.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Date;

public class RentableResourceService {

    private static EntityManagerFactory emf = null;

    UserActionsLogService log;

    static {
        emf = Persistence.createEntityManagerFactory("persistenceUnits.resourcesManagement");
    }

    public RentableResourceService(UserActionsLogService log) {
        this.log = log;
    }

    public void addResourceToInventory(User user, RentableResource resource, int amount) {
        String description = "Admin is going to add Rentable Resource: " + amount + " of" + resource.toString();
        log.recordUserAction(user, new Date(), description);

        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        resource.setLeftAmount(resource.getLeftAmount() + amount);
        resource.setTotalAmount(resource.getTotalAmount() + amount);
        em.merge(resource);
        em.getTransaction().commit();

        description = "Was added " + amount + " of " + resource.toString();
        log.recordUserAction(user, new Date(), description);
    }

    public RentableResource getRentableResourceById(int id) {
        EntityManager em = emf.createEntityManager();
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