package com.gmail.kramarenko104.entity;

import javax.persistence.*;

@Entity
@Table (name = "consumable")

public class ConsumableResource extends Resource {

    private int consumedAmount;

    public ConsumableResource(){
        super();
    }

    @Override
    public int takeResource(int takeAmount) {
        int tookAmount = super.takeResource(takeAmount);
        consumedAmount += tookAmount;
        return tookAmount;
    }

    public int getConsumed() {
        return consumedAmount;
    }

}
