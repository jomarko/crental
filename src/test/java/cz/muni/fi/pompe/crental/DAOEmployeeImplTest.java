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
import static org.junit.Assert.*;

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
    
    static Employee newEmployee(Long id, String name, String passwd, AccessRight ar){
        Employee e = new Employee();
        e.setId(id);
        e.setName(name);
        e.setPassword(passwd);
        e.setAccessRight(ar);
        return e;
    }
    
    static void assertDeepEqualsEmployee(Employee expected, Employee actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getAccessRight(), actual.getAccessRight());
    }
}
