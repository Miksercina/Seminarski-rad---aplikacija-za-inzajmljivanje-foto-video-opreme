/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.sun.java.accessibility.util.AWTEventMonitor;
import domain.Grad;
import domain.Zakupac;
import forme.FormaMod;
import forme.KreirajZakupcaForma;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import komunikacija.Komunikacija;

/**
 *
 * @author Lenovo
 */
public class KreirajZakupcaController {
    private final KreirajZakupcaForma kzf;

    public KreirajZakupcaController(KreirajZakupcaForma kzf) {
        this.kzf = kzf;
        dodajSlušaoca();
        applyTheme();
    }

    public void otvoriFormu(FormaMod formaMod) {
        try {
            pripremiFormu(formaMod);
            kzf.setVisible(true);
            kzf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(kzf, "Greška");
        }
    }

    private void pripremiFormu(FormaMod formaMod) throws Exception {
        switch (formaMod) {
            case DODAJ:
                List<Grad> listaGradova = komunikacija.Komunikacija.getInstance().vratiListuGradova();
                kzf.getjComboBoxGrad().removeAllItems();
                kzf.getjComboBoxGrad().addItem(new Grad(0L, "Izaberi grad", ""));
                
                for(Grad g : listaGradova){
                    kzf.getjComboBoxGrad().addItem(g);
                }
                kzf.getjComboBoxGrad().setSelectedIndex(0);
                kzf.getjButtonAzuriraj().setEnabled(false);
                kzf.getjButtonDodaj().setEnabled(true);
                kzf.getjButtonDodaj().setVisible(true);
                kzf.getjButtonAzuriraj().setVisible(false);
                kzf.getjLabelId().setVisible(false);
                kzf.getjTextFieldId().setVisible(false);
                break;
            case IZMENI:
                List<Grad> listaGradova1 = komunikacija.Komunikacija.getInstance().vratiListuGradova();
                for(Grad g : listaGradova1){
                    kzf.getjComboBoxGrad().addItem(g);
                }
                kzf.getjButtonAzuriraj().setEnabled(true);
                kzf.getjButtonDodaj().setEnabled(false);
                kzf.getjButtonDodaj().setVisible(false);
                kzf.getjButtonAzuriraj().setVisible(true);
                kzf.getjLabelId().setVisible(true);
                kzf.getjTextFieldId().setVisible(true);
                kzf.getjTextFieldId().setEnabled(false);
                
                Zakupac z = (Zakupac) koordinator.Koordinator.getInstance().vratiParam("zakupac");
                kzf.getjTextFieldId().setText(z.getIdZakupac()+"");
                kzf.getjTextFieldIme().setText(z.getIme());
                kzf.getjTextFieldPrezime().setText(z.getPrezime());
                kzf.getjTextFieldEmail().setText(z.getEmail());
                kzf.getjTextFieldBrTelefona().setText(z.getBrojTelefona());
                kzf.getjTextFieldAdresa().setText(z.getAdresa());
                kzf.getjComboBoxGrad().setSelectedItem(z.getGrad());
                break;
            case DETALJI:
                List<Grad> listaGradova2 = komunikacija.Komunikacija.getInstance().vratiListuGradova();
                for(Grad g : listaGradova2){
                    kzf.getjComboBoxGrad().addItem(g);
                }
                kzf.getjButtonDodaj().setVisible(false);
                kzf.getjButtonAzuriraj().setVisible(false);
                kzf.getjButtonAzuriraj().setEnabled(false);
                
                Zakupac z2 = (Zakupac) koordinator.Koordinator.getInstance().vratiParam("zakupac");
                kzf.getjTextFieldId().setText(z2.getIdZakupac()+"");
                kzf.getjTextFieldIme().setText(z2.getIme());
                kzf.getjTextFieldPrezime().setText(z2.getPrezime());
                kzf.getjTextFieldEmail().setText(z2.getEmail());
                kzf.getjTextFieldBrTelefona().setText(z2.getBrojTelefona());
                kzf.getjTextFieldAdresa().setText(z2.getAdresa());
                kzf.getjComboBoxGrad().setSelectedItem(z2.getGrad());
                
                kzf.getjComboBoxGrad().setEnabled(false);
                kzf.getjTextFieldId().setEnabled(false);
                kzf.getjTextFieldIme().setEnabled(false);
                kzf.getjTextFieldPrezime().setEnabled(false);
                kzf.getjTextFieldEmail().setEnabled(false);
                kzf.getjTextFieldBrTelefona().setEnabled(false);
                kzf.getjTextFieldAdresa().setEnabled(false);
                break;
           
        }
    }

