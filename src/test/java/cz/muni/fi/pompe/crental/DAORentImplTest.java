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
public class DAORentImplTest {
    private EntityManagerFactory emf;
    private DAORentImpl daorent;
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
        daorent = new DAORentImpl();
        daorent.setEntityManagerFactory(emf);
    }
    
    @After
    public void tearDown() {
        emf.close();
    }
    
    @Test
    public void testCreateRent() {
        
    }
    
    @Test
    public void testUpdateRent() {
        
    }
    
    @Test
    public void testDeleteRent() {
        
    }
    
    @Test
    public void testGetAllRequest() {
        
    }
    
    @Test
    public void testGetRentById() {
        
    }
}
