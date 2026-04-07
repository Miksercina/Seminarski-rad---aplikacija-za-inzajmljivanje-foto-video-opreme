/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author Aleksa
 */
public interface Repository<T> {
    
    List<T> getAll(T param, String uslov) throws Exception;
    PreparedStatement add (T param) throws Exception;
    void edit(T param) throws Exception;
    void delete(T param) throws Exception;
    List<T> getAll();
    T get(T param, String uslov) throws Exception;
    
}
