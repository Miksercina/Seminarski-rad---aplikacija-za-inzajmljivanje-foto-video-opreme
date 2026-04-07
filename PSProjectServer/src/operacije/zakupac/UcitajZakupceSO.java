/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.zakupac;

import domain.Zakupac;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class UcitajZakupceSO extends ApstraktnaGenerickaOperacija{
    List<Zakupac> zakupci;
    @Override
    protected void preduslovi(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        String uslov = " JOIN grad ON zakupac.postanskiBroj=grad.postanskiBroj";
        zakupci = broker.getAll(new Zakupac(),uslov);
    }

    public List<Zakupac> getZakupci() {
        return zakupci;
    }
    
}
