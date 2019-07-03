# Publish Subscribe Interface

## Publisher
```
public class Publisher{
   static Context ictx = null;
   
   public static void main (String[] args){
      ictx = new InitialContext();
      Topic topic = (Topic) ictx.lookup("topic");
      TopicConncectionFacotry tcf = (TopicConncectionFacotry) ictx.lookup("ConnectionFactory");
      ictx.close();
      
      TopicConnection tconn = tcf.createTopicConnection();
      TopicSession tsession = tconn.createTopicSession(false, Session.AUTO_ACKNOLEDGE);
      TopicPublisher tpub = tsession.createPublisher(topic);
      TextMessage msg = tsession.createTextMessage();
      msg.setText("TEXT");
      tpub.publish(msg);
      tconn.close();
   }
}

```

## Subscriber async
```
public Class Subscriber{
   static Context ictx = null;

   public static void main (String[] args){
      ictx = new InitialContext();
      Topic topic = (Topic) ictx.lookup("topic");
      TopicConncectionFacotry tcf = (TopicConncectionFacotry) ictx.lookup("ConnectionFactory");
      ictx.close();
      
      TopicConnection tconn = tcf.createTopicConnection();
      TopicSession tsession = tconn.createTopicSession(false, Session.AUTO_ACKNOLEDGE);
      TopicSubscriber tsub = tsession.createSubscriber(topic);
      tsub.setMessageListener(new MsgListener);
      
      tconn.start();
      System.in.read();
      tconn.close();
   }
}

class MsgListener implements MessageListener{
   String id;
   public MsgListener(){id="";}
   public void onMessage(Message msg){
      TextMessage tMsg = (TextMessage) msg;
      try{
         System.out.println("message: "+tMsg.getText() );
      }catch(JMSExceptio e){
         e.printStackTrace();
      }
   }
}
```
