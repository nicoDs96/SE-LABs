/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaws;
import javax.xml.ws.Endpoint;


/**
 *
 * @author nds
 */
public class pizzaWSServer {
    public static void main(String args[]) throws InterruptedException{
        pizzaWSImpl ws = new pizzaWSImpl();
        String addres = "http://localhost:8080/pizza";  
      
        Endpoint.publish(addres, ws);
        System.out.println("Endpoint Published...\nTry to reach the server");
       
    }
}