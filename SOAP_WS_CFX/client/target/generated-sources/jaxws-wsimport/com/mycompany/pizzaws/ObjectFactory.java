
package com.mycompany.pizzaws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mycompany.pizzaws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCosts_QNAME = new QName("http://pizzaws.mycompany.com/", "getCosts");
    private final static QName _GetCostsResponse_QNAME = new QName("http://pizzaws.mycompany.com/", "getCostsResponse");
    private final static QName _GetMenu_QNAME = new QName("http://pizzaws.mycompany.com/", "getMenu");
    private final static QName _GetMenuResponse_QNAME = new QName("http://pizzaws.mycompany.com/", "getMenuResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mycompany.pizzaws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMenuResponse }
     * 
     */
    public GetMenuResponse createGetMenuResponse() {
        return new GetMenuResponse();
    }

    /**
     * Create an instance of {@link GetCosts }
     * 
     */
    public GetCosts createGetCosts() {
        return new GetCosts();
    }

    /**
     * Create an instance of {@link GetCostsResponse }
     * 
     */
    public GetCostsResponse createGetCostsResponse() {
        return new GetCostsResponse();
    }

    /**
     * Create an instance of {@link GetMenu }
     * 
     */
    public GetMenu createGetMenu() {
        return new GetMenu();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCosts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzaws.mycompany.com/", name = "getCosts")
    public JAXBElement<GetCosts> createGetCosts(GetCosts value) {
        return new JAXBElement<GetCosts>(_GetCosts_QNAME, GetCosts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCostsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzaws.mycompany.com/", name = "getCostsResponse")
    public JAXBElement<GetCostsResponse> createGetCostsResponse(GetCostsResponse value) {
        return new JAXBElement<GetCostsResponse>(_GetCostsResponse_QNAME, GetCostsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMenu }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzaws.mycompany.com/", name = "getMenu")
    public JAXBElement<GetMenu> createGetMenu(GetMenu value) {
        return new JAXBElement<GetMenu>(_GetMenu_QNAME, GetMenu.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMenuResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzaws.mycompany.com/", name = "getMenuResponse")
    public JAXBElement<GetMenuResponse> createGetMenuResponse(GetMenuResponse value) {
        return new JAXBElement<GetMenuResponse>(_GetMenuResponse_QNAME, GetMenuResponse.class, null, value);
    }

}
