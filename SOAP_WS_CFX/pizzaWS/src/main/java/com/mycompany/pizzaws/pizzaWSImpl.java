/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;

/**
 *
 * @author nds
 */
@WebService(endpointInterface="com.mycompany.pizzaws.pizzaWSInterface")
public class pizzaWSImpl implements pizzaWSInterface {
    private List<String> menu;
    private Map<String,Integer> costs;

    public pizzaWSImpl() {
        this.menu = new LinkedList<>();
        this.costs = new HashMap<>();
        menu.add("margherita");
        costs.put("margherita", 3);
        menu.add("diavola");
        costs.put("diavola", 4);
        menu.add("ortolana");
        costs.put("ortolana", 5);
    }

    @Override
    public List<String> getMenu() {
        return this.menu;
    }

    @Override
    public Map<String, Integer> getCosts() {
        return this.costs;
    }
    
}
