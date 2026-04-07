/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Oprema;
import domain.StatusStavke;
import domain.StavkaNajma;
import domain.UgovorONajmu;
import domain.Zakupac;
import domain.Zakupodavac;
import forme.FormaMod;
import forme.KreirajUgovorONajmuForma;
import forme.model.ModelTabeleStavka;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author Lenovo
 */
public class KreirajUgovorONajmuController {
    private final KreirajUgovorONajmuForma kuonf;
    private int trenutniRedniBroj = 1;
    private ModelTabeleStavka mts;
    private List<StavkaNajma> stavke;
    private double ukupnaCena;
    private UgovorONajmu uon;

    public KreirajUgovorONajmuController(KreirajUgovorONajmuForma kuonf) {
        this.kuonf = kuonf;
        addActionListener();
        applyTheme();
    }

    public void otvoriFormu(FormaMod mod) throws Exception {
        kuonf.setVisible(true);
        pripremiFormu(mod);
        kuonf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        kuonf.setLocationRelativeTo(null);

    }

    private void pripremiFormu(FormaMod mod) throws Exception {
        mts = new ModelTabeleStavka();
        
        stavke = new ArrayList<>();
        
        kuonf.getjTableStavke().setModel(mts);
        
        kuonf.getjTextFieldUkupnaCena().setText(String.valueOf(mts.getUkupnaCena()));
        
        List<Zakupodavac> zakupodavci = Komunikacija.getInstance().vratiZakupodavce();
        uon = new UgovorONajmu();
        
        for (Zakupodavac z : zakupodavci) {
            kuonf.getjComboBoxZakupodavac().addItem(z);
            if(z.equals(Koordinator.getInstance().getUlogovani())){
                kuonf.getjComboBoxZakupodavac().setSelectedItem(z);
                kuonf.getjComboBoxZakupodavac().setEnabled(false);
                uon.setZakupodavac(z);
            }
        }
        
        
        
        List<Zakupac> zakupci = komunikacija.Komunikacija.getInstance().vratiZakupce();
        for(Zakupac z : zakupci){
            kuonf.getjComboBoxZakupac().addItem(z);
        }
        
        List<Oprema> oprema = Komunikacija.getInstance().vratiOpremu();
        for (Oprema o : oprema) {
            if(o.getDostupnaKolicina() > 0){
                kuonf.getjComboBoxOprema().addItem(o);
            }
        }
        
        switch (mod) {
            case DODAJ:
                kuonf.getjButtonObrisiStavku().setVisible(false);
                for (Zakupodavac z : zakupodavci) {
                    if(z.equals(Koordinator.getInstance().getUlogovani())){
                        kuonf.getjComboBoxZakupodavac().setSelectedItem(z);
                        kuonf.getjComboBoxZakupodavac().setEnabled(false);
                        uon.setZakupodavac(z);
                    }
                    kuonf.getjButtonIzmeni().setVisible(false);
                }
                break;
            case DETALJI:
                kuonf.getjButtonIzmeni().setVisible(false);
                UgovorONajmu u = (UgovorONajmu) koordinator.Koordinator.getInstance().vratiParam("ugovoronajmu");
                kuonf.getjButtonDodaj().setVisible(false);
                kuonf.getjButtonDodajStavku().setVisible(false);
                kuonf.getjComboBoxZakupodavac().setSelectedItem(u.getZakupodavac());
                kuonf.getjComboBoxZakupodavac().setEnabled(false);
                kuonf.getjComboBoxZakupac().setSelectedItem(u.getZakupac());
                kuonf.getjComboBoxZakupac().setEnabled(false);
                kuonf.getjComboBoxOprema().setVisible(false);
                kuonf.getJtextFieldVremeOd().setVisible(false);
                kuonf.getjTextVremeDo().setVisible(false);
                kuonf.getjTextFieldKolicina().setEnabled(false);
                kuonf.getjTextFieldKolicina().setVisible(false);
                kuonf.getjTextFieldUkupnaCena().setText(String.valueOf(mts.getUkupnaCena()));
                try {
                    List<StavkaNajma> stavke = Komunikacija.getInstance().vratiStavke(u.getIdUgovorONajmu());
                    mts = new ModelTabeleStavka(stavke);
                    kuonf.getjTableStavke().setModel(mts);
                    kuonf.getjTextFieldUkupnaCena().setText(String.valueOf(mts.getUkupnaCena()));
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(kuonf, "Greška pri učitavanju stavki!", "Greška", JOptionPane.ERROR_MESSAGE);
                }
                kuonf.getjButtonObrisiStavku().setVisible(false);
                kuonf.getjLabel4().setVisible(false);
                kuonf.getjLabel5().setVisible(false);
                kuonf.getjLabel6().setVisible(false);
                kuonf.getjLabelKolicina().setVisible(false);
                break;
            case IZMENI:
                UgovorONajmu uon2 = (UgovorONajmu) Koordinator.getInstance().vratiParam("ugovoronajmu");
                System.out.println("UON2: " + uon2);
                if (uon2 == null) {
                    JOptionPane.showMessageDialog(kuonf, "Greška: ugovor nije pronađen!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("ID: " + uon2.getIdUgovorONajmu());
                uon = uon2;

                for (Zakupodavac z : zakupodavci) {
                    if (z.equals(Koordinator.getInstance().getUlogovani())) {
                        kuonf.getjComboBoxZakupodavac().setSelectedItem(z);
                        kuonf.getjComboBoxZakupodavac().setEnabled(false);
                        uon.setZakupodavac(z); 
                    }
                }

                for (Zakupac z : zakupci) {
                    if (z.equals(uon2.getZakupac())) {
                        kuonf.getjComboBoxZakupac().setSelectedItem(z);
                    }
                }

                List<StavkaNajma> stavkeZaPrikaz = Komunikacija.getInstance().vratiStavke(uon2.getIdUgovorONajmu());
                System.out.println("ID ugovora: " + uon2.getIdUgovorONajmu());
                System.out.println("Broj stavki: " + stavkeZaPrikaz.size());

                int maxRb = 0;
                for (StavkaNajma s : stavkeZaPrikaz) {
                    if (s.getStatus() == null) {
                        s.setStatus(StatusStavke.POSTOJECA);
                    }
                    s.setUgovorONajmu(uon);
                    if (s.getRb() > maxRb) {
                        maxRb = s.getRb().intValue();
                    }
                }

                mts = new ModelTabeleStavka(stavkeZaPrikaz); // direktno sa stavkeZaPrikaz
                kuonf.getjTableStavke().setModel(mts);

                stavke.clear();
                stavke.addAll(stavkeZaPrikaz);

                trenutniRedniBroj = maxRb + 1;

                ukupnaCena = 0;
                for (StavkaNajma s : stavke) {
                    ukupnaCena += s.getCena();
                }
                kuonf.getjTextFieldUkupnaCena().setText(String.valueOf(mts.getUkupnaCena()));
                kuonf.getjButtonDodaj().setVisible(false);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addActionListener() {
        kuonf.dodajStavkuAddActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dodaj(e);
        }

        private void dodaj(ActionEvent e) {
            Oprema o = (Oprema) kuonf.getjComboBoxOprema().getSelectedItem();
            LocalDate vremeOd, vremeDo;
            int kolicina;
            try {
                vremeOd = LocalDate.parse(kuonf.getJtextFieldVremeOd().getText().trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                vremeDo = LocalDate.parse(kuonf.getjTextVremeDo().getText().trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null,
                        "Datum mora biti u formatu dd.MM.yyyy (npr. 13.03.2026.).",
                        "Greška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                kolicina = Integer.parseInt(kuonf.getjTextFieldKolicina().getText().trim());
                    if (kolicina <= 0) {
                        JOptionPane.showMessageDialog(null,"Količina mora biti veća od 0.", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"Količina mora biti celi broj.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            if (kolicina > o.getDostupnaKolicina()) {
                JOptionPane.showMessageDialog(null,"Tražena količina (" + kolicina + ") veća od dostupne (" + o.getDostupnaKolicina() + ").","Greška", JOptionPane.ERROR_MESSAGE);
                return;
            
            }
            Long rb = (long) trenutniRedniBroj;
            Long brojDana = ChronoUnit.DAYS.between(vremeOd, vremeDo);
           
            if (brojDana < 2) {
             JOptionPane.showMessageDialog(null, "Oprema se mora iznajmiti na duze od 2 dana.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
                }
            if (vremeOd.isAfter(vremeDo)) {
                JOptionPane.showMessageDialog(null, "Vreme od mora biti pre vremena do.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            double cena = brojDana * o.getCenaPoDanu();
            for (StavkaNajma stavka : (List<StavkaNajma>)mts.getAktivneStavke()) {
                if (o.getDostupnaKolicina() > o.getUkupnaKolicina()) {
                        JOptionPane.showMessageDialog(null, "Dostupna kolicina ne sme biti veca od ukupne kolicine opreme!", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                }
            }

            StavkaNajma stavka = new StavkaNajma(rb, o, vremeOd, vremeDo, kolicina, cena);
            stavka.setStatus(StatusStavke.NOVA);
            stavka.setUgovorONajmu(uon);
            
            o.setDostupnaKolicina(o.getDostupnaKolicina() - kolicina);
    
            stavke.add(stavka);
            mts.dodajStavku(stavka);
            kuonf.getjTableStavke().revalidate();
            kuonf.getjTableStavke().repaint();
            trenutniRedniBroj++;
            kuonf.getjTextFieldUkupnaCena().setText(String.valueOf(mts.getUkupnaCena()));

            kuonf.getjTextVremeDo().setText("");
            kuonf.getJtextFieldVremeOd().setText("");
            kuonf.getjTextFieldKolicina().setText("");
            kuonf.getjComboBoxOprema().setSelectedIndex(0);

           
            
        }
    });

    kuonf.dodajAddActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dodaj(e);
        }

        private void dodaj(ActionEvent e) {
            try {
                Zakupodavac zakupodavac = (Zakupodavac) kuonf.getjComboBoxZakupodavac().getSelectedItem();
                Zakupac zakupac = (Zakupac) kuonf.getjComboBoxZakupac().getSelectedItem();

                uon.setZakupac(zakupac);
                uon.setZakupodavac(zakupodavac);
                uon.setUkupnaCena(mts.getUkupnaCena());
                if (stavke.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Sistem ne može da zapamti ugovor o najmu.",
                            "Greška",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                uon.setStavke(stavke);
                try {
                    Komunikacija.getInstance().dodajUgovor(uon);
                    JOptionPane.showMessageDialog(kuonf, "Sistem je zapamtio ugovor o najmu.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                    kuonf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kuonf, ex.getMessage(), "Neuspešno", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Sistem ne može da zapamti ugovor o najmu", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    kuonf.obrisiStavkuAddActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int red = kuonf.getjTableStavke().getSelectedRow();
            if (red == -1) {
                JOptionPane.showMessageDialog(kuonf, "Morate da selektujete stavku", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int potvrda = JOptionPane.showConfirmDialog(kuonf, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
            if (potvrda != JOptionPane.YES_OPTION) return;

            StavkaNajma s = ((List<StavkaNajma>)mts.getAktivneStavke()).get(red);
            s.setStatus(StatusStavke.OBRISANA);
            mts.obrisiStavku(s);
           
            kuonf.getjTextFieldUkupnaCena().setText(String.valueOf(mts.getUkupnaCena()));
            System.out.println("KLASA IS CONTROLEER STATUS STAVKE: " + s.getStatus());

           
          
        }
    });

    kuonf.izmeniAddActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Zakupodavac zakupodavac = (Zakupodavac) kuonf.getjComboBoxZakupodavac().getSelectedItem();
                Zakupac zakupac = (Zakupac) kuonf.getjComboBoxZakupac().getSelectedItem();

                uon.setZakupodavac(zakupodavac);
                uon.setZakupac(zakupac);

                mts = (ModelTabeleStavka) kuonf.getjTableStavke().getModel();
                uon.setStavke(mts.getSveStavke());

                if (((List<StavkaNajma>)mts.getAktivneStavke()).isEmpty()) {
                    JOptionPane.showMessageDialog(kuonf, "Sistem ne može da zapamti ugovor o najmu", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                uon.setUkupnaCena(mts.getUkupnaCena());
                
                
                Komunikacija.getInstance().izmeniEvidenciju(uon);

                JOptionPane.showMessageDialog(kuonf, "Sistem je zapamtio ugovor o najmu.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                kuonf.dispose();
            
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(kuonf, "Sistem ne može da zapamti ugovor o najmu.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    }

    private void applyTheme() {
        JButton[] buttons = {
            kuonf.getjButtonDodaj(), kuonf.getjButtonDodajStavku(),
            kuonf.getjButtonObrisiStavku(), kuonf.getjButtonIzmeni()
        };
        for (JButton b : buttons) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setBackground(new Color(45, 137, 239)); 
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
            Dimension dim = new Dimension(140, 35); 
            b.setPreferredSize(dim);
        }


        JTextField[] textFields = {kuonf.getjTextVremeDo(),kuonf.getjTextFieldKolicina(), kuonf.getjTextFieldUkupnaCena(), kuonf.getJtextFieldVremeOd()};
        for (JTextField tf : textFields) {
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBackground(new Color(245, 245, 245));
        }

        JComboBox<?>[] combos = {kuonf.getjComboBoxOprema(), kuonf.getjComboBoxZakupac(), kuonf.getjComboBoxZakupodavac()};
        for (JComboBox<?> cb : combos) {
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            cb.setBackground(Color.WHITE);
        }

 
        kuonf.getjTableStavke().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        kuonf.getjTableStavke().getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        kuonf.getjTableStavke().setRowHeight(25);

   
    kuonf.setBackground(new Color(245, 245, 245)); 
    }
    
}
