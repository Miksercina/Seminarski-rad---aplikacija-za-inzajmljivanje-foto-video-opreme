/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;
import domain.Brend;
import domain.Grad;
import domain.Oprema;
import domain.StavkaNajma;
import domain.UgovorONajmu;
import domain.Zakupac;
import domain.Zakupodavac;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import koordinator.Koordinator;
/**
 *
 * @author Lenovo
 */
public class Komunikacija {
    private Socket socket;
    private Posiljalac posiljalac;
    private Primalac primalac;
    private static Komunikacija instance;
    private Zakupodavac ulogovani;
    
    public Komunikacija() {
    }

    public static Komunikacija getInstance() {
        if(instance == null){
            instance = new Komunikacija();
        }
        return instance;
    }
    public void connection(){
        try {
            socket = new Socket("localhost",9000);
            posiljalac = new Posiljalac(socket);
            primalac = new Primalac(socket);
        } catch (IOException ex) {
            System.out.println("Server nije povezan.");
        }
    }
    public Zakupodavac login(String username, String password) throws Exception{
        Zakupodavac z = new Zakupodavac();
        z.setKorisnickoIme(username);
        z.setSifra(password);
        Zahtev zahtev = new Zahtev(Operacije.LOGIN, z);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        z = (Zakupodavac) odgovor.getOdgovor();
        ulogovani=z;
        return z;
    }

    public List<Grad> vratiListuGradova() throws Exception {
        Zahtev z = new Zahtev(Operacije.VRATI_GRADOVE, null);
        List<Grad> gradovi = new ArrayList<>();
        posiljalac.posalji(z);
        Odgovor odg = (Odgovor) primalac.primi();
        gradovi = (List<Grad>) odg.getOdgovor();
        return gradovi;
    }

    

    public void dodajZakupca(Zakupac z) throws Exception {
        Zahtev z1 = new Zahtev(Operacije.DODAJ_ZAKUPCA, z);
        posiljalac.posalji(z1);
        Odgovor o1 = (Odgovor) primalac.primi();
        if(o1.getOdgovor() == null){
            System.out.println("USPEŠNO");
        }else{
            Exception e = (Exception) o1.getOdgovor();
            throw e;
        }
        
    }

    public void dodajBrend(Brend b) throws Exception {
        Zahtev z2 = new Zahtev(Operacije.DODAJ_BREND, b);
        posiljalac.posalji(z2);
        Odgovor o2 = (Odgovor) primalac.primi();
        System.out.println("PRIMLJENI ODGOVOR: " + o2); 
        if(o2 == null){
            System.out.println("O2 JE NULL");
            return;
        }
        if(o2.getOdgovor() == null){
            System.out.println("USPEŠNO");
        }else{
            Exception e1 = (Exception) o2.getOdgovor();
            throw e1;
        }
        
        
    }

    public void dodajOpremu(Oprema o) throws Exception {
        Zahtev z3 = new Zahtev(Operacije.DODAJ_OPREMU,o);
        posiljalac.posalji(z3);
        Odgovor o3 = (Odgovor) primalac.primi();
        if(o3.getOdgovor() == null){
            System.out.println("USPEŠNO");
        }else{
            Exception e2 = (Exception) o3.getOdgovor();
            throw e2;
        }
    }

    public List<Zakupac> vratiZakupce() throws Exception {
        Zahtev z4 = new Zahtev(Operacije.VRATI_ZAKUPCE,null);
        posiljalac.posalji(z4);
        Odgovor o4 = (Odgovor) primalac.primi();
        List<Zakupac> lista = (List<Zakupac>) o4.getOdgovor();
            return lista;
        
    }

    public void obrisiZakupca(Zakupac z) throws Exception {
        Zahtev z5 = new Zahtev(Operacije.OBRISI_ZAKUPCA, z);
        posiljalac.posalji(z5);
        Odgovor o5 = (Odgovor) primalac.primi();
        if(o5.getOdgovor() == null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception) o5.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }
    public void azurirajZakupca(Zakupac z) throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.IZMENI_ZAKUPCA, z);
        posiljalac.posalji(zahtev);
         Odgovor odgovor = (Odgovor) primalac.primi();
        if(odgovor.getOdgovor()==null){
            System.out.println("USPEŠNO");
            Koordinator.getInstance().osveziFormu();
        }else{
            Exception e = (Exception) odgovor.getOdgovor();
            throw e;
        }
        
    }

    public Zakupodavac vratiZakupodavca() {
        return ulogovani;
    }

    public List<Zakupodavac> vratiZakupodavce() throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.VRATI_ZAKUPODAVCE,null);
        List<Zakupodavac> treneri = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        treneri = (List<Zakupodavac>) odg.getOdgovor();
        return treneri;
    }

    public List<Oprema> vratiOpremu() throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.VRATI_OPREMU,null);
        List<Oprema> oprema = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        oprema = (List<Oprema>) odg.getOdgovor();
        return oprema;
    }

    public List<StavkaNajma> vratiStavke(Long idUgovorONajmu) throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.VRATI_STAVKE, idUgovorONajmu);
        List<StavkaNajma> stavke = new ArrayList<>(); 
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        stavke = (List<StavkaNajma>) odg.getOdgovor();
        return stavke;
    }

    public List<UgovorONajmu> vratiUgovoreONajmu() throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.VRATI_UGOVORE, null);
        List<UgovorONajmu> ugovori = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        ugovori = (List<UgovorONajmu>) odg.getOdgovor();
        return ugovori;
    }
    public void dodajUgovor(UgovorONajmu u) throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.DODAJ_UGOVOR, u);
        posiljalac.posalji(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primi();


        if (odgovor.getOdgovor() == null) {
            System.out.println("USPEŠNO");
        } else {
            Exception e = (Exception) odgovor.getOdgovor();
            throw e;
        }
    }
    public List<UgovorONajmu> vratiUgovoreUlogovanog(Zakupodavac ulogovani) throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.VRATI_UGOVORE_ULOGOVANOG, ulogovani);
        List<UgovorONajmu> evidencije = new ArrayList<>();
           
        posiljalac.posalji(zahtev);
        
        Odgovor odg = (Odgovor) primalac.primi();
        evidencije = (List<UgovorONajmu>) odg.getOdgovor();
        return evidencije;
    }

    public void izmeniEvidenciju(UgovorONajmu u) throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.IZMENI_UGOVOR, u);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("USPESNO");
        } else {
            System.out.println("NEUSPESNO");
        }
    }

    public void logout(Zakupodavac z) throws Exception {
        Zahtev zahtev = new Zahtev(Operacije.LOGOUT, z);
         posiljalac.posalji(zahtev);
        Odgovor odgovor = (Odgovor) primalac.primi();
    }
    
}
