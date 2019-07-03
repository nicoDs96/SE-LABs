# JMS
è una architettura per lo scambio di messaggi peer to peer o attraverso publish-subscribe.  
L'architettura che implementa le queue/topic è al di fuori del JMS, che definisce la sola interfaccia per la comunicazione.

## Producer
1. Individuare il servizio attraverso JNDI (primo blocco try-catch)
```
//definire record JNDI per raggiungere il servizio:
Properties props = new Properties();
props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
//Creare il contesto
jndiContext = new InitialContext(props);   
```
2. Cercare la connection factory e la destinazione attraverso il contesto JNDI (secondo blocco try-catch)
```
//Serve a creare connessioni
connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
//serve per la definizione del producer (vedi punto 5)
destination = (Destination)jndiContext.lookup(destinationName);
```

3. Creare una connesione
```
 connection = connectionFactory.createConnection();
```
4. Generare una sessione dalla connessione
```
 session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
```
5. Generare un producer dalla sessione attraverso la definizione della destinazione
```
 producer = session.createProducer(destination);
```
6. Creare il messaggio
```
TextMessage message = null;
message = session.createTextMessage();
message.setText(
   "Testo del messaggio"
);
```
7. Inviare il messaggio
```
 producer.send(message);
```  
## Consumer
### Async
1. Individuare il servizio attraverso JNDI (primo blocco try-catch)
```
//definire record JNDI per raggiungere il servizio:
Properties props = new Properties();
props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
//Creare il contesto
jndiContext = new InitialContext(props);   
```
2. Inizializzare la connectionf factory per il topic desiderato  
```
topicConnectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
destination = (Destination)jndiContext.lookup(destinationName);
```
3. Instanziare la connessione verso il topic
```
topicConnection = (TopicConnection)topicConnectionFactory.createConnection();
```
4. Instanziare la sessione verso il topic
```
topicSession = (TopicSession)topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
```
5. Instanziare il subscriber ad un dato topic ed assegnargli un listener
```
TopicSubscriber topicSubscriber = topicSession.createSubscriber((Topic)destination);
topicSubscriber.setMessageListener(this);
```
6. Metodi per avviare e terminare la ricezione
```  
topicConnection.stop();
topicConnection.start();
```
7. Definire il listener per la ricezione dei messaggi  
```
public void onMessage(Message mex) {
		try {
			String nome = mex.getStringProperty("Nome");
			float valore = mex.getFloatProperty("Valore");
			
			Risorsa r = new Risorsa(nome, valore);

			super.setChanged();	// pattern Observable: rende attivo il cambiamento di stato
			super.notifyObservers(r); //pattern Observable: notifica gli osservatori
		} catch (JMSException err) {
			err.printStackTrace();
		}
}
```
### Sync

## Esempi Completi
### Producer
```
import javax.jms.*;
import javax.naming.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public static void  main(String[] args) throws NamingException, JMSException {
  
  Context jndiContext = null;
  ConnectionFactory connectionFactory = null;
  Connection connection = null;
  Session session = null;
  Destination destination = null;
  MessageProducer producer = null;
  String destinationName = "dynamicTopics/Quotazioni";
  
  /*
  * Create a JNDI API InitialContext object
  */
  
  try {
    
    Properties props = new Properties();
    
    props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
    props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
    jndiContext = new InitialContext(props);        
    
  } catch (NamingException e) {
    LOG.info("ERROR in JNDI: " + e.toString());
    System.exit(1);
  }
  
  /*
  * Look up connection factory and destination.
  */
  try {
    connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
    destination = (Destination)jndiContext.lookup(destinationName);
  } catch (NamingException e) {
    LOG.info("JNDI API lookup failed: " + e);
    System.exit(1);
  }
  
  /*
  * Create connection. Create session from connection; false means
  * session is not transacted. Create sender and text message. Send
  * messages, varying text slightly. Send end-of-messages message.
  * Finally, close connection.
  */
  try {
    connection = connectionFactory.createConnection();
    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    producer = session.createProducer(destination);
    
    
    
    TextMessage message = null; 
    message = session.createTextMessage();

    int i = 0;
    while (true && i<300) {//invia 300 messaggi ogni 5 secondi
      i++;
      prop2 ="qualcosa";
      prop1 = "faccia qualcuno qualcosa";
      message.setStringProperty("Nome", prop1);
      message.setStringProperty("Valore", prop2);
      message.setText(
      "Testo del messaggio"
      );
      
      producer.send(message);
      
      try {
        Thread.sleep(5000);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
  catch (JMSException e) {
    LOG.info("Exception occurred: " + e);
  } 
  finally {
    if (connection != null) {
      try {
        connection.close();
      } catch (JMSException e) {
      }
    }
  }
}
```
## Consumer async
```

import it.uniroma1.diag.stockmarket.peer.client.dto.Quotazione;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagesJMSListener extends Observable implements MessageListener {
  
  private TopicConnection topicConnection;
  private TopicSession topicSession = null;
  private Destination destination = null;
  private MessageProducer producer = null;
  
  
  public MessagesJMSListener(Observer[] osservatori) {
      for (Observer osservatore : osservatori) {
         super.addObserver(osservatore);
      }
    
    Context jndiContext = null;
    ConnectionFactory topicConnectionFactory = null;
    
    String destinationName = "dynamicTopics/Quotazioni";
    
    try {
      
      Properties props = new Properties();
      
      props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
      props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
      jndiContext = new InitialContext(props);   
      
      
      
      
      topicConnectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
      destination = (Destination)jndiContext.lookup(destinationName);
      topicConnection = (TopicConnection)topicConnectionFactory.createConnection();
      topicSession = (TopicSession)topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      
      TopicSubscriber topicSubscriber =  
      topicSession.createSubscriber((Topic)destination);
      
      topicSubscriber.setMessageListener(this);
    } catch (JMSException err) {
      err.printStackTrace();
    } catch (NamingException err) {
      err.printStackTrace();
    }
  }
  
  /**
  * Chiude la ricezione dei messaggi sulla topic quotazioni
  */
  public void stop() {
    try {
      topicConnection.stop();
    } catch (JMSException err) {
      err.printStackTrace();
    }
  }
  
  /**
  * Apre la ricezione dei messaggi sulla topic quotazioni
  */
  public void start() {
    try {
      topicConnection.start();
    } catch (JMSException err) {
      err.printStackTrace();
    }
  }
  
  public void onMessage(Message mex) {
    try {
      String nome = mex.getStringProperty("Nome");
      float valore = mex.getFloatProperty("Valore");
      
      Quotazione quotazione = new Quotazione(nome, valore);
      
      super.setChanged();	// rende attivo il cambiamento di stato
      super.notifyObservers(quotazione);
    } catch (JMSException err) {
      err.printStackTrace();
    }
  }
  
}
```
