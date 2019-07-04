/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.nicods.rabbitPS;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nds
 */
public class Publisher extends Thread {

    private static final String EXCHANGE_NAME = "topic_xxx";
    public String routingKey = "porno.tuamadre";
    public String routingKey2 = "porno.tuopadre";

    public static void main(String[] argv) throws Exception {
          Publisher prandom1 = new Publisher();
          Publisher prandom2 = new Publisher();
          Publisher prandom3 = new Publisher();
          Publisher prandom4 = new Publisher();
          
          prandom1.start();
          prandom2.start();
          prandom3.start();
          prandom4.start();
    }

    @Override
    public void run() {
        while (true) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            try (Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel()) {

                channel.exchangeDeclare(EXCHANGE_NAME, "topic");
                
                
                
                String rk = "";
                String message="";
                
                int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1); //min, max+1
                if(randomNum == 0){
                    rk = this.routingKey;
                    message = "tua madre a pecora";
                }else{
                    rk = this.routingKey2;
                    message = "tua padre a 90 su tua madre a pecora = grattapecora";
                }


                channel.basicPublish(EXCHANGE_NAME, rk, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + rk + "':'" + message + "'");

           
                System.out.println(" [x] Sent '" + rk + "':'" + message + "'");
                
                randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1); //min, max+1
                Thread.sleep(randomNum*1000);
                
            } catch (IOException ex) {
                Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
