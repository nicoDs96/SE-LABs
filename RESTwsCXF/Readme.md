# RESTful WS w Apache CXF  
https://cxf.apache.org/docs/jax-rs-basics.html#JAX-RSBasics-@Path  
POM  
```
<?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.mycompany</groupId>
    <artifactId>RESTwCXF</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-frontend-jaxrs</artifactId>
            <version>3.1.7</version>
        </dependency>

        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-transports-http-jetty</artifactId>
            <version>3.1.7</version>
        </dependency>
        <dependency>
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-jaxrs</artifactId>
            <version>1.9.13</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.2</version>
        </dependency>
    
    </dependencies>
 </project>
```

1. Implementare l'interfaccia WS REST (contract):  
1.1 Utilizzare ```@Path("serviceBaseFolder")``` per definire il primo parametro significativo che identifica l' intero servizio in un dato dominio. Esempio www.cazzarola.it/serviceBaseFolder  
1.2 Utilizzare ```@Produces("text/xml")``` per specificare il tipo di risposta. E' possibile specificare piu' di un tipo di risposta  
1.3 definire i metodi getter con annotazioni ```@GET``` e ```@Path```. Il path specificato dopo @GET verra' messo in append al path specificato dall'interfaccia del web service  
1.4 definire setter con ```@POST``` o ```@PUT``` o ```@DELETE```
2. Implementare il servizio. * NB * avendo definito le annotazioni nell'interfaccia l'implementazione non richiede nient'altro che @Override
3. Rendere il WS raggiungibile con JAX-RS:  
3.1 Creare un ```JAXRSServerFactoryBean```  
3.2 Associare al FB una implementazione del WS (SingletonResourceProvider)  
3.3 impostare l'indirizzo a cui e' possibile raggiungere il dominio  
3.4 creare il server ```Server s= factoryBean.create()```  
```
public static void main(String args[]) {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(pizzeriaWSImpl.class);
        factoryBean.setResourceProvider(
                new SingletonResourceProvider(new pizzeriaWSImpl())
        );
        
        factoryBean.setAddress("http://localhost:8080/");
        Server server = factoryBean.create();
        System.out.println("Server up and running");
    }
```

