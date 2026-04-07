/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.zakupodavac;

import domain.Zakupodavac;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class VratiZakupodavceSO extends ApstraktnaGenerickaOperacija {
    List<Zakupodavac> zakupodavci;

    public List<Zakupodavac> getZakupodavci() {
        return zakupodavci;
    }
    
    @Override
    protected void preduslovi(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        zakupodavci = broker.getAll(new Zakupodavac(),"");
    }
    
}
