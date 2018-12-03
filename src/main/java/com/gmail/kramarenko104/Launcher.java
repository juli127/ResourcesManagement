package com.gmail.kramarenko104;

import com.gmail.kramarenko104.service.*;
import com.gmail.kramarenko104.entity.*;
import org.apache.log4j.PropertyConfigurator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Launcher {

    static {
        PropertyConfigurator.configure(Launcher.class.getResource("/log4j.properties"));
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnits.resourcesManagement");
        final EntityManager em = emf.createEntityManager();

        // create new users
        UserService userService = new UserService(em);
        final User admin = new User();
        admin.setId(1);
        admin.setName("admin");
        admin.setIpAddress("192.168.95.1");
        userService.saveUser(admin);

        final User user1 = new User();
        user1.setId(2);
        user1.setName("Ivanov Ivan Ivanovich");
        user1.setIpAddress("192.168.95.3");
        userService.saveUser(user1);

        final User user2 = new User();
        user2.setId(3);
        user2.setName("Petrenko Vasyl Nikolaevich");
        user2.setIpAddress("192.168.93.2");
        userService.saveUser(user2);

        UserActionsLogService log = new UserActionsLogService(em);

        // init set of Consumable Resources ////////////////////////////////////////
        ConsumableResourceService consServ = new ConsumableResourceService(log, em);

        // all these actions should be added to log
        ConsumableResource consRes1 = new ConsumableResource();
        consRes1.setName("Parker pen");
        consServ.replenishResource(admin, consRes1, 128);

        ConsumableResource consRes2 = new ConsumableResource();
        consRes2.setName("A4 paper block");
        consServ.replenishResource(admin, consRes2, 245);

        ConsumableResource consRes3 = new ConsumableResource();
        consRes3.setName("A4 folder");
        consServ.replenishResource(admin, consRes3, 355);

        // init set of Rentable Resources //////////////////////////////////
        RentableResourceService rentServ = new RentableResourceService(log, em);

        // all these actions should be added to log
        RentableResource rentRes = new RentableResource();
        rentRes.setName("table");
        rentServ.addResourceToInventory(admin, rentRes, 345);

        rentRes = new RentableResource();
        rentRes.setName("computer ASUS-34");
        rentServ.addResourceToInventory(admin, rentRes, 89);

        rentRes = new RentableResource();
        rentRes.setName("book1");
        rentServ.addResourceToInventory(admin, rentRes, 2);

        //////////////////////////////////////////////////////////////
        // user asks ConsumableResource, save request from user
        /// this action should be added to log
        consServ.consumeResource(user1, consRes1, 7);
        consServ.consumeResource(user2, consRes3, 5);


    }

}
