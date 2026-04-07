/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package opreacije.login;

import domain.Zakupodavac;
import operacije.ApstraktnaGenerickaOperacija;
import server.sesija.ServerSesija;

/**
 *
 * @author Lenovo
 */
public class LoginSO extends ApstraktnaGenerickaOperacija{
    Zakupodavac zakupodavac;
    
    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Zakupodavac)){
            throw new Exception("Sistem ne moze da pronadje unetog zakupodavca.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        Zakupodavac zakupodavac = (Zakupodavac) param;
        String uslov = " WHERE korisnickoIme= '" + zakupodavac.getKorisnickoIme() 
                + "' AND sifra='" + zakupodavac.getSifra() + "'";

        zakupodavac = (Zakupodavac) broker.get(zakupodavac, uslov);

        if (zakupodavac == null) {
            throw new Exception("Korisničko ime ili šifra nisu ispravni.");
        }

        if (ServerSesija.getInstance().jeUlogovan(zakupodavac.getKorisnickoIme())) {
            throw new Exception("Korisnik je već ulogovan na drugom uređaju.");
        }

        ServerSesija.getInstance().dodajKorisnika(zakupodavac.getKorisnickoIme());
        this.zakupodavac = zakupodavac;
    }

    public Zakupodavac getUlogovaniZakupodavac() {
        return zakupodavac;
    }

    
    
}
