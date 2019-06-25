/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaxbandxds;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author nds
 */
public class DateAdapetr extends XmlAdapter{

    @Override
    public Object unmarshal(Object vt) throws Exception {
        return LocalDate.parse((CharSequence) vt);
    }

    @Override
    public Object marshal(Object birthDate) throws Exception {
        return birthDate.toString();
    }
    
}
