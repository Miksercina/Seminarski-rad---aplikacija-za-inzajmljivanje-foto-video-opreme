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
public class DodajZakupcaSO extends ApstraktnaGenerickaOperacija{
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Zakupac)){
            throw new Exception("Sistem ne može da zapamti unetog zakupca.");
        }
        Zakupac z = (Zakupac) param;
        String uslov = " JOIN grad ON zakupac.postanskiBroj=grad.postanskiBroj WHERE zakupac.idZakupac=" + z.getIdZakupac();
        Zakupac postojeci = (Zakupac) broker.get(z, uslov);
        if(postojeci != null){
            throw new ZakupacVecPostojiException("Sistem ne može da doda zakupca koji već postoji u bazi.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        broker.add((Zakupac)param);
    }
    
}
