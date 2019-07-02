/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientrest;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import static javax.ws.rs.client.Entity.entity;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author Nodes
 */
public class Client {

    private static String BASE_URL = "http://localhost:8080/risorse/";
    private static CloseableHttpClient client;

    public static void createClient() {
        client = HttpClients.createDefault();
    }

    public static void closeClient() throws IOException {
        client.close();
    }

    public static void main(String[] args) throws MalformedURLException, IOException, JAXBException {
       /*
        XML MESSAGES
       */
        
        createClient();
        
        /*
         GET
         */
        String nomeRisorsa = "tre";
        String geturi = "http://localhost:8080/risorse/"+nomeRisorsa;
        
        HttpGet httpGet = new HttpGet(geturi);
        httpGet.setHeader("Content-Type", "text/xml");
        //httpGet.setHeader("Accept", "text/xml");
        HttpResponse response = client.execute(httpGet);
        System.out.print("\nGet effettuata.\nRisposta:\n" + response.toString()+"\nResponse Entity:\t"+response.getEntity().getContent().toString() );
        Risorsa resFromGet = JAXB.unmarshal( response.getEntity().getContent(), Risorsa.class);
        System.out.print("\n\nUnmarshall:\t"+resFromGet.toString()+"\n\n");
        /*
         POST
         */
        Risorsa oggettoRisorsa = new Risorsa();
        oggettoRisorsa.setId("quattro");
        oggettoRisorsa.setName("chinotto");
        HttpPost httpPost = new HttpPost(BASE_URL  );
        //creazione dummy file
        JAXBContext jaxbContext = JAXBContext.newInstance(Risorsa.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(oggettoRisorsa, new File("res.xml"));
        File file = new File("res.xml");
        InputStream targetStream = new FileInputStream(file);

        httpPost.setEntity(new InputStreamEntity(targetStream)); //set the object as i

        httpPost.setHeader("Content-Type", "text/xml");
        //httpPost.setHeader("Accept", "text/xml");
        response = client.execute(httpPost);
        System.out.print("\nPost con risorsa:\n"+oggettoRisorsa.toString()+"\neseguita con esito:\t"+response.getStatusLine() );
        
        
        /*
         PUT
        */
        HttpPut httpPut = new HttpPut(BASE_URL + oggettoRisorsa.getId() ); 
        
        oggettoRisorsa.setName("NOME DI RISORSA");
        jaxbContext = JAXBContext.newInstance(Risorsa.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(oggettoRisorsa, new File("res.xml"));
        file = new File("res.xml");
        targetStream = new FileInputStream(file);

        httpPut.setEntity(new InputStreamEntity(targetStream));
        httpPut.setHeader("Content-Type", "text/xml");
        //httpPut.setHeader("Accept", "text/xml");
        response = client.execute(httpPut);
        System.out.print("\nPut con risorsa:\n"+oggettoRisorsa.toString()+"\neseguita con esito:\t"+response.getStatusLine() );
        
        /*
         DELETE
        */
        HttpDelete httpDelete = new HttpDelete(BASE_URL + oggettoRisorsa.getId() );
        response = client.execute(httpDelete);
        System.out.print("\nDelete con risorsa:\n"+oggettoRisorsa.toString()+"\neseguita con esito:\t"+response.getStatusLine() );
        
        
        /*
         GET deleted element
        */
        geturi = "http://localhost:8080/risorse/"+oggettoRisorsa.getId();
        
        httpGet = new HttpGet(geturi);
        httpGet.setHeader("Content-Type", "text/xml");
        httpGet.setHeader("Accept", "text/xml");
        response = client.execute(httpGet);
        if(response == null){
           System.out.print("\nGet effettuata, risposta nulla.\nStato:\n" + response.getStatusLine() );
        }else{
            System.out.print("\nGet effettuata.\nClasse risposta:\n");
        }
        
        /*
         JSON Comunication using GSON from google
        */
       System.out.print("\n\n\n---------------------------------------\nJson Messages\n---------------------------------------\n\n\n");
        /*
         GET
         */
       
         nomeRisorsa = "tre";
         geturi = "http://localhost:8080/risorse/"+nomeRisorsa;
        
        httpGet = new HttpGet(geturi);
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Accept", "application/json");
        response = client.execute(httpGet);
        System.out.print("\nGet effettuata.\n"
                + "Content Type Risposta:\n" + response.getEntity().getContentType()
                + "\nResponse Entity:\t"+response.getEntity().getContent().toString() );
        
        Gson gson = new Gson();
        Risorsa resJSON = (Risorsa) gson.fromJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), Risorsa.class);
        System.out.print("\n\nrisorsa JSON:\t"+resJSON.toString());
        
        /*
         POST
        */
        
         oggettoRisorsa = new Risorsa();
        oggettoRisorsa.setId("quattro");
        oggettoRisorsa.setName("chinotto");
        httpPost = new HttpPost(BASE_URL  );
        //creazione dummy file
        BufferedWriter writer = new BufferedWriter(new FileWriter("res.json"));
        writer.write(gson.toJson(oggettoRisorsa));
        writer.close();
        file = new File("res.json");
        
        targetStream = new FileInputStream(file);
        httpPost.setEntity(new InputStreamEntity(targetStream)); //set the object as i

        httpPost.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Accept", "application/json");
        response = client.execute(httpPost);
        System.out.print("\nPost con risorsa:\n"+oggettoRisorsa.toString()+"\neseguita con esito:\t"+response.getStatusLine() );

    }
}
