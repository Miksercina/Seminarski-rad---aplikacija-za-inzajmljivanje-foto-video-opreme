/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.sun.java.accessibility.util.AWTEventMonitor;
import domain.Zakupodavac;
import forme.LoginForma;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import komunikacija.Komunikacija;
import koordinator.Koordinator;
/**
 *
 * @author Lenovo
 */
public class LoginController {
    private final LoginForma lf;
    
    public LoginController(LoginForma lf) {
        this.lf = lf;
        customizeUI();
        addActionListener();
    }

    public void otvoriFormu() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(lf);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void customizeUI() {
        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontButton = new Font("Segoe UI", Font.BOLD, 14);

        lf.getjLabelKorIme().setFont(fontLabel);
        lf.getjLabelPass().setFont(fontLabel);
        lf.getjTextFieldKorIme().setFont(fontInput);
        lf.getjPasswordField().setFont(fontInput);
        lf.getjButtonLOGIN().setFont(fontButton);
        lf.getjButtonIzadji().setFont(fontButton);


        lf.getjButtonLOGIN().setBackground(new Color(70, 130, 180));
        lf.getjButtonLOGIN().setForeground(Color.WHITE);
        lf.getjButtonLOGIN().setContentAreaFilled(true);
        lf.getjButtonIzadji().setBackground(new Color(220, 53, 69));
        lf.getjButtonIzadji().setForeground(Color.WHITE);
        lf.getjButtonIzadji().setContentAreaFilled(true);


        lf.setOpaque(true);  
        lf.setBackground(new Color(245, 245, 245));


        lf.getjButtonLOGIN().setCursor(new Cursor(Cursor.HAND_CURSOR));
        lf.getjButtonIzadji().setCursor(new Cursor(Cursor.HAND_CURSOR));
        lf.setOpaque(true);
        lf.setBackground(new Color(245, 245, 245));
    }

    private void addActionListener() {
        lf.loginAddActionListener(e -> login());
    }

    private void login() {
        try {
            String username = lf.getjTextFieldKorIme().getText().trim();
            String password = String.valueOf(lf.getjPasswordField().getPassword());
            
            Komunikacija.getInstance().connection();
            Zakupodavac ulogovani = Komunikacija.getInstance().login(username,password);
            if(ulogovani == null){
                JOptionPane.showMessageDialog(lf,"Korisničko ime i šifra koju ste uneli nisu ispravni.","Greška",JOptionPane.ERROR_MESSAGE);
            }else{
                Koordinator.getInstance().setUlogovani(ulogovani);
                JOptionPane.showMessageDialog(lf, "Korisničko ime i sifra koje ste uneli su ispravni","Uspeh",JOptionPane.INFORMATION_MESSAGE);
                Koordinator.getInstance().otvoriGlavnuFormu();
                SwingUtilities.getWindowAncestor(lf).setVisible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(lf, "Greška prilikom prijave.");
        }
    }
    
    
}
