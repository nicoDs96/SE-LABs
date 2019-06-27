/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaws;

import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author nds
 */
@WebService
public interface pizzaWSInterface {
    //returns the names of all the avaliables pizzas
    @XmlJavaTypeAdapter(ListAdapter.class)
    public  List<String> getMenu();
    //return a map containing as key the pizza name and as value the pizza cost
    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String ,Integer > getCosts();
}

