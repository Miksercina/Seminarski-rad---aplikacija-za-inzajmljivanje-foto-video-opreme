/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.oprema;

import domain.Oprema;
import exception.OpremaVecPostojiException;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class DodajOpremuSO extends ApstraktnaGenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Oprema)){
            throw new Exception("Sistem ne može da zapamti uneti opremu");
        }
        Oprema o = (Oprema) param;
        String uslov = " WHERE nazivProizvoda='" + o.getNazivProizvoda() + "' AND nazivProizvodjaca='" + o.getNazivProizvodjaca()+"'";
        Oprema postojeci = (Oprema) broker.get(o, uslov);
        if(postojeci != null){
            throw new OpremaVecPostojiException("Sistem ne može uneti brend jer on već postoji.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        broker.add((Oprema)param);
    }
    
}
