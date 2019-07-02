# RESTful WS w Apache CXF  
https://cxf.apache.org/docs/jax-rs-basics.html#JAX-RSBasics-@Path  

## Implementazione API
Esempio API:  
```
@Path("resourcesFolder")
@Produces("text/xml")
public class mainRepository(){
/*
repository data init ...
*/

//accessing methods
@GET
@Path("mamme/{mammtID}")
public ResClass getMammt(@PathParam("mammtID") String mammtIdentifier){}

@POST
@Path("mamme")
public Response addMammt( Mammt mammtObject){}

@PUT
@Path("mamme/{mammtID}")
public Response updateMammt(@PathParam("mammtID") String mammtIdentifier, Mammt mammtObjectModified ){//utilizzare l'id per identificare la risorsa e l'oggetto per effettuare le modifiche}

@DELETE
@Path("mamme/{mammtID}")
public Response deleteMammt(@PathParam("mammtID") String mammtIdentifier){} 

}
```
1. Implementare la repository principale WS REST:  
1.1 Utilizzare ```@Path("serviceBaseFolder")``` per definire il primo parametro significativo che identifica l' intero servizio in un dato dominio. Esempio www.cazzarola.it/serviceBaseFolder  
1.2 Utilizzare ```@Produces("text/xml")``` per specificare il tipo di risposta. E' possibile specificare piu' di un tipo di risposta  
1.3 definire i metodi getter con annotazioni ```@GET``` e ```@Path```. Il path specificato dopo @GET verra' messo in append al path specificato dall'interfaccia del web service  
1.4 definire setter con ```@POST``` o ```@PUT``` o ```@DELETE```
2. Implementare tutti i metodi di gestione dati (backend dell'interfaccia)  
2.1 Definire le risposte HTTP: ``` return Response.ok().build()``` o ```return Response.status(Response.Status.NOT_FOUND).build()``` etc..
3. Rendere il WS raggiungibile con JAX-RS:  
3.1 Creare un ```JAXRSServerFactoryBean```  
3.2 Associare al FB una implementazione del WS (SingletonResourceProvider)  
3.3 impostare l'indirizzo a cui e' possibile raggiungere il dominio  
3.4 creare il server ```Server s= factoryBean.create()```   
Note: subpath ``` @Path("cartellaPrincipale/{parVariabile}/sottocartella")``` il metodo sarà qualcosa del tipo ```public void getQualcosa( @PathParam("parVariabile") String parametroDaURL )```   
```
public static void main(String args[]) {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(MainRepositoryWSImpl.class);
        factoryBean.setResourceProvider(
                new SingletonResourceProvider(new MainRepositoryWSImpl())
        );
        
        factoryBean.setAddress("http://localhost:8080/");
        Server server = factoryBean.create();
        System.out.println("Server up and running");
    }
```
## Implementazione Client 
Definire la url di base alla repository principale:  
``` private static String BASE_URL = "http://localhost:8080/mainDir/";```  
Inizzializzare il client:   
``` 
private static CloseableHttpClient client; 

public static void createClient() {
    client = HttpClients.createDefault();
}
public static void closeClient() throws IOException {
    client.close();
}
```
### HTTP GET
```
 URL url = new URL(BASE_URL + "pathAllaRisporsa/"+"parametroURL");
    InputStream input = url.openStream();
    //Supponendo che la classe sia Course
    Course course = JAXB.unmarshal(new InputStreamReader(input), Course.class);
```
### HTTP POST
La risorsa che costituisce il body del post può essere passata da file:

```
HttpPost httpPost = new HttpPost(BASE_URL + "path/variabile/students");

final File initialFile = new File("/Home/Documents/NetBeansProjects/MyRestClient/file.xml");
final InputStream targetStream = new FileInputStream(initialFile);

httpPost.setEntity(new InputStreamEntity(targetStream));
httpPost.setHeader("Content-Type", "text/xml");
HttpResponse response = client.execute(httpPost);
assertEquals(409, response.getStatusLine().getStatusCode());

```
  
La risorsa che costituisce il body del post può essere passata da file dummy, costruito al momento attraverso il marshaller della classe invocato sull'oggetto da inviare all'api:  

```
HttpPost httpPost = new HttpPost(BASE_URL + "path/variabile/students");
//creazione dummy file
JAXBContext jaxbContext = JAXBContext.newInstance( Student.class );
Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
jaxbMarshaller.marshal( oggettoStudente, new File( "studente.xml" ) );  

File file = new File( "countries.xml" );
final InputStream targetStream = new FileInputStream(file);

httpPost.setEntity(new InputStreamEntity(targetStream));

httpPost.setHeader("Content-Type", "text/xml");
HttpResponse response = client.execute(httpPost);
assertEquals(409, response.getStatusLine().getStatusCode());
```

### HTTP PUT

```
HttpPut httpPut = new HttpPut(BASE_URL + "/path/3"); //3 è l'id della risorsa da modificare
/*
resourceStram è uno stream che legge una risorsa in formato xml
*/
httpPut.setEntity(new InputStreamEntity(resourceStream));
httpPut.setHeader("Content-Type", "text/xml");
HttpResponse response = client.execute(httpPut);
//proccess the response

```

### HTTP DELETE 
```
HttpDelete httpDelete = new HttpDelete(BASE_URL + "1/students/1");
HttpResponse response = client.execute(httpDelete);
```

## POM  
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
