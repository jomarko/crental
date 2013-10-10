/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jozef
 */
public class DAOEmployeeImplTest {
    
    private EntityManagerFactory emf;
    private DAOEmployeeImpl daoemployee;
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
        daoemployee = new DAOEmployeeImpl();
        daoemployee.setEntityManagerFactory(emf);
    }
    
    @After
    public void tearDown() {
        emf.close();
    }
    
    @Test
    public void testCreateEmployee() {
        
    }
    
    @Test
    public void testUpdateEmployee() {
        
    }
    
    @Test
    public void testDeleteEmployee() {
        
    }
    
    @Test
    public void testGetAllRequest() {
        
    }
    
    @Test
    public void testGetEmployeeById() {
        
    }
}
