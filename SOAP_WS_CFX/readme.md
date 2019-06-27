## WS con Apache CFX

### Maven dependecy & Plugin: 
WS:  
```
<dependency>  
    <groupId>org.apache.cxf</groupId>  
    <artifactId>cxf-rt-frontend-jaxws</artifactId>  
    <version>3.1.6</version>  
</dependency>  
```

```
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-rt-transports-http-jetty</artifactId>
    <version>3.1.6</version>
</dependency>
```
Server:  
```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <configuration>
        <mainClass>com.baeldung.cxf.introduction.Server</mainClass>
    </configuration>
</plugin>
```

### Progettare il servizio:
1) creare le interfacce per definire un "contratto"  
2) implementare le interfacce  
3) invocare i metodi del WS da un client.  

cfx fornisce le annotazioni che generano in automatico i file wsdl e altre utility al fine di rendere il WS utilizzabile  

### Implementazione:
1. Creare l'interfaccia dell'endpoint con annotazione @WebService  
1.1 Annotare tutti i getter (e/o i setter) con un tipo di ritorno non supportato da JAXB con un adapter: (opzionale)  
```
@XmlJavaTypeAdapter(StudentMapAdapter.class)
public Map<String,Integer> getKVPair();
```
1.2 Implementare gli adapter (opzionale)

2. Implementare l'interfaccia dell'endpoint. Utilizzare l'annotazione: 
```
@WebService(endpointInterface = "com.mycompany.packagetoTheInterface.wsInterface")
```

3. Implementare il server che scolta su un dato url per le chiamate al webservice:
```
//this code will host the server
public class Server {
    public static void main(String args[]) throws InterruptedException {
        BaeldungImpl implementor = new wsImplementationClass();
        String address = "http://localhost:8080/wsURL"; //NB specificare il protocollo o potrebbero generarsi errori
        Endpoint.publish(address, implementor);
        Thread.sleep(60 * 1000);        
        System.exit(0);
    }
}
```  
4. In NetBeans creare un client con il wizard: nuovo progetto -> ...  
Sul package click tasto dx mouse -> new -> WebService Client  
nella configurazione inserire l'url specificato come address dell'endpoint con una richiesta get per wsdl:  
```
localhost:8080/wsURL?wsdl
```
