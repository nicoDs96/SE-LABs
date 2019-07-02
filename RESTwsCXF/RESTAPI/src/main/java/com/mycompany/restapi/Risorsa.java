/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restapi;

import java.util.Objects;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Nodes
 */
@XmlRootElement(name="Risorsa")
public class Risorsa {
    private String id;
    private String name;

    public Risorsa() {
        this.id = "";
        this.name= "";
    }

    public String getId() {
        return id;
    }
    @XmlElement(name="identifier")
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Risorsa other = (Risorsa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Risorsa{" + "id=\t" + id + ",\tname=\t" + name + "\t}";
    }
    
    
    
    
}
