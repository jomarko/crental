/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jozef
 */
public class DAOCarImplTest {
    private EntityManagerFactory emf;
    private DAOCarImpl daocar;
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
        daocar = new DAOCarImpl();
        daocar.setEntityManagerFactory(emf);
    }
    
    @After
    public void tearDown() {
        emf.close();
    }
    
    @Test
    public void testCreateCar() {
        
    }
    
    @Test
    public void testUpdateCar() {
        
    }
    
    @Test
    public void testDeleteCar() {
        
    }
    
    @Test
    public void testGetAllRequest() {
        
    }
    
    @Test
    public void testGetCarById() {
        
    }
}
