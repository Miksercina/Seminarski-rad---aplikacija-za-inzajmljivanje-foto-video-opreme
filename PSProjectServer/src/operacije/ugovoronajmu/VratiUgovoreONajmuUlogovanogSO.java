/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.ugovoronajmu;

import domain.UgovorONajmu;
import domain.Zakupodavac;
import java.util.List;
import operacije.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Aleksa
 */
public class VratiUgovoreONajmuUlogovanogSO extends ApstraktnaGenerickaOperacija {

   private List<UgovorONajmu> lista;

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Zakupodavac)) {
            throw new Exception("Parametar mora biti objekat klase trener.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Zakupodavac z = (Zakupodavac) param;

        String uslov = " JOIN zakupac ON ugovoronajmu.idZakupac = zakupac.idZakupac " +
                       "JOIN zakupodavac ON ugovoronajmu.idZakupodavac= zakupodavac.idZakupodavac " +
                       "JOIN grad ON zakupac.postanskiBroj = grad.postanskiBroj " +
                       "WHERE ugovoronajmu.idZakupodavac= " + z.getIdZakupodavac();

        lista = broker.getAll(new UgovorONajmu(), uslov);
    }

    public List<UgovorONajmu> getLista() {
        return lista;
    }
    
    
}

