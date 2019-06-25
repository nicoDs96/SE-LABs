/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaxbandxds;

import java.io.File;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author nds
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PropertyException {
        // TODO code application logic here
       
        Persona p = new Persona();
        p.setBirthdate(LocalDate.now());
        p.setName("nicola");
        p.setSurname("Gay Santo");
        
        /*
        MARSHAL
        */
       
        JAXBContext c=null;
        try {
           c = JAXBContext.newInstance(Persona.class);
       
            Marshaller m = c.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(p,new File("persona.xml") );
            m.marshal( p, System.out );
        } catch (JAXBException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
           UNMARSHAL
        */
        try {
            Unmarshaller u = c.createUnmarshaller();
            File xmlData = new File( "persona.xml" );
            Persona pUnmarshalled = (Persona) u.unmarshal(xmlData);
            System.out.println(
                    "Persona Unmarshalled from file:\n"
                    + pUnmarshalled.toString() 
            );
        } catch (JAXBException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        USING AN XSD VALIDATOR
        */
        
        try {
            SchemaFactory schemaF = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File f = new File("xdsPersonaValidator.xml");
            Schema validator = schemaF.newSchema(f);
            JAXBContext jc = JAXBContext.newInstance(Persona.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setSchema(validator);
            //set  a custom handler
            /*
            NB Nel file xdsPersonaValidator.xml l'elenco dei parametri é ordinato,
               se non si rispetta l'ordine del file xml viene generato un errore.
            */
            marshaller.setEventHandler((ValidationEventHandler) new XDSValidationErrorHandler());
            System.out.println("Marshalling with the xds validator:\n...\n");
            marshaller.marshal(p, System.out);
            
            Persona pwrong = new Persona();
            pwrong.setBirthdate(LocalDate.now());
            pwrong.setName("Ho@ @sbagliato");
            pwrong.setSurname("Scusa@@@@@");
            marshaller.marshal(pwrong, System.out);
            
            
        } catch (SAXException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
}
