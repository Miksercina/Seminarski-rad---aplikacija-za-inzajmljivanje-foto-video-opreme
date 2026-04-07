/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domain.Grad;
import domain.Zakupac;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lenovo
 */
public class ModelTabeleZakupac extends AbstractTableModel {

    
    List<Zakupac> lista ;
    String[] kolone = {"Id","Ime","Prezime","Email", "Grad","Adresa","Broj telefona"};
    @Override
    public int getRowCount() {
        return lista.size();
    }
    public ModelTabeleZakupac(List<Zakupac> lista) {
        this.lista = lista;
    }
    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zakupac z = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return z.getIdZakupac();
            case 1:
                return z.getIme();
            case 2:
                return z.getPrezime();
            case 3:
                return z.getEmail();
            case 4:
                return z.getGrad().getNaziv();
            case 5:
                return z.getAdresa();
            case 6:
                return z.getBrojTelefona();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    public Zakupac getZakupac(int row){
        return lista.get(row);
    }
    public void pretrazi(String ime, String prezime, String email,Grad grad,String adresa, String brojTelefona){
        List<Zakupac> filtriranaLista = lista.stream()
        .filter(k -> (ime == null || ime.isEmpty() || k.getIme().toLowerCase().contains(ime.toLowerCase())))
        .filter(k -> (prezime == null || prezime.isEmpty() || k.getPrezime().toLowerCase().contains(prezime.toLowerCase())))
        .filter(k -> (email == null || email.isEmpty() || k.getEmail().toLowerCase().contains(email.toLowerCase())))
        .filter(k -> (grad == null || grad.getNaziv().equals("Odaberite grad") || k.getGrad().getNaziv().equals(grad.getNaziv())))
        .filter(k -> (adresa == null || adresa.isEmpty() || k.getAdresa().toLowerCase().contains(adresa.toLowerCase())))
        .filter(k -> (brojTelefona == null || brojTelefona.isEmpty() || k.getBrojTelefona().toLowerCase().contains(brojTelefona.toLowerCase())))
                .collect(Collectors.toList());        
        this.lista = filtriranaLista;
        fireTableDataChanged();
    }

    public List<Zakupac> getLista() {
        return lista;
    }
    
    
    
}
