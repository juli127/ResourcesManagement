package com.gmail.kramarenko104.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rentable")
public class RentableResource extends Resource {

    private int totalAmount;

    public RentableResource(){
        super();
    }

    public void setTotalAmount(int amount) {
        this.totalAmount = amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void addResource(int addAmount) {
        super.addResource(addAmount);
        totalAmount += addAmount;
    }
}
