package com.creativeshare.zapyhakoom.Model;

import java.io.Serializable;
import java.util.List;

public class Item_Cart_Model implements Serializable {

    private List<Orders_Cart_Model> orders;

    public void setOrders(List<Orders_Cart_Model> orders) {
        this.orders = orders;
    }

    public List<Orders_Cart_Model> getOrders() {
        return orders;
    }
}
