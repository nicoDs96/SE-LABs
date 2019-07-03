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
public class Sender {

    static Context ictx = null;

    public static void main(String[] args) throws JMSException {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            
            //Creare il contesto
            ictx = new InitialContext(props);
            
            Queue queue ;
            queue = (Queue) ictx.lookup("dynamicQueues/codaMessaggi");
            
            QueueConnectionFactory qcf;
            qcf = (QueueConnectionFactory) ictx.lookup("ConnectionFactory");
            
            ictx.close();
            
            QueueConnection qc;
            qc = qcf.createQueueConnection();
            
            QueueSession qs;  
            qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            
            QueueSender qsender;
            qsender = qs.createSender(queue);
            
            TextMessage msg;
            int i=0;
            while(i<20){
                i++;
                msg = qs.createTextMessage();
                msg.setText("TESTO"+i);
                qsender.send(msg);
                System.out.print("\nInviato messaggio:\t"+msg.getText()+"\n");
            
            }
            
            System.out.println("Gesu cri ho fatto senza eccezioni");
            System.exit(0);
        } catch (NamingException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
