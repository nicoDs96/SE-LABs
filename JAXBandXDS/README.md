# JAXB  
**J**ava **A**rchitecture for **X**ML **B**inding  

## Rendere una classe serializzabile con XML  
Annotazioni sulla classe che vogliamo trasformare in xml:  
```
 @XmlType( propOrder = { "attr1", "attr2", "attr3",...} )  
 @XmlRootElement(name="ClassNameOrSimilar")  
  Class declaration(){}  
 @XmlElement(name="attributeName", required = true)  
  attributeSetter(){}  
 @XmlAttribute(Name="xmlClassAttributeNmae")  <ClassNameOrSimilar xmlClassAttributeNmae=value>  
```
### NB la classe deve avere un costruttore vuoto, l'oggetto va inizzializzato con i setter di ogni attributo, a meno che non si voglia definire un Adapter personalizzato (NO)  

## Marshalling  
Creare il contesto (trasformare le annotazioni in scheletro xml)  
```
JAXBContext jaxbContext = JAXBContext.newInstance( Country.class );
```
istanziare il marshaller sulla base della classe che si vuole serializzare in XML  
```
Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
```
format the XML  
```
jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
```
Marshalling su file e std out Stream:  
```
jaxbMarshaller.marshal( spain, new File( "country.xml" ) );  
jaxbMarshaller.marshal( spain, System.out ); 
```    
## UnMarshalling  
Read the xml file to unmarshal  
```
File file = new File( "countries.xml" );
```
istanziare il contesto: (classe di partenza da cui si istanzia l'oggetto con gli attributi letti dal file xml)  
```
JAXBContext jaxbContext = JAXBContext.newInstance( Countries.class );
```
Definire l'unmarshaller  
```
Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
```
Instanziare l'oggetto  
```
ClassName objectName = (ClassName)jaxbUnmarshaller.unmarshal( file );
```
Creare una classe Adapter per supportare tipi di dati non standard  
```
public class DateAdapter extends XmlAdapter<toObj, fromObj>{//implementare abstract methods}
```
    
## XSD    
**X**ml **S**schema **D**efinition   
é l'unico standard xml w3. Permette di creare regole di validazione su un file xml.  
Nota bene che qualora si inserscono elementi nel file XSD, l'ordine deve essere lo stesso del file xml (specificato in @XmlType( propOrder = { "attr1", "attr2", "attr3",...} )  ).  

  
