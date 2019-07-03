# Point to Point JMS
Clients pp  
## Sender

```
public class Sender{
   static Context ictx = null;
   public stati void main(String[] args){
      ictx = new InitialCOntext();
      Queue queue = (Queue) ictx.lookup("queue");
      QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("ConnectionFactory");
      ictx.close();
      
      QueueConnection qc = qcf.createQueueConnection();
      QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      QueueSender qsender = qs.createSender(queue);
      TextMessage msg = qs.createTextMessage();
      
      msg.setText("TESTO");
      qsender.send(msg);
   
   }
}
```
## Receiver Sync

```

public class Receiver{
   static Context ictx = null;
   public stati void main(String[] args){
      ictx = new InitialCOntext();
      Queue queue = (Queue) ictx.lookup("queue");
      QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("ConnectionFactory");
      ictx.close();
      
      QueueConnection qc = qcf.createQueueConnection();
      QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      QueueReceiver qsrec = qs.createReceiver(queue);
      qc.start() //starting session
      TextMessage msg ;
      msg = (TextMessage) qrec.receive();
      
      msg.setText("TESTO");
      qsender.send(msg);
      
      qc.close();
   
   }

}

```
