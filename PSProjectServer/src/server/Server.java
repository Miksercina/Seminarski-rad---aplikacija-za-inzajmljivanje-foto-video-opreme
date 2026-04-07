/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.util.List;
import niti.ObradaKlijentskihZahteva;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Lenovo
 */
public class Server extends Thread {
    private boolean kraj = false;
    private ServerSocket serverSocket;
    private List<ObradaKlijentskihZahteva> klijenti;

    public Server() {
        klijenti = new ArrayList<>();
    }
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000);
            while(!kraj){
                Socket s = serverSocket.accept();
                System.out.println("Klijent je povezan.");
                
                ObradaKlijentskihZahteva okz = new ObradaKlijentskihZahteva(s);
                klijenti.add(okz);
                okz.start();
            }
        } catch (IOException ex) {
            if(kraj){
                System.out.println("Server je zaustavljen");
            }else{
                ex.printStackTrace();
            }
        }
    }
    public void zaustaviServer(){
        kraj = true;
        try{   
            if(klijenti != null){
                for(ObradaKlijentskihZahteva k : klijenti){
                    k.prekini();
                }
            }
            if(serverSocket != null && !serverSocket.isClosed()){
                serverSocket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
