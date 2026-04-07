/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Zakupodavac;
import forme.GlavnaForma;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import koordinator.Koordinator;

/**
 *
 * @author Lenovo
 */
public class GlavnaFormaController {
    private static GlavnaFormaController instance;
    private GlavnaForma gf;

    public GlavnaFormaController(GlavnaForma gf) {
        this.gf = gf;
    }

    public static GlavnaFormaController getInstance() {
        if(instance == null){
            instance = new GlavnaFormaController(new GlavnaForma());
        }
        return instance;
    }

    public void otvoriFormu() {
        Zakupodavac ulogovani = Koordinator.getInstance().getUlogovani();
        gf.setVisible(true);
        gf.getjLabelPozdrav().setText("Zdravo, " + ulogovani.getKorisnickoIme());

        // DODAJ OVO
        gf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    komunikacija.Komunikacija.getInstance().logout(Koordinator.getInstance().getUlogovani());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
}
