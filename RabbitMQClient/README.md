# Client to Rabbit 
Tutorials Index:  
https://www.rabbitmq.com/getstarted.html  

https://www.rabbitmq.com/tutorials/tutorial-one-java.html

## POM
```
<dependency>
  <groupId>com.rabbitmq</groupId>
  <artifactId>amqp-client</artifactId>
  <version>5.7.2</version>
</dependency>
```

## Sending a message to the Queue 
Prima di inviare un messaggio spesso è necessario definire un exchange, specialemente se si lavora con PS o  Topic Subscription.  Il codice che segue è un semplice esempio di p2p 
```
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
  private final static String QUEUE_NAME = "hello";
  public static void main(String[] argv) throws Exception {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      try (Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel()  ) {
         channel.queueDeclare(QUEUE_NAME, false, false, false, null);
         String message = "Hello World!";
         channel.basicPublish("", QUEUE_NAME, null, message.getBytes() );
         System.out.println(" [x] Sent '" + message + "'");
     
     }
  }
}    
```

## Receiving a message from the queue
```  
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

```
