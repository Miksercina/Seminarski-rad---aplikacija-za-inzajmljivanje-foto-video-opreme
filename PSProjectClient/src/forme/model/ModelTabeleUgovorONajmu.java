/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domain.UgovorONajmu;
import domain.Zakupac;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lenovo
 */
public class ModelTabeleUgovorONajmu extends AbstractTableModel {
    List<UgovorONajmu> lista = new ArrayList<>();
    String[] kolone = {"Id","Zakupodavac","Zakupac","Ukupna cena"};

    public ModelTabeleUgovorONajmu(List<UgovorONajmu> lista) {
        this.lista = lista != null ? lista : new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UgovorONajmu u = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return u.getIdUgovorONajmu();
            case 1:
                return u.getZakupodavac().getNaziv();
            case 2:
                return u.getZakupac().getIme() + " " + u.getZakupac().getPrezime();
            case 3:
                return u.getUkupnaCena();
            default:
                return "N/A";
        }
    }

    public List<UgovorONajmu> getLista() {
        return lista;
    }
    public void pretrazi(String imeZ, String prezimeZ, String nazivT) {
        List<UgovorONajmu> filteredList = lista.stream()
            .filter(e -> (imeZ == null || imeZ.isEmpty() || e.getZakupac().getIme().toLowerCase().contains(imeZ.toLowerCase())))
            .filter(e -> (prezimeZ == null || prezimeZ.isEmpty() || e.getZakupac().getPrezime().toLowerCase().contains(prezimeZ.toLowerCase())))
            .filter(e -> (nazivT == null || nazivT.isEmpty() || e.getZakupodavac().getNaziv().toLowerCase().contains(nazivT.toLowerCase())))
            .collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }

    public void pretrazi(String imeZ, String prezimeZ) {
        List<UgovorONajmu> filteredList = lista.stream()
            .filter(e -> (imeZ == null || imeZ.isEmpty() || e.getZakupac().getIme().toLowerCase().contains(imeZ.toLowerCase())))
            .filter(e -> (prezimeZ == null || prezimeZ.isEmpty() || e.getZakupac().getPrezime().toLowerCase().contains(prezimeZ.toLowerCase())))
            
            .collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }

    public void pretrazi(Zakupac k) {
        Zakupac placeholderZakupac = new Zakupac();
        placeholderZakupac.setIme("Odaberite zakupca");

        List<UgovorONajmu> filteredList = lista.stream()

            .filter(l -> k == null 
                         || "Odaberite zakupca".equals(k.getIme()) 
                         || l.getZakupac().equals(k))



            .collect(Collectors.toList());

        this.lista = filteredList;
        fireTableDataChanged();
}   
    
}
