/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.ugovoronajmu;

import domain.UgovorONajmu;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Lenovo
 */
public class VratiUgovoreONajmuSO extends ApstraktnaGenerickaOperacija {
    private List<UgovorONajmu> ugovori;
    @Override
    protected void preduslovi(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param, String key) throws Exception {
        String uslov = " JOIN zakupac ON ugovoronajmu.idZakupac = zakupac.idZakupac JOIN zakupodavac ON ugovoronajmu.idZakupodavac=zakupodavac.idzakupodavac JOIN grad ON zakupac.postanskiBroj=grad.postanskiBroj";
        ugovori = broker.getAll(new UgovorONajmu(), uslov);
    }

    public List<UgovorONajmu> getUgovori() {
        return ugovori;
    }
    
}
