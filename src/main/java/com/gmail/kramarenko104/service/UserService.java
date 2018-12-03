package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserService {

    private static EntityManagerFactory emf = null;

    static {
        emf = Persistence.createEntityManagerFactory("persistenceUnits.resourcesManagement");
    }

    public UserService(){
        init ();
    }

    public User getUser(int id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            return em.find(User.class, id);
        } finally {
            em.getTransaction().rollback();
        }
    }

    public void saveUser(User user) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    private void init() {

        final User admin = new User();
        admin.setId(1);
        admin.setName("admin");
        admin.setIpAddress("192.168.95.1");
        saveUser(admin);

        final User user1 = new User();
        user1.setId(2);
        user1.setName("Ivanov Ivan Ivanovich");
        user1.setIpAddress("192.168.95.3");
        saveUser(user1);

        final User user2 = new User();
        user2.setId(3);
        user2.setName("Petrenko Vasyl Nikolaevich");
        user2.setIpAddress("192.168.93.2");
        saveUser(user2);
    }

}
