package com.gmail.kramarenko104.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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

}
