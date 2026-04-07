/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package konfiguracija;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class Konfiguracija {
    private static Konfiguracija instance;
    private Properties configuration;
    

    public Konfiguracija() throws IOException {
        try {
            configuration = new Properties();
            configuration.load(new FileInputStream("C:\\xampp\\htdocs\\softveri\\2019zadatak1\\PSProjectServer\\config\\config.properties"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Konfiguracija getInstance() throws IOException {
        if(instance == null){
            instance = new Konfiguracija();
        }
        return instance;
    }
    
    public String getProperty(String key){
        return configuration.getProperty(key, "n/a");
    }
   
   public void setProperty(String key, String value){
    configuration.setProperty(key, value);
   }
   
   public void saveChanges(){
    try{
         configuration.store(new FileOutputStream("C:\\xampp\\htdocs\\softveri\\2019zadatak1\\PSProjectServer\\config\\config.properties"),null);
    }catch(IOException ex){
    ex.printStackTrace();
    }
   }
    
}
