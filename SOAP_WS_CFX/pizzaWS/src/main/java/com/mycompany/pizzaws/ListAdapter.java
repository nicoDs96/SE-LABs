/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaws;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author nds
 */
class ListAdapter extends XmlAdapter< String, List<String> >{

    @Override
    public List<String> unmarshal(String vt) throws Exception {
        List<String> menu = new LinkedList<>();
        String [] elms = vt.split(",");
        for(int i =0; i<elms.length;i++){
            menu.add(elms[i]);
        }
        return menu;
    }

    @Override
    public String marshal(List<String> bt) throws Exception {
        String out = "";
        for(String s: bt){
            out += s+",";
        }
        return out;
    }
    
}
