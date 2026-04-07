/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.stavke;

import domain.StavkaNajma;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class VratiStavkeSO extends ApstraktnaGenerickaOperacija {
    List<StavkaNajma> stavke;
    @Override
    protected void preduslovi(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        String uslov = " JOIN oprema ON stavkanajma.idOprema=oprema.idOprema WHERE idUgovorONajmu=" + (Long)param;
        stavke = broker.getAll(new StavkaNajma(), uslov);
    }

    public List<StavkaNajma> getStavke() {
        return stavke;
    }
    
}
