/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.StavkaNajma;
import domain.UgovorONajmu;
import domain.Zakupac;
import forme.PrikazUgovorONajmuForma;
import forme.model.ModelTabeleStavka;
import forme.model.ModelTabeleUgovorONajmu;
import forme.model.ModelTabeleZakupac;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author Lenovo
 */
public class PrikazUgovorONajmuController {
    PrikazUgovorONajmuForma pu;

    public PrikazUgovorONajmuController(PrikazUgovorONajmuForma pu) {
        this.pu = pu;
        addActionListener();
        addMouseListener();
        applyTheme();
    }

    public void otvoriFormu() throws Exception{
        pripremiFormu();
        pu.setVisible(true);
    }
    public void osveziFormu() throws Exception {
        pripremiFormu();
    }
    private void pripremiFormu() throws Exception {
        List<UgovorONajmu> evidencijeTreninga = komunikacija.Komunikacija.getInstance().vratiUgovoreONajmu();
        ModelTabeleUgovorONajmu mtu = new ModelTabeleUgovorONajmu(evidencijeTreninga);
        pu.getjTableUgovorONajmu().setModel(mtu);
        
        List<StavkaNajma> stavke = new ArrayList<>();
        ModelTabeleStavka mts = new ModelTabeleStavka(stavke);
        pu.getjTableStavke().setModel(mts);
    }

    private void addActionListener() {
        pu.addJButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imeK = pu.getjTextFieldZakupacIme().getText().trim();
                String prezimeK = pu.getjTextFieldZakupacPrezime().getText().trim();
                String nazivZakupodavca = pu.getjTextFieldZakupodavacIme().getText().trim();
                
                
                ModelTabeleUgovorONajmu mte = (ModelTabeleUgovorONajmu) pu.getjTableUgovorONajmu().getModel();
                mte.pretrazi(imeK,prezimeK,nazivZakupodavca);
                
                if (mte.getLista().isEmpty()) {
                    JOptionPane.showMessageDialog(pu, "Sistem ne može da nađe ugovore o najmu po zadatim kriterijumima.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(pu, "Sistem je našao ugovore o najmu po zadatim kriterijumima.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
        pu.addJButtonResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pripremiFormu();
                } catch (Exception ex) {
                    Logger.getLogger(PrikazUgovorONajmuController.class.getName()).log(Level.SEVERE, null, ex);
                }
                pu.getjTextFieldZakupacIme().setText("");
                pu.getjTextFieldZakupacPrezime().setText("");
                pu.getjTextFieldZakupodavacIme().setText("");
                
            }
        });
        
        pu.addJButtonDetaljiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pu.getjTableUgovorONajmu().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(pu, "Sistem ne može da nađe ugovor o najmu.", "Greška", JOptionPane.ERROR_MESSAGE);
                }else{
                    
                    JOptionPane.showMessageDialog(pu, "Sistem je našao ugovor o najmu.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleUgovorONajmu mtu  = (ModelTabeleUgovorONajmu) pu.getjTableUgovorONajmu().getModel();
                    UgovorONajmu u = mtu.getLista().get(red);
                    Koordinator.getInstance().dodajParam("ugovoronajmu", u);
                    try {
                        Koordinator.getInstance().otvoriDetaljiUgovorONajmu();
                    } catch (Exception ex) {
                        Logger.getLogger(PrikazUgovorONajmuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            }
        });
        
    }

    private void addMouseListener() {
        pu.getjTableUgovorONajmu().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                int red = pu.getjTableUgovorONajmu().getSelectedRow();
                if(red!=-1){
                    ModelTabeleUgovorONajmu mtu = (ModelTabeleUgovorONajmu) pu.getjTableUgovorONajmu().getModel();
                    UgovorONajmu ugovor = mtu.getLista().get(red);
                    List<StavkaNajma> stavke;
                        try {
                            stavke = Komunikacija.getInstance().vratiStavke(ugovor.getIdUgovorONajmu());
                            ModelTabeleStavka mts = new ModelTabeleStavka(stavke);
                            pu.getjTableStavke().setModel(mts);
                        } catch (Exception ex) {
                            Logger.getLogger(PrikazUgovorONajmuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                
                
                }

            }
            
            
            
        });
    }

    private void applyTheme() {
        JButton[] buttons = {
        pu.getjButtonPretrazi(),
        pu.getjButtonDetalji(),
        pu.getjButtonResetuj()
        };
        for (JButton b : buttons) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setBackground(new Color(45, 137, 239)); 
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
            b.setPreferredSize(new Dimension(140, 35));
        }


        JTextField[] textFields = {
            pu.getjTextFieldZakupacIme(),
            pu.getjTextFieldZakupacPrezime(),
            pu.getjTextFieldZakupodavacIme(),
        };
        for (JTextField tf : textFields) {
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBackground(new Color(245, 245, 245));
        }


        JTable[] tables = {pu.getjTableUgovorONajmu(), pu.getjTableStavke()};
        for (JTable t : tables) {
            t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            t.setRowHeight(25);
        }


        pu.setBackground(new Color(245, 245, 245));
        }
    
}
