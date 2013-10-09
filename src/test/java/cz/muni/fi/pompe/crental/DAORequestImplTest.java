/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class DAORequestImplTest {

    public DAORequestImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createRequest method, of class DAORequestImpl.
     */
    @Test
    public void testCreateRequest() {
        System.out.println("createRequest");
        Request r = null;
        DAORequestImpl instance = new DAORequestImpl();
        instance.createRequest(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateRequest method, of class DAORequestImpl.
     */
    @Test
    public void testUpdateRequest() {
        System.out.println("updateRequest");
        Request r = null;
        DAORequestImpl instance = new DAORequestImpl();
        instance.updateRequest(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRequest method, of class DAORequestImpl.
     */
    @Test
    public void testDeleteRequest() {
        System.out.println("deleteRequest");
        Request r = null;
        DAORequestImpl instance = new DAORequestImpl();
        instance.deleteRequest(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllRequest method, of class DAORequestImpl.
     */
    @Test
    public void testGetAllRequest() {
        System.out.println("getAllRequest");
        DAORequestImpl instance = new DAORequestImpl();
        List expResult = null;
        List result = instance.getAllRequest();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRequestById method, of class DAORequestImpl.
     */
    @Test
    public void testGetRequestById() {
        System.out.println("getRequestById");
        Long id = null;
        DAORequestImpl instance = new DAORequestImpl();
        Request expResult = null;
        Request result = instance.getRequestById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}