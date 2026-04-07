/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.ugovoronajmu;

import domain.Oprema;
import domain.StavkaNajma;
import domain.UgovorONajmu;
import java.util.Date;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Aleksa
 */
public class IzmeniUgovorONajmuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (!(param instanceof UgovorONajmu)) {
            throw new Exception("Sistem ne može da zapamti ugovor o najmu.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        UgovorONajmu u = (UgovorONajmu) param;
        List<StavkaNajma> stavke = u.getStavke();

        for (StavkaNajma s : stavke) {
            s.setUgovorONajmu(u);

            switch (s.getStatus()) {
                case NOVA:
                    broker.add(s);
                    Oprema oNova = s.getOprema();
                    oNova.setDostupnaKolicina(oNova.getDostupnaKolicina() - s.getKolicina());
                    broker.edit(oNova);
                    break;
                case OBRISANA:
                    broker.delete(s);
                    Oprema oObrisana = s.getOprema();
                    oObrisana.setDostupnaKolicina(oObrisana.getDostupnaKolicina() + s.getKolicina());
                    broker.edit(oObrisana);
                    break;
                case IZMENJENA:
                    broker.edit(s);  
                    break;
                case POSTOJECA:
                   
                    break;
            }
        }

        broker.edit(u);  
    }
}