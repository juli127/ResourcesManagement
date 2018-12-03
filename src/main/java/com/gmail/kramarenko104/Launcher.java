package com.gmail.kramarenko104;

import com.gmail.kramarenko104.service.*;
import com.gmail.kramarenko104.entity.*;
import org.apache.log4j.PropertyConfigurator;
import javax.persistence.EntityManagerFactory;

public class Launcher {

    //private static EntityManagerFactory emf = null;

    static {
        PropertyConfigurator.configure(Launcher.class.getResource("/log4j.properties"));
    }


    public static void main(String[] args) {

        // create new users
        UserService userService = new UserService();
        User admin = userService.getUser(1);
        User user1 = userService.getUser(2);
        User user2 = userService.getUser(3);

        UserActionsLogService log = new UserActionsLogService();

        // init set of Consumable Resources ////////////////////////////////////////
        ConsumableResourceService consServ = new ConsumableResourceService(log);

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
        RentableResourceService rentServ = new RentableResourceService(log);

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
