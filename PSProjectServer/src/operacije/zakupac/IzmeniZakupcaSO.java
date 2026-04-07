/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.zakupac;

import domain.Zakupac;
import exception.ZakupacVecPostojiException;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class IzmeniZakupcaSO extends ApstraktnaGenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Zakupac)){
            throw new Exception("Sistem ne može da izmeni zakupca");
        }
        Zakupac z = (Zakupac) param;
        String uslov = " JOIN grad ON zakupac.postanskiBroj=grad.postanskiBroj WHERE email='" + z.getEmail() + "'";
        Zakupac postojeci = (Zakupac) broker.get(z, uslov);
        if(postojeci != null && !postojeci.getIdZakupac().equals(z.getIdZakupac())) 
            throw new ZakupacVecPostojiException("Sistem ne može da zapamti zakupca.");
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        broker.edit((Zakupac)param);
    }
    
}
