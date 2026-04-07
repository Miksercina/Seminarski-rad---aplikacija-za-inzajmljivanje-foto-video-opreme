/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.oprema;

import domain.Oprema;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class VratiOpremuSO extends ApstraktnaGenerickaOperacija {
    List<Oprema> oprema;

    public List<Oprema> getOprema() {
        return oprema;
    }
    
    @Override
    protected void preduslovi(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        oprema = broker.getAll(new Oprema(),"");
    }
    
}
