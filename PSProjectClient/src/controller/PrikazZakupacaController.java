/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Grad;
import domain.Zakupac;
import forme.PrikazZakupacaForma;
import forme.model.ModelTabeleZakupac;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import koordinator.Koordinator;

/**
 *
 * @author Lenovo
 */
public class PrikazZakupacaController {
    private final PrikazZakupacaForma pzf;

    public PrikazZakupacaController(PrikazZakupacaForma pzf) {
        this.pzf = pzf;
        applyTheme();
        addActionListener();
    }

    private void applyTheme() {
        JButton[] buttons = {
            pzf.getjButtonObrisi(),
            pzf.getjButtonAzuriraj(),
            pzf.getjButtonPretrazi(),
            pzf.getjButtonResetuj(),
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
            pzf.getjTextFieldIme(),
            pzf.getjTextFieldPrezime(),
            pzf.getjTextFieldEmail(),
            pzf.getjTextFieldBrTelefona(),
            pzf.getjTextFieldAdresa()
        };
        for (JTextField tf : textFields) {
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBackground(Color.WHITE);
        }
        
        JComboBox<?>[] combos = { pzf.getjComboBoxGrad()};
        for (JComboBox<?> cb : combos) {
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            cb.setBackground(Color.WHITE);
        }
        
        JTable[] tables = { pzf.getjTableZakupac()};
        for (JTable t : tables) {
            t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            t.setRowHeight(25);
        }
        
        pzf.getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void addActionListener() {
        pzf.addjButtonObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pzf.getjTableZakupac().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(pzf, "Sistem ne može da nađe zakupca.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                }else{
                    
                    ModelTabeleZakupac mtz  = (ModelTabeleZakupac) pzf.getjTableZakupac().getModel();
                    Zakupac z = mtz.getLista().get(red);
                    try {
                        komunikacija.Komunikacija.getInstance().obrisiZakupca(z);
                        JOptionPane.showMessageDialog(pzf, "Sistem je obrisao zakupca.", "Uspešno", JOptionPane.INFORMATION_MESSAGE); 
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pzf, "Sistem ne može da obriše zakupca.", "Neuspešno", JOptionPane.ERROR_MESSAGE); 
                    }
            }
            }
        });
        pzf.addjButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pzf.getjTableZakupac().getSelectedRow();
                if(red==-1){
                JOptionPane.showMessageDialog(pzf, "Sistem ne može da nađe zakupca.", "Greška", JOptionPane.ERROR_MESSAGE);
                }else{
                    
                        ModelTabeleZakupac mtz  = (ModelTabeleZakupac) pzf.getjTableZakupac().getModel();
                        Zakupac z = mtz.getLista().get(red);
                        Koordinator.getInstance().dodajParam("zakupac", z);
                    try {
                        Koordinator.getInstance().otvoriIzmeniZakupcaFormu();
                    } catch (Exception ex) {
                        Logger.getLogger(PrikazZakupacaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            }
        });
        pzf.addjButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = pzf.getjTextFieldIme().getText().trim();
                String prezime = pzf.getjTextFieldPrezime().getText().trim();
                String adresa = pzf.getjTextFieldAdresa().getText().trim();
                String brTelefona = pzf.getjTextFieldBrTelefona().getText();
                String email = pzf.getjTextFieldEmail().getText().trim();
                
                Grad grad = (Grad) pzf.getjComboBoxGrad().getSelectedItem();
                
                ModelTabeleZakupac mtz = (ModelTabeleZakupac) pzf.getjTableZakupac().getModel();
                
                boolean svaPoljaPrazna = ime.isEmpty() && prezime.isEmpty() && adresa.isEmpty()
                && brTelefona.isEmpty() && email.isEmpty() && grad == null;
                if (svaPoljaPrazna) {
                    try {
                        pripremiFormu();
                        return;
                    } catch (Exception ex) {
                        Logger.getLogger(PrikazZakupacaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                mtz.pretrazi(ime,prezime,email,grad,adresa,brTelefona);
                
                if (mtz.getLista().isEmpty()) {
                    JOptionPane.showMessageDialog(pzf, "Sistem ne može da nađe zakupce po zadatim kriterijumima.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(pzf, "Sistem je našao zakupce po zadatim kriterijumima.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
        pzf.addjButtonResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pripremiFormu();
                } catch (Exception ex) {
                    Logger.getLogger(PrikazZakupacaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                pzf.getjTextFieldIme().setText("");
                pzf.getjTextFieldPrezime().setText("");
                pzf.getjTextFieldEmail().setText("");
                pzf.getjTextFieldBrTelefona().setText("");
                pzf.getjTextFieldAdresa().setText("");
                pzf.getjComboBoxGrad().setSelectedIndex(0);
            }
        });
        pzf.addjButtonDetaljiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pzf.getjTableZakupac().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(pzf, "Sistem ne može da nađe zakupca.", "Greška", JOptionPane.ERROR_MESSAGE);
                }else{
                        ModelTabeleZakupac mtz  = (ModelTabeleZakupac) pzf.getjTableZakupac().getModel();
                        Zakupac k = mtz.getLista().get(red);
                        Koordinator.getInstance().dodajParam("zakupac", k);
                    try {
                        Koordinator.getInstance().otvoriDetaljiZakupcaFormu();
                    } catch (Exception ex) {
                        Logger.getLogger(PrikazZakupacaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            }
        });
    }
    public void otvoriFormu() throws Exception{
        pripremiFormu();
        pzf.setVisible(true);
    }

    private void pripremiFormu() throws Exception {
        List<Zakupac> zakupci = komunikacija.Komunikacija.getInstance().vratiZakupce();
        ModelTabeleZakupac mtz = new ModelTabeleZakupac(zakupci);
        pzf.getjTableZakupac().setModel(mtz);
        pzf.getjComboBoxGrad().removeAllItems();
        List<Grad> gradovi = komunikacija.Komunikacija.getInstance().vratiListuGradova();
        for(Grad g : gradovi){
            pzf.getjComboBoxGrad().addItem(g);
        }
        pzf.getjComboBoxGrad().addItem(null);
    }

    public void osveziFormu() throws Exception {
        pripremiFormu();
    }
}
