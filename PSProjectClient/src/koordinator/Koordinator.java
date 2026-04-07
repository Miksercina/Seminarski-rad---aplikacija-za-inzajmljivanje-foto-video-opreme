/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koordinator;

import controller.UbaciOpremuController;
import controller.GlavnaFormaController;
import controller.UbaciBrendController;
import controller.KreirajZakupcaController;
import controller.IzmeniUgovorONajmuController;
import controller.KreirajUgovorONajmuController;
import controller.LoginController;
import controller.PrikazUgovorONajmuController;
import controller.PrikazZakupacaController;
import domain.Zakupac;
import domain.Zakupodavac;
import forme.UbaciOpremuForma;
import forme.FormaMod;
import forme.GlavnaForma;
import forme.UbaciBrendForma;
import forme.KreirajZakupcaForma;
import forme.IzmeniUgovorONajmuForma;
import forme.KreirajUgovorONajmuForma;
import forme.LoginForma;
import forme.PrikazZakupacaForma;
import forme.PrikazUgovorONajmuForma;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;

/**
 *
 * @author Lenovo
 */
public class Koordinator {
    private static Koordinator instance;
    private Map<String, Object> parametri;
    private LoginController loginController;
    private UbaciBrendController kreirajBrendController;
    private GlavnaFormaController glavnaFormaController;
    private UbaciOpremuController dodajOpremuController;
    private KreirajZakupcaController kzController;
    private PrikazZakupacaController pzController;
    private KreirajUgovorONajmuController kuoncController;
    private PrikazUgovorONajmuController puController;
    private IzmeniUgovorONajmuController iuController;
    
    private Zakupodavac ulogovani;
    private Koordinator() {
        parametri = new HashMap<>();
    }
    public static void applyGlobalTheme(){
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.background", new Color(45,137,239));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.focusPainted", false);
        
    }

    public static Koordinator getInstance() {
        if(instance == null){
            instance = new Koordinator();
            applyGlobalTheme();
        }
        return instance;
    }
    public void otvoriLoginFormu(){
        loginController = new LoginController(new LoginForma());
        loginController.otvoriFormu();
    }

    public Zakupodavac getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Zakupodavac ulogovani) {
        this.ulogovani = ulogovani;
    }

    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }

    public void otvoriDodajZakupcaFormu() {
        kzController = new KreirajZakupcaController(new KreirajZakupcaForma());
        kzController.otvoriFormu(FormaMod.DODAJ);
    }
    public Object vratiParam(String s){
        return parametri.get(s);
    }

    public void otvoriDodajBrendFormu() {
        kreirajBrendController = new UbaciBrendController(new UbaciBrendForma());
        kreirajBrendController.otvoriFormu();
    }

    public void otvoriDodajOpremuForma() {
        dodajOpremuController = new UbaciOpremuController(new UbaciOpremuForma());
        dodajOpremuController.otvoriFormu();
    }

    public void dodajParam(String s, Object k) {
        parametri.put(s,k);
    }

    public void otvoriDetaljiZakupcaFormu() {
        kzController = new KreirajZakupcaController(new KreirajZakupcaForma());
        kzController.otvoriFormu(FormaMod.DETALJI);
    }

    public void otvoriIzmeniZakupcaFormu() {
        kzController = new KreirajZakupcaController(new KreirajZakupcaForma());
        kzController.otvoriFormu(FormaMod.IZMENI);
    }

    public void osveziFormu() throws Exception {
        pzController = new PrikazZakupacaController(new PrikazZakupacaForma());
        pzController.osveziFormu();
    }

    public void otvoriPrikazZakupaca() throws Exception {
        pzController = new PrikazZakupacaController(new PrikazZakupacaForma());
        pzController.otvoriFormu();
    }

    public void otvoriKreirajUgovorONajmu() throws Exception {
        kuoncController = new KreirajUgovorONajmuController(new KreirajUgovorONajmuForma());
        kuoncController.otvoriFormu(FormaMod.DODAJ);
    }
    public void otvoriDetaljiUgovorONajmu() throws Exception {
        kuoncController = new KreirajUgovorONajmuController(new KreirajUgovorONajmuForma());
        kuoncController.otvoriFormu(FormaMod.DETALJI);
    }
    public void otvoriIzmeniUgovorONajmu() throws Exception {
        iuController = new IzmeniUgovorONajmuController(new IzmeniUgovorONajmuForma());
        iuController.otvoriFormu();
    }

    public void otvoriPrikazUgovorONajmuFormu() throws Exception {
        puController = new PrikazUgovorONajmuController(new PrikazUgovorONajmuForma());
        puController.otvoriFormu();
    }

    public void otvoriIzmeniUgovorONajmuUlogovanogFormu() throws Exception {
        kuoncController = new KreirajUgovorONajmuController(new KreirajUgovorONajmuForma());
        kuoncController.otvoriFormu(FormaMod.IZMENI);
    }

    public void obrisiParam(String kljuc) {
        parametri.remove(kljuc);
    }
    
}
