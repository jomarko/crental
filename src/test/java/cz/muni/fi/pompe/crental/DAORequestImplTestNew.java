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
public class DAORequestImplTestNew {
    private EntityManagerFactory emf;
    private DAORequestImpl daorequest;
    private DAOEmployeeImpl daoemployee;
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
        daorequest = new DAORequestImpl(emf);
        daoemployee = new DAOEmployeeImpl();
        daoemployee.setEntityManagerFactory(emf);
    }
    
    @After
    public void tearDown() {
        emf.close();
    }


    /**
     * Test of deleteRequest method, of class DAORequestImpl.
     */
    @Test
    public void testDeleteRequest() {
        System.out.println("deleteRequest");
        
        Employee e = new Employee();
        e.setAccessRight(AccessRight.Employee);
        e.setName("Patrik Pompe");
        e.setPassword("XZXX");
        
        this.daoemployee.createEmployee(e);
        
        int before = this.daorequest.getAllRequest().size();
        
        Request r = new Request();
        r.setDateFrom(new Date());
        r.setDateTo(new Date());
        r.setEmployee(e);
        r.setDescription("Chci fabii");
        this.daorequest.createRequest(r);
        assertEquals(before + 1, this.daorequest.getAllRequest().size());
        
        this.daorequest.deleteRequest(r);
        
        assertEquals(before, this.daorequest.getAllRequest().size());
    }

    /**
     * Test of createRequest method, of class DAORequestImpl.
     */
    @Test
    public void testCreateRequest() {
        System.out.println("createRequest");
        
        Employee e = new Employee();
        e.setAccessRight(AccessRight.Employee);
        e.setName("Patrik Pompe");
        e.setPassword("XZXX");
        
        this.daoemployee.createEmployee(e);
        
        int before = this.daorequest.getAllRequest().size();
        
        Request r = new Request();
        r.setDateFrom(new Date());
        r.setDateTo(new Date());
        r.setEmployee(e);
        r.setDescription("Chci fabii");
        this.daorequest.createRequest(r);
        
        Request r2 = new Request();
        r2.setDateFrom(new Date());
        r2.setDateTo(new Date());
        r2.setEmployee(e);
        r2.setDescription("Chci Octavii");
        this.daorequest.createRequest(r2);
        
        assertEquals(before + 2, this.daorequest.getAllRequest().size());
        
    }

    /**
     * Test of updateRequest method, of class DAORequestImpl.
     */
    @Test
    public void testUpdateRequest() {
        System.out.println("updateRequest");

        Employee e = new Employee();
        e.setAccessRight(AccessRight.Employee);
        e.setName("Patrik Pompe");
        e.setPassword("XZXX");
        
        this.daoemployee.createEmployee(e);
        
        Employee e2 = new Employee();
        e2.setAccessRight(AccessRight.Employee);
        e2.setName("Anton AusTyrol");
        e2.setPassword("XZXX");
        
        this.daoemployee.createEmployee(e2);
        
        Request r = new Request();
        r.setDateFrom(new Date());
        r.setDateTo(new Date());
        r.setEmployee(e);
        r.setDescription("Chci fabii");
        this.daorequest.createRequest(r);
        
        Date tommorow = new Date(113, 9,12);
        String descNew = "Chci octavii";
        
        r.setDateFrom(tommorow);
        r.setDateTo(tommorow);
        r.setDescription(descNew);
        r.setEmployee(e2);
        
        daorequest.updateRequest(r);
        
        Request rFromDb = daorequest.getRequestById(r.getId());
        
        assertEquals(rFromDb.getDateFrom(), r.getDateFrom());
        assertEquals(rFromDb.getDateTo(), r.getDateTo());
        assertEquals(rFromDb.getDescription(), r.getDescription());
        assertEquals(rFromDb.getEmployee(), r.getEmployee());
        assertEquals(rFromDb.getId(), r.getId());
    }
}