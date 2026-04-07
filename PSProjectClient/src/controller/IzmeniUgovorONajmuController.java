/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.StavkaNajma;
import domain.UgovorONajmu;
import domain.Zakupac;
import domain.Zakupodavac;
import forme.IzmeniUgovorONajmuForma;
import forme.model.ModelTabeleStavka;
import forme.model.ModelTabeleUgovorONajmu;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author Lenovo
 */
public class IzmeniUgovorONajmuController {
    private final IzmeniUgovorONajmuForma iuf;
    List<StavkaNajma> originalneStavke;

    public List<StavkaNajma> getOriginalneStavke() {
        return originalneStavke;
    }

    public void setOriginalneStavke(List<StavkaNajma> originalneStavke) {
        this.originalneStavke = originalneStavke;
    }
    

    public IzmeniUgovorONajmuController(IzmeniUgovorONajmuForma iuf) {
        this.iuf = iuf;
        applyTheme();
        addActionListener();
        addMouseListener();
    }
    public void osveziFormu() throws Exception {
        pripremiFormu();
    }
    public void otvoriFormu() throws Exception {
        pripremiFormu();
        iuf.setVisible(true);
        iuf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        iuf.setTitle("Izmena ugovora");
    }

    private void applyTheme() {
        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontButton = new Font("Segoe UI", Font.BOLD, 14);

       
        JComboBox<?>[] combos = {iuf.getjComboBoxZakupac()};
        for (JComboBox<?> cb : combos) {
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            cb.setBackground(Color.WHITE);
        }
    
        iuf.getjButtonPretrazi().setFont(fontButton);
        iuf.getjButtonIzmeni().setFont(fontButton);
        iuf.getjButtonResetuj().setFont(fontButton);
        
        
        Color primary = new Color(70, 130, 180);
        Color danger = new Color(220, 53, 69);

        iuf.getjButtonPretrazi().setBackground(primary);
        iuf.getjButtonPretrazi().setForeground(Color.WHITE);
        iuf.getjButtonPretrazi().setFocusPainted(false);
        iuf.getjButtonPretrazi().setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        iuf.getjButtonResetuj().setBackground(new Color(100, 149, 237));
        iuf.getjButtonResetuj().setForeground(Color.WHITE);
        iuf.getjButtonResetuj().setFocusPainted(false);
        iuf.getjButtonResetuj().setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        iuf.getjButtonIzmeni().setBackground(danger);
        iuf.getjButtonIzmeni().setForeground(Color.WHITE);
        iuf.getjButtonIzmeni().setFocusPainted(false);
        iuf.getjButtonIzmeni().setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        
      

       
        iuf.getjTableUgovori().setFont(fontLabel);
        iuf.getjTableUgovori().setBackground(Color.WHITE);
        iuf.getjTableStavke().setFont(fontLabel);
        iuf.getjTableStavke().setBackground(Color.WHITE);
    }

    private void addActionListener() {
        iuf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zakupac k = (Zakupac) iuf.getjComboBoxZakupac().getSelectedItem();
                
                
                
                ModelTabeleUgovorONajmu mtu = (ModelTabeleUgovorONajmu) iuf.getjTableUgovori().getModel();
                
                mtu.pretrazi(k);
                if (mtu.getLista().isEmpty()) {
                    JOptionPane.showMessageDialog(iuf, "Sistem ne može da nađe ugovore o najmu po zadatim kriterijumima.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(iuf, "Sistem je našao ugovore o najmu po zadatim kriterijumima.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
        iuf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pripremiFormu();
                } catch (Exception ex) {
                    Logger.getLogger(PrikazZakupacaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                iuf.getjComboBoxZakupac().setSelectedIndex(0);
               
                
                
            }
        });
        
        iuf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 int red = iuf.getjTableUgovori().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(iuf, "Sistem ne može da nađe ugovore o najmu.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(iuf, "Sistem je našao ugovore o najmu.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleUgovorONajmu mte = (ModelTabeleUgovorONajmu) iuf.getjTableUgovori().getModel();

                    UgovorONajmu evidencijaTreninga = mte.getLista().get(red);
                   Koordinator.getInstance().dodajParam("ugovoronajmu", evidencijaTreninga);
                   iuf.dispose();
                     try {
                         Koordinator.getInstance().otvoriIzmeniUgovorONajmuUlogovanogFormu();
                     } catch (Exception ex) {
                         Logger.getLogger(IzmeniUgovorONajmuController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                }

            }

        });
    }

    private void addMouseListener() {
        iuf.getjTableUgovori().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                int red = iuf.getjTableUgovori().getSelectedRow();
                if(red!=-1){
                ModelTabeleUgovorONajmu mte = (ModelTabeleUgovorONajmu) iuf.getjTableUgovori().getModel();
                UgovorONajmu evidencija = mte.getLista().get(red);
                List<StavkaNajma> stavke;
                    try {
                        stavke = Komunikacija.getInstance().vratiStavke(evidencija.getIdUgovorONajmu());
                        ModelTabeleStavka mts = new ModelTabeleStavka(stavke);
                        iuf.getjTableStavke().setModel(mts);
                    } catch (Exception ex) {
                        Logger.getLogger(PrikazUgovorONajmuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                
                }

            }
    });
    
                }

    private void pripremiFormu() throws Exception {
        Zakupodavac ulogovani = Koordinator.getInstance().getUlogovani();
        List<UgovorONajmu> ugovori = komunikacija.Komunikacija.getInstance().vratiUgovoreUlogovanog(ulogovani);
        ModelTabeleUgovorONajmu mte = new ModelTabeleUgovorONajmu(ugovori);
        iuf.getjTableUgovori().setModel(mte);
        
        List<StavkaNajma> stavke = new ArrayList<>();
        ModelTabeleStavka mts = new ModelTabeleStavka(stavke);
        iuf.getjTableStavke().setModel(mts);
        List<Zakupodavac> treneri = Komunikacija.getInstance().vratiZakupodavce();
        
        
        
        
        
        List<Zakupac> zakupci = Komunikacija.getInstance().vratiZakupce();
        Zakupac prazno = new Zakupac();
        prazno.setIme("Odaberite");
        prazno.setPrezime("zakupca");
        zakupci.add(0, prazno);
        iuf.getjComboBoxZakupac().removeAllItems();
        for (Zakupac z : zakupci) {
            iuf.getjComboBoxZakupac().addItem(z);
        }
    }
    
}
