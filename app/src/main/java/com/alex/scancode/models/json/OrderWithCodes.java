package com.alex.scancode.models.json;

import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;
import com.google.gson.Gson;

import java.util.List;

public class OrderWithCodes {
    private List<Code> codeList;

    private Order order;
    private int identifier;


    public OrderWithCodes(Order order, List<Code> codeList, int identifier) {
        this.order = order;
        this.codeList = codeList;
        this.identifier = identifier;
    }

    // Getter and setter methods for Order and codeList

    // Convert OrderWithCodes to JSON
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
