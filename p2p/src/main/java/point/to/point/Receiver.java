/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package point.to.point;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.*;

/**
 *
 * @author nds
 */
public class Receiver {

    /**
     * @param args the command line arguments
     */
    static Context ictx = null;

    public static void main(String[] args) throws JMSException, InterruptedException {
        try {
            Properties props = new Properties();
       props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
       props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
       
            ictx = new InitialContext(props);
            Queue queue = (Queue) ictx.lookup("dynamicQueues/codaMessaggi");
            QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("ConnectionFactory");
            ictx.close();

            QueueConnection qc = qcf.createQueueConnection();
            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver qsrec = qs.createReceiver(queue);
            qc.start(); //starting session
            TextMessage msg;
            

            int i = 0;
            while (true && i < 10) {
                i++;
                msg = (TextMessage) qsrec.receive();
                System.out.print("Message: " + msg.getText() + "\nId: " + msg.getJMSMessageID());
                System.out.println("wainting for a message... ");
                Thread.sleep(5000);
            }

            qc.close();
        } catch (NamingException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
