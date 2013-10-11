/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class DAORequestImplTest {

    private DAORequestImpl dao;
    private EntityManagerFactory emf;

    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
        dao = new DAORequestImpl(emf);
    }
    
    @After
    public void tearDown() {
        emf.close();
    }

    /**
     * Test of createRequest method, of class DAORequestImpl.
     */
    @Test
    public void testSaveRequest() {
        DAOEmployeeImpl daoE = new DAOEmployeeImpl();
        daoE.setEntityManagerFactory(emf);
        Employee e = new Employee();
        e.setName("Anton Takac");
        e.setPassword("XXX");
        e.setAccessRight(AccessRight.Admin);
        daoE.createEmployee(e);
        
        Request r = new Request();
        r.setEmployee(e);
        r.setDateFrom(new Date(2013, 10, 25));
        r.setDateTo(new Date(2013, 10, 26));
        r.setDescription("Chci oktavku");
        
        dao.createRequest(r);
//        List<Request> rl = dao.getAllRequest();
        
//        assertEquals(rl.get(0), e);
    }

    /**
     * Test of deleteRequest method, of class DAORequestImpl.
     */
    @Test
    public void testDeleteRequest() {

    }

    /**
     * Test of getAllRequest method, of class DAORequestImpl.
     */
    @Test
    public void testGetAllRequest() {

    }

    /**
     * Test of getRequestById method, of class DAORequestImpl.
     */
    @Test
    public void testGetRequestById() {

    }
}