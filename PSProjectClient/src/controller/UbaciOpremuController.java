/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.KategorijaOpreme;
import domain.Oprema;
import forme.UbaciOpremuForma;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import komunikacija.Komunikacija;

/**
 *
 * @author Lenovo
 */
public class UbaciOpremuController {
    private final UbaciOpremuForma dof;

    public UbaciOpremuController(UbaciOpremuForma dof) {
        this.dof = dof;
        popuniCMBox();
        applyTheme();
        addActionListener();
        
    }

    private void applyTheme() {
        dof.setLocationRelativeTo(null);
        JTextField[] textFields = { dof.getjTextFieldProizvodjac(),dof.getjTextFieldProizvod(),dof.getjTextFieldUkupnaKol(),dof.getjTextFieldDostupnaKol(),dof.getjTextFieldCenaPoDanu()};
        for (JTextField tf : textFields) {
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBackground(Color.WHITE);
        }
        
        JComboBox<?>[] combos = {dof.getjComboBoxKategorijaOpreme()};
        for (JComboBox<?> cb : combos) {
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            cb.setBackground(Color.WHITE);
        }
        
        dof.getjButtonDodaj().setFont(new Font("Segoe UI", Font.BOLD, 14));
        dof.getjButtonDodaj().setBackground(new Color(45, 137, 239)); 
        dof.getjButtonDodaj().setForeground(Color.WHITE);
        dof.getjButtonDodaj().setFocusPainted(false);
        
        dof.getContentPane().setBackground(new Color(245,245,245));
    }

    private void addActionListener() {
        dof.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String proizvodjac = dof.getjTextFieldProizvodjac().getText();
                String proizvod = dof.getjTextFieldProizvod().getText();
                String cenaTekst = dof.getjTextFieldCenaPoDanu().getText();
                String ukupnoTekst = dof.getjTextFieldUkupnaKol().getText();
                String dostupnoTekst = dof.getjTextFieldDostupnaKol().getText();
                KategorijaOpreme kategorijaOpreme = (KategorijaOpreme) dof.getjComboBoxKategorijaOpreme().getSelectedItem();

                if (proizvodjac.isEmpty() && proizvod.isEmpty() && cenaTekst.isEmpty() && ukupnoTekst.isEmpty() && dostupnoTekst.isEmpty()) {
                    JOptionPane.showMessageDialog(dof, "Sistem ne može da zapamti opremu.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (proizvodjac.isEmpty()) {
                    JOptionPane.showMessageDialog(dof, "Potrebno je da unesete naziv proizvođača.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!proizvodjac.matches("[a-zA-ZšđčćžŠĐČĆŽ\\s]+") || proizvodjac.length() <= 2) {
                    JOptionPane.showMessageDialog(dof, "Naziv proizvođača nije odgovarajuće.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (proizvod.isEmpty()) {
                    JOptionPane.showMessageDialog(dof, "Potrebno je da unesete naziv proizvoda.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (cenaTekst.isEmpty()) {
                    JOptionPane.showMessageDialog(dof, "Potrebno je da unesete cenu po danu.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ukupnoTekst.isEmpty()) {
                    JOptionPane.showMessageDialog(dof, "Potrebno je da unesete ukupnu količinu.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (dostupnoTekst.isEmpty()) {
                    JOptionPane.showMessageDialog(dof, "Potrebno je da unesete dostupnu količinu.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double cenaPoDanu = Double.valueOf(cenaTekst);
                int ukupnaKolicina = Integer.valueOf(ukupnoTekst);
                int dostupnaKolicina = Integer.valueOf(dostupnoTekst);

                if (cenaPoDanu <= 0) {
                    JOptionPane.showMessageDialog(dof, "Cena po danu ne sme biti manja ili jednaka 0.", "Neuspeh", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ukupnaKolicina <= 0) {
                    JOptionPane.showMessageDialog(dof, "Ukupna količina ne sme biti manja ili jednaka 0.", "Neuspeh", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (dostupnaKolicina <= 0 || dostupnaKolicina > ukupnaKolicina) {
                    JOptionPane.showMessageDialog(dof, "Dostupna količina ne sme biti manja ili jednaka 0, niti veća od ukupne količine.", "Neuspeh", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Oprema o = new Oprema(-1L, proizvodjac, proizvod, kategorijaOpreme, cenaPoDanu, ukupnaKolicina, dostupnaKolicina);

                try {
                    Komunikacija.getInstance().dodajOpremu(o);
                    JOptionPane.showMessageDialog(dof, "Sistem je zapamtio opremu.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                    dof.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dof, ex.getMessage(), "Neuspešno", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    

    public void otvoriFormu() {
        dof.setVisible(true);
        dof.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void popuniCMBox() {
        dof.getjComboBoxKategorijaOpreme().removeAllItems();
        KategorijaOpreme[] kategorije = KategorijaOpreme.values();
        for(KategorijaOpreme k : kategorije ){
            dof.getjComboBoxKategorijaOpreme().addItem(k);
        }
    }
    
}
