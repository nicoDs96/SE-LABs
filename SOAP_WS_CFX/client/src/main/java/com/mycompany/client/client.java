/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client;

/**
 *
 * @author biar
 */
public class client {
    public static void main(String args[]) throws InterruptedException{
        System.out.println("Richiedo Costi:\n");
        Thread.sleep(1000);
        System.out.println("...");
        Thread.sleep(1000);
        System.out.println("Costi: "+ getCosts());
        System.out.println("Richiedo Menu:\n");
        Thread.sleep(1000);
        System.out.println("...");
        Thread.sleep(1000);
        System.out.println("Menu: "+ getMenu());
    }
    
    
private static String getCosts(){ // Call Web Service Operation
    com.mycompany.pizzaws.PizzaWSImplService service = new com.mycompany.pizzaws.PizzaWSImplService();
    com.mycompany.pizzaws.PizzaWSInterface port = service.getPizzaWSImplPort();
    // TODO process result here
    java.lang.String result = port.getCosts();
    return result;
} 
private static String getMenu(){
    com.mycompany.pizzaws.PizzaWSImplService service = new com.mycompany.pizzaws.PizzaWSImplService();
    com.mycompany.pizzaws.PizzaWSInterface port = service.getPizzaWSImplPort();
    // TODO process result here
    java.lang.String result = port.getMenu();
    return result;
} 

}
