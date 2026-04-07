/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Brend;
import forme.UbaciBrendForma;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import komunikacija.Komunikacija;

/**
 *
 * @author Lenovo
 */
public class UbaciBrendController {
    private final UbaciBrendForma dbf;

    public UbaciBrendController(UbaciBrendForma dbf) {
        this.dbf = dbf;
        applyTheme();
        addActionListener();
    }

    private void applyTheme() {
        JTextField[] textFields = { dbf.getjTextFieldNaziv(),dbf.getjTextFieldEmail(),dbf.getjTextFieldBrTelefona()};
        for (JTextField tf : textFields) {
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBackground(Color.WHITE);
        }
        dbf.getjButtonDodaj().setFont(new Font("Segoe UI", Font.BOLD, 14));
        dbf.getjButtonDodaj().setForeground(Color.WHITE);
        dbf.getjButtonDodaj().setBackground(new Color(45, 137, 239));
        dbf.getjButtonDodaj().setFocusPainted(false);
        dbf.getjButtonDodaj().setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        dbf.getjButtonDodaj().setPreferredSize(new Dimension(140, 35));
        
        
    }

    private void addActionListener() {
        dbf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String naziv = dbf.getjTextFieldNaziv().getText().trim().toUpperCase();
                String email = dbf.getjTextFieldEmail().getText().trim();
                String brojTelefona = dbf.getjTextFieldBrTelefona().getText().trim();
        
            if (naziv.isEmpty()) {
                JOptionPane.showMessageDialog(dbf, "Potrebno je uneti naziv.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!naziv.matches("[a-zA-Z0-9šđčćžŠĐČĆŽ\\s]+") || naziv.length() <= 2) {
                JOptionPane.showMessageDialog(dbf, "Naziv nije odgovarajući.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(dbf, "Potrebno je uneti email.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(dbf, "Email nije u odgovarajućem formatu.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (brojTelefona.isEmpty()) {
                JOptionPane.showMessageDialog(dbf, "Potrebno je uneti broj telefona.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!brojTelefona.matches("^(\\+381|0)[\\s\\-]?[0-9]{2,3}[\\s\\-/]?[0-9]{3,4}[\\s\\-]?[0-9]{3,4}$")) {
                JOptionPane.showMessageDialog(dbf, "Broj telefona nije odgovarajući.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Brend b = new Brend(-1L,naziv,email,brojTelefona);
             
            try {
                Komunikacija.getInstance().dodajBrend(b);
                JOptionPane.showMessageDialog(dbf, "Sistem je zapamtio sertifkat.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                dbf.dispose();
            }  catch(Exception ex) {
                 JOptionPane.showMessageDialog(dbf, ex.getMessage(), "Neuspešno", JOptionPane.ERROR_MESSAGE);
            }
            }
        });
    }

    public void otvoriFormu() {
        dbf.setVisible(true);
    }
    
}
