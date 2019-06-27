/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaws;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author nds
 */
class MapAdapter extends XmlAdapter<String, Map<String,Integer> >{

    @Override
    public Map<String, Integer> unmarshal(String vt) throws Exception {
        String [] kvPairs = vt.split(";");
        Map<String,Integer> costs = new HashMap<>();
        for(int i=0; i< kvPairs.length; i++){
            
            String[] pair = kvPairs[i].split(",");
            costs.put(
                    pair[0], 
                    Integer.parseInt(pair[1]) 
            );
        }
        return costs;
        
    }

    @Override
    public String marshal(Map<String, Integer> bt) throws Exception {
        Set es = bt.entrySet();
        String xmlData = "";
        Iterator it = es.iterator();
        while (it.hasNext() ){
            Entry<String,Integer> next = (Entry<String,Integer>) it.next();
            xmlData += next.getKey()+","+next.getValue().toString()+";";
        }
        return xmlData;
    }

   
}
