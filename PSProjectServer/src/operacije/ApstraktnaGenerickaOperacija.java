/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;
import repository.db.impl.DbRepositoryGeneric;
import repository.*;
/**
 *
 * @author Lenovo
 */
public abstract class ApstraktnaGenerickaOperacija {
    protected final Repository broker;

    public ApstraktnaGenerickaOperacija() {
        this.broker = new DbRepositoryGeneric();
    }
     
     public void izvrsi(Object param, String key) throws Exception {
        try {
            preduslovi(param);
            startTransaction();
            izvrsiOperaciju(param, key);
            commitTransaction();
            System.out.println("Uspesno izvrsena operacija!");
        } catch (Exception exception) {
            
   
    rollbackTransaction();
    throw exception; 
        }finally{
       // cancelConnection();
        }
        
    }
     
     
     protected abstract void preduslovi(Object param) throws Exception;

    protected abstract void izvrsiOperaciju(Object param, String key) throws Exception;

    private void startTransaction() throws Exception {
        ((DbRepository) broker).connect();
    }

    protected void commitTransaction() throws Exception {
        ((DbRepository) broker).commit();
    }

    protected void rollbackTransaction() throws Exception {
        ((DbRepository) broker).rollback();
    }
    
    protected void cancelConnection() throws Exception {
    
    ((DbRepository) broker).disconnect();
    }
}
