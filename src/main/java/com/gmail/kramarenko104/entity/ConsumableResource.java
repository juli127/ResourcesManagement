package com.gmail.kramarenko104.entity;

import javax.persistence.*;

@Entity
@Table (name = "consumable")

public class ConsumableResource extends Resource{

    private int consumedAmount;

    public ConsumableResource(){
    }

    public int getConsumed() {
        return consumedAmount;
    }

    public void setConsumed(int consumedAmount) {
        this.consumedAmount = consumedAmount;
    }

}