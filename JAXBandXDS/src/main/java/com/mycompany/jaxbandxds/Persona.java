/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaxbandxds;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author nds
 */
@XmlType(propOrder = {"surname","name","birthdate"})
@XmlRootElement(name = "Persona")
public class Persona {
    private String name;
    private String surname;
    private LocalDate birthdate;

    public Persona() {
    }

    public String getName() {
        return name;
    }
    
    @XmlElement(name="PersonName")
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    
    @XmlElement(name="PersonSurname")
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }
    
    //tell the class to use the adapter since LocalDate is not a supported format
    @XmlElement(name="PersonBDay")
    @XmlJavaTypeAdapter(DateAdapetr.class)
    public void setBirthdate(LocalDate bithdate) {
        this.birthdate = bithdate;
    }

    @Override
    public String toString() {
        return "Persona{\t" + "name=" + name + ", \t surname=" + surname + ", \t bithdate=" + birthdate + "}\n";
    }
   
    
}
