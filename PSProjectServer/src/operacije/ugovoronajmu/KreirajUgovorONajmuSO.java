/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.ugovoronajmu;

import domain.Oprema;
import domain.StavkaNajma;
import domain.UgovorONajmu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Aleksa
 */
public class KreirajUgovorONajmuSO extends ApstraktnaGenerickaOperacija {

        @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof UgovorONajmu)) {
            throw new Exception("Sistem ne moze da kreira ugovor o najmu.");
        }

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        UgovorONajmu ugovor = (UgovorONajmu) param;
        if(ugovor.getStavke().size() > 3){
            ugovor.setUkupnaCena(ugovor.getUkupnaCena()*0.9);
        }
        PreparedStatement ps = broker.add(param);
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        Long id = rs.getLong(1);
        ugovor.setIdUgovorONajmu(id);
        
        
        for (StavkaNajma sn : ugovor.getStavke()) {
            sn.setUgovorONajmu(ugovor);
            broker.add(sn);

            Oprema o = sn.getOprema();
            o.setDostupnaKolicina(o.getDostupnaKolicina() - sn.getKolicina());
            broker.edit(o); 
    }
        




    }
}