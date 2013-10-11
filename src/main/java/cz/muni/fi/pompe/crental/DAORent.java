/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import java.util.List;

/**
 *
 * @author jozef
 */
public interface DAORent {
    void createRent(Rent rent);
   
    void deleteRent(Rent rent);
    
    void updateRent(Rent rent);
    
    List<Rent> getAllRents();
    
    Rent getRentById(Long id);
}
