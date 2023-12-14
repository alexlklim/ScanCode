package com.alex.scancode.models.json;

import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;
import com.google.gson.Gson;

import java.util.List;

public class OrderWithCodes {
    private int identifier;
    private Order order;
    private List<Code> codeList;


    public OrderWithCodes(Order order, List<Code> codeList, int identifier) {
        this.identifier = identifier;
        this.order = order;
        this.codeList = codeList;
    }

    // Getter and setter methods for Order and codeList

    // Convert OrderWithCodes to JSON
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getIdentifier() {
        return identifier;
    }

    public Order getOrder() {
        return order;
    }

    public List<Code> getCodeList() {
        return codeList;
    }
}