    private void addActionListener(){
        String ime = kzf.getjTextFieldIme().getText().trim();
        String prezime = kzf.getjTextFieldPrezime().getText().trim();
        String email = kzf.getjTextFieldEmail().getText().trim();
        String adresa = kzf.getjTextFieldAdresa().getText().trim();
        String brTelefona = kzf.getjTextFieldBrTelefona().getText().trim();
        if(!validirajPolja())return;
                
        Grad grad = (Grad) kzf.getjComboBoxGrad().getSelectedItem();
        
                
        Zakupac z = new Zakupac(-1L, ime, prezime, email, grad, adresa, brTelefona);
                
        try{
            Komunikacija.getInstance().dodajZakupca(z);
            JOptionPane.showMessageDialog(kzf, "Uspešno zapamćen zakupac.","Uspeh",JOptionPane.INFORMATION_MESSAGE);
            kzf.dispose();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(kzf, ex.getMessage(),"Neuspeh",JOptionPane.ERROR_MESSAGE);
        }
                    
        
    }

    private void applyTheme() {
        JButton[] buttons = {kzf.getjButtonDodaj(), kzf.getjButtonAzuriraj()};
        for (JButton b : buttons) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setBackground(new Color(45, 137, 239)); 
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
            b.setPreferredSize(new Dimension(140, 35));
        }
    
        JTextField[] textFields = {kzf.getjTextFieldId(), kzf.getjTextFieldIme(), kzf.getjTextFieldPrezime(), kzf.getjTextFieldEmail(),kzf.getjTextFieldBrTelefona(),kzf.getjTextFieldAdresa()};
        for (JTextField tf : textFields) {
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBackground(new Color(245, 245, 245));
        }

        kzf.getjComboBoxGrad().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        kzf.getjComboBoxGrad().setBackground(Color.WHITE);

        kzf.setBackground(new Color(245, 245, 245));
    }
    
    private void dodajSlušaoca() {
        kzf.getjButtonDodaj().addActionListener((e) -> {
            addActionListener(); 
        });
        kzf.getjButtonAzuriraj().addActionListener((e) -> {
            azurirajActionListener();
         });
    }
    private void azurirajActionListener() {
        String ime = kzf.getjTextFieldIme().getText().trim();
        String prezime = kzf.getjTextFieldPrezime().getText().trim();
        String email = kzf.getjTextFieldEmail().getText().trim();
        String adresa = kzf.getjTextFieldAdresa().getText().trim();
        String brTelefona = kzf.getjTextFieldBrTelefona().getText().trim();
        Long id = Long.parseLong(kzf.getjTextFieldId().getText().trim());
        Grad grad = (Grad) kzf.getjComboBoxGrad().getSelectedItem();
        if(!validirajPolja())return;
        Zakupac z = new Zakupac(id, ime, prezime, email, grad, adresa, brTelefona);

        try {
            Komunikacija.getInstance().azurirajZakupca(z);
            JOptionPane.showMessageDialog(kzf, "Uspešno ažuriran zakupac.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            kzf.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(kzf, ex.getMessage(), "Neuspeh", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean validirajPolja() {
        String ime = kzf.getjTextFieldIme().getText().trim();
        String prezime = kzf.getjTextFieldPrezime().getText().trim();
        String email = kzf.getjTextFieldEmail().getText().trim();
        String adresa = kzf.getjTextFieldAdresa().getText().trim();
        String brTelefona = kzf.getjTextFieldBrTelefona().getText().trim();

        if(ime.isEmpty()){
            JOptionPane.showMessageDialog(kzf, "Potrebno je da unesete ime.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!ime.matches("[a-zA-ZšđčćžŠĐČĆŽ\\s]+") || ime.length() <= 2){
            JOptionPane.showMessageDialog(kzf, "Ime nije odgovarajuće.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(prezime.isEmpty()){
            JOptionPane.showMessageDialog(kzf, "Potrebno je da unesete prezime.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!prezime.matches("[a-zA-ZšđčćžŠĐČĆŽ\\s]+") || prezime.length() <= 2){
            JOptionPane.showMessageDialog(kzf, "Prezime nije odgovarajuće.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(adresa.isEmpty() || adresa.length() <= 2){
            JOptionPane.showMessageDialog(kzf, "Adresa nije odgovarajuća.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!brTelefona.matches("^(\\+381|0)[\\s\\-]?[0-9]{2,3}[\\s\\-/]?[0-9]{3,4}[\\s\\-]?[0-9]{3,4}$")){
            JOptionPane.showMessageDialog(kzf, "Broj telefona nije odgovarajući.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(email.isEmpty() || !email.contains("@")){
            JOptionPane.showMessageDialog(kzf, "Email nije odgovarajući.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Grad grad = (Grad) kzf.getjComboBoxGrad().getSelectedItem();
        if(grad == null || grad.getNaziv().equals("Izaberi grad")){
            JOptionPane.showMessageDialog(kzf, "Potrebno je da selektujete grad.", "Neuspešno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
