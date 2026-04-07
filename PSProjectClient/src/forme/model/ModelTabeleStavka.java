/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domain.StatusStavke;
import domain.StavkaNajma;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lenovo
 */
public class ModelTabeleStavka extends AbstractTableModel {
    private final List<StavkaNajma> lista;
    private final String[] kolone = {"Redni broj", "Oprema", "Vreme izdavanja", "Vreme vracanja", "Kolicina", "Cena stavke"};
    public ModelTabeleStavka(List<StavkaNajma> lista) {
        this.lista = (lista != null) ? lista : new ArrayList<>();
        fireTableDataChanged();
    }

    public ModelTabeleStavka() {
        lista = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return ((List<StavkaNajma>) getAktivneStavke()).size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaNajma stavka = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return stavka.getRb();
            case 1:
                return stavka.getOprema().getNazivProizvodjaca() + " " + stavka.getOprema().getNazivProizvoda();
            case 2:
                return stavka.getVremeIzdavanja();
            case 3:
                return stavka.getVremeVracanja();
            case 4:
                return stavka.getKolicina();
            case 5:
                return stavka.getCena();
            default:
                throw new AssertionError();
        }
    }

    public List<StavkaNajma> getLista() {
        return lista;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column]; 
    }

    public Object getAktivneStavke() {
        List<StavkaNajma> aktivne = new ArrayList<>();
        for (StavkaNajma s : lista) {
            if (s.getStatus()!= StatusStavke.OBRISANA) {
                aktivne.add(s);
            }
        }
        return aktivne;
    }
    public void obrisiStavku(StavkaNajma s) {
        
        s.setStatus(StatusStavke.OBRISANA);
        fireTableDataChanged();
    }
    public List<StavkaNajma> getSveStavke() {
        return new ArrayList<>(lista);
    }
    public void ocistiTabelu() {
        lista.clear();
        fireTableDataChanged();
    }

    public long getUkupnaCena() {
        long ukupno = 0;
        for (StavkaNajma s : (List<StavkaNajma>)getAktivneStavke()) {
            ukupno += s.getCena();
        }
        return ukupno;
    }

    public void dodajStavku(StavkaNajma stavka) {
        lista.add(stavka);
        fireTableDataChanged();
    }
}
