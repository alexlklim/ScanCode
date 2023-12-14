package com.alex.scancode.models.json;

import com.google.gson.Gson;

import java.util.List;

public class OrdersList {

    private int identifier;
    List<OrderWithCodes> ordersList;

    public OrdersList(int identifier, List<OrderWithCodes> ordersList) {
        this.identifier = identifier;
        this.ordersList = ordersList;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


}
