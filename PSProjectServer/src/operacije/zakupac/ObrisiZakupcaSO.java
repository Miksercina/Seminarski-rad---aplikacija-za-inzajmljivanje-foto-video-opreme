/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.zakupac;

import domain.Zakupac;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class ObrisiZakupcaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Zakupac)){
            throw new Exception("Sistem ne može da obriše zakupca.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        broker.delete((Zakupac)param);
    }
    
}
