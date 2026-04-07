/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package opreacije.grad;

import domain.Grad;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class VratiGradoveSO extends ApstraktnaGenerickaOperacija{
    List<Grad> gradovi;
    @Override
    protected void preduslovi(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        gradovi = broker.getAll(new Grad(),"");
    }

    public List<Grad> getGradovi() {
        return gradovi;
    }
    
}
