/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.brend;

import domain.Brend;
import exception.BrendVecPostojiException;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class UbaciBrendSO extends ApstraktnaGenerickaOperacija{
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Brend)){
            throw new Exception("Sistem ne može da zapamti uneti brend");
        }
        Brend b = (Brend) param;
        String uslov = " WHERE nazivOsiguravajuceKuce='" + b.getNazivOsiguravajuceKuce()+"'";
        Brend postojeci = (Brend) broker.get(b, uslov);
        if(postojeci != null){
            throw new BrendVecPostojiException("Sistem ne može uneti brend jer on već postoji.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        broker.add((Brend)param);
    }
    
}
