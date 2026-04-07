/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;
import controller.Controller;
import domain.Brend;
import domain.Grad;
import domain.Oprema;
import domain.StavkaNajma;
import domain.UgovorONajmu;
import domain.Zakupac;
import domain.Zakupodavac;
import exception.BrendVecPostojiException;
import exception.OpremaVecPostojiException;
import exception.ZakupacVecPostojiException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import komunikacija.Odgovor;
import komunikacija.Posiljalac;
import komunikacija.Primalac;
import komunikacija.Zahtev;
import server.sesija.ServerSesija;
/**
 *
 * @author Lenovo
 */
public class ObradaKlijentskihZahteva extends Thread {
    private Socket socket;
    private Posiljalac posiljalac;
    private Primalac primalac;
    private boolean kraj = false;

    public ObradaKlijentskihZahteva(Socket socket) {
        this.socket = socket;
        posiljalac = new Posiljalac(socket);
        primalac = new Primalac(socket);
    }

    @Override
    public void run() {
        while(!kraj){
            
            Object obj = primalac.primi();
            if(obj == null){
                System.out.println("Klijent je zatvorio vezu,nit se prekida.");
                prekini();
                break;
            }
            try {
                    Zahtev z = (Zahtev) obj;
                    Odgovor odgovor = new Odgovor();
                    switch (z.getOperacija()) {
                     case LOGIN:
                        try{
                            Zakupodavac zakupodavac = (Zakupodavac) z.getParametar();
                            zakupodavac = controller.Controller.getInstance().login(zakupodavac);
                            odgovor.setOdgovor(zakupodavac);
                        }catch(Exception e){
                            odgovor.setOdgovor(e);
                        }
                        break;
                     case DODAJ_ZAKUPCA:
                        Zakupac zakupac = (Zakupac) z.getParametar();
                        try{
                            controller.Controller.getInstance().dodajZakupca(zakupac);
                            odgovor.setOdgovor(null);
                        }catch(ZakupacVecPostojiException zvpe){
                            odgovor.setOdgovor(zvpe);
                        }catch (Exception ex){
                            odgovor.setOdgovor(ex);
                        }
                        break;
                     case VRATI_GRADOVE:
                        List<Grad> lista = controller.Controller.getInstance().vratiGradove();
                        odgovor.setOdgovor(lista);
                        break;
                    case DODAJ_BREND:
                        Brend b = (Brend) z.getParametar();
                        try {
                            controller.Controller.getInstance().dodajBrend(b);
                            odgovor.setOdgovor(null); 
                        } catch (BrendVecPostojiException be) {
                            odgovor.setOdgovor(be);
                        } catch (Exception e) {
                            odgovor.setOdgovor(e);
                        }
                        break;
                     case DODAJ_OPREMU:
                        Oprema o = (Oprema) z.getParametar();
                        try{
                            controller.Controller.getInstance().dodajOpremu(o);
                            odgovor.setOdgovor(null);
                        }catch(OpremaVecPostojiException ove){
                            odgovor.setOdgovor(ove);
                        }catch(Exception e){
                            odgovor.setOdgovor(e);
                        }
                         break;
                     case VRATI_ZAKUPCE:
                        List<Zakupac> zakupci = controller.Controller.getInstance().vratiZakupce();
                        odgovor.setOdgovor(zakupci);
                        break;
                     case OBRISI_ZAKUPCA:
                        Zakupac zakupac1 = (Zakupac) z.getParametar();
                        controller.Controller.getInstance().obrisiZakupca(zakupac1);
                        odgovor.setOdgovor(null);
                        break;
                     case IZMENI_ZAKUPCA:
                        Zakupac zakupac2 = (Zakupac) z.getParametar();
                        try {
                        controller.Controller.getInstance().izmeniZakupca(zakupac2);
                        odgovor.setOdgovor(null);
                        }catch (ZakupacVecPostojiException zvp) {
                            odgovor.setOdgovor(zvp);
                        }catch (Exception excp) {
                            odgovor.setOdgovor(excp);
                        }
                         break;
                     case VRATI_ZAKUPODAVCE:
                        List<Zakupodavac> treneri = Controller.getInstance().vratiZakupodavce();
                        odgovor.setOdgovor(treneri);
                        break;
                     case VRATI_OPREMU:
                        List<Oprema> oprema = controller.Controller.getInstance().vratiOpremu();
                        odgovor.setOdgovor(oprema);
                        break;
                     case VRATI_STAVKE:
                        List<StavkaNajma> stavke = controller.Controller.getInstance().vratiStavke((Long)z.getParametar());
                        odgovor.setOdgovor(stavke);
                        break;
                     case VRATI_UGOVORE:
                        List<UgovorONajmu> ugovori = controller.Controller.getInstance().vratiUgovoreONajmu();
                        odgovor.setOdgovor(ugovori);
                        break;
                     case DODAJ_UGOVOR:
                        try{
                            UgovorONajmu u = (UgovorONajmu) z.getParametar();
                            controller.Controller.getInstance().dodajUgovor(u);
                            odgovor.setOdgovor(null);
                        } catch (Exception excp) {
                            odgovor.setOdgovor(excp);
                        }
                         break;
                     case IZMENI_UGOVOR:
                        try{   
                            UgovorONajmu u = (UgovorONajmu) z.getParametar();
                            controller.Controller.getInstance().izmeniEvidencijuRadionice(u);
                            odgovor.setOdgovor(null);
                        } catch(Exception ex) {
                            ex.printStackTrace();
                            odgovor.setOdgovor(ex); 
                        }
                        break;
                     case VRATI_UGOVORE_ULOGOVANOG:
                        Zakupodavac ulogovani = (Zakupodavac) z.getParametar();
                        List<UgovorONajmu> ugovoriUlogovanogZakupodavca = controller.Controller.getInstance().vratiUgovoreUlogovanog(ulogovani);
                        odgovor.setOdgovor(ugovoriUlogovanogZakupodavca);
                        break;
                     case LOGOUT:
                        Zakupodavac z2 = (Zakupodavac) z.getParametar();
                        ServerSesija.getInstance().ukloniKorisnika(z2.getKorisnickoIme());
                        posiljalac.posalji(new Odgovor(null));
                        break;
                     default:
                        System.out.println("GRESKA, NE POSTOJI OPERACIJA");
                }
                posiljalac.posalji(odgovor);
                System.out.println("ODGOVOR POSLAT: " + odgovor.getOdgovor());
            } catch (Exception ex) {
                ex.printStackTrace();
                prekini(); 
                break;
            }
        }
    }

   

    public void prekini() {
        kraj = true;
        try{ 
            if(socket != null && !socket.isClosed()){
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
       
        interrupt();
        
    }
    
    
}
