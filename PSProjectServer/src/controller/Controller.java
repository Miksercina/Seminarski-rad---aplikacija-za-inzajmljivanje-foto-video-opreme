/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Brend;
import domain.Grad;
import domain.Oprema;
import domain.StavkaNajma;
import domain.UgovorONajmu;
import domain.Zakupac;
import domain.Zakupodavac;
import java.util.ArrayList;
import java.util.List;
import operacije.brend.UbaciBrendSO;
import operacije.oprema.DodajOpremuSO;
import operacije.oprema.VratiOpremuSO;
import operacije.stavke.VratiStavkeSO;
import operacije.ugovoronajmu.IzmeniUgovorONajmuSO;
import operacije.ugovoronajmu.KreirajUgovorONajmuSO;
import operacije.ugovoronajmu.VratiUgovoreONajmuUlogovanogSO;
import operacije.ugovoronajmu.VratiUgovoreONajmuSO;
import operacije.zakupac.DodajZakupcaSO;
import operacije.zakupac.IzmeniZakupcaSO;
import operacije.zakupac.ObrisiZakupcaSO;
import operacije.zakupac.UcitajZakupceSO;
import operacije.zakupodavac.VratiZakupodavceSO;
import opreacije.login.LoginSO;
import opreacije.grad.VratiGradoveSO;

/**
 *
 * @author Lenovo
 */
public class Controller {
    private static Controller instance;
    private static List<String> ulogovaniKorisnici = new ArrayList<>();

    public Controller() {
    }

    public static Controller getInstance() {
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

    public static List<String> getUlogovanиKorisnici() {
        return ulogovaniKorisnici;
    }
    

    public Zakupodavac login(Zakupodavac zakupodavac) throws Exception {
        LoginSO loginso  = new LoginSO();
        loginso.izvrsi(zakupodavac, null);
        System.out.println("KLASA CONTROLLER: " + loginso.getUlogovaniZakupodavac());
        return loginso.getUlogovaniZakupodavac();
    }

    public List<Grad> vratiGradove() throws Exception {
        VratiGradoveSO vratiGradoveSO = new VratiGradoveSO();
        vratiGradoveSO.izvrsi(null, null);
        System.out.println("KLASA CONTROLLER: " + vratiGradoveSO.getGradovi());
        return vratiGradoveSO.getGradovi();
    }

    public void dodajZakupca(Zakupac zakupac) throws Exception {
        DodajZakupcaSO dzso = new DodajZakupcaSO();
        dzso.izvrsi(zakupac, null);
    }

    public void dodajBrend(Brend b) throws Exception{
        UbaciBrendSO dbso = new UbaciBrendSO();
        dbso.izvrsi(b, null);
    }

    public void dodajOpremu(Oprema o) throws Exception {
        DodajOpremuSO doso = new DodajOpremuSO();
        doso.izvrsi(o, null);
    }

    public List<Zakupac> vratiZakupce() throws Exception {
        UcitajZakupceSO uzso = new UcitajZakupceSO();
        uzso.izvrsi(null, null);
        System.out.println("KLASA CONTROLLER: " + uzso.getZakupci());
        return uzso.getZakupci();
    }

    public void obrisiZakupca(Zakupac zakupac1) throws Exception {
        ObrisiZakupcaSO ozso = new ObrisiZakupcaSO();
        ozso.izvrsi(zakupac1, null);
    }

    public void izmeniZakupca(Zakupac zakupac2) throws Exception {
        IzmeniZakupcaSO izso = new IzmeniZakupcaSO();
        izso.izvrsi(zakupac2, null);
    }

    public List<Zakupodavac> vratiZakupodavce() throws Exception {
        VratiZakupodavceSO vzso = new VratiZakupodavceSO();
        vzso.izvrsi(null, null);
        System.out.println("KLASA CONTROLLER: "+vzso.getZakupodavci());
        return vzso.getZakupodavci();
    }
    public List<Oprema> vratiOpremu() throws Exception{
        VratiOpremuSO voso = new VratiOpremuSO();
        voso.izvrsi(null, null);
        System.out.println("KLASA CONTROLLER: "+voso.getOprema());
        return voso.getOprema();
    }

    public List<StavkaNajma> vratiStavke(Long id) throws Exception {
        VratiStavkeSO vsso = new VratiStavkeSO();
        vsso.izvrsi(id, null);
        return vsso.getStavke();
    }
    public List<UgovorONajmu> vratiUgovoreONajmu() throws Exception{
        VratiUgovoreONajmuSO vso = new VratiUgovoreONajmuSO();
        vso.izvrsi(null, null);
        return vso.getUgovori();
    }
    public void dodajUgovor(UgovorONajmu u) throws Exception {
        KreirajUgovorONajmuSO operacija = new KreirajUgovorONajmuSO();
        operacija.izvrsi(u, null);
    
    }
    public List<UgovorONajmu> vratiUgovoreUlogovanog(Zakupodavac ulogovani) throws Exception {
        VratiUgovoreONajmuUlogovanogSO operacija = new VratiUgovoreONajmuUlogovanogSO();
        operacija.izvrsi(ulogovani, null);
        return operacija.getLista();
    }
    public void izmeniEvidencijuRadionice(UgovorONajmu u) throws Exception {
        IzmeniUgovorONajmuSO operacija = new IzmeniUgovorONajmuSO();
        operacija.izvrsi(u, null);
    }
    
}
