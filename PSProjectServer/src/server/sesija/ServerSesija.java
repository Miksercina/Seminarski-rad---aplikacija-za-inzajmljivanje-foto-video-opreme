/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.sesija;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ServerSesija {
    private static ServerSesija instance;
    private List<String> aktivniKorisnici = new ArrayList<>();
    
    private ServerSesija() {}
    
    public static ServerSesija getInstance() {
        if (instance == null) {
            instance = new ServerSesija();
        }
        return instance;
    }
    
    public synchronized boolean jeUlogovan(String korisnickoIme) {
        return aktivniKorisnici.contains(korisnickoIme);
    }
    
    public synchronized void dodajKorisnika(String korisnickoIme) {
        aktivniKorisnici.add(korisnickoIme);
    }
    
    public synchronized void ukloniKorisnika(String korisnickoIme) {
        aktivniKorisnici.remove(korisnickoIme);
    }
}
