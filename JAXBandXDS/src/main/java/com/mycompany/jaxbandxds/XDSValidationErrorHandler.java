/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaxbandxds;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import jdk.internal.org.xml.sax.ErrorHandler;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.SAXParseException;

/**
 *
 * @author nds
 */
public class XDSValidationErrorHandler implements ValidationEventHandler {

    @Override
    public boolean handleEvent(ValidationEvent event) {
        System.out.println( "Error catched!!" );
        System.out.println( "event.getSeverity():  " + event.getSeverity() );
        System.out.println( "event:  " + event.getMessage() );
        System.out.println( "event.getLinkedException():  " + event.getLinkedException() );
        //the locator contains much more information like line, column, etc.
        System.out.println( "event.getLocator().getObject():  " + event.getLocator().getObject() );
        return false;
    }

 
    
}
