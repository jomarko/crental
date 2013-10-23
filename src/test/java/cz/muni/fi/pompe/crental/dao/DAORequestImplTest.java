package cz.muni.fi.pompe.crental.dao;

import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.entity.AccessRight;
import cz.muni.fi.pompe.crental.entity.Request;
import cz.muni.fi.pompe.crental.dao.impl.DAORequestImpl;
import cz.muni.fi.pompe.crental.dao.impl.DAOEmployeeImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import static junit.framework.Assert.assertEquals;
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
public class DAORequestImplTest {

    private EntityManagerFactory emf;
    private DAORequestImpl daorequest;
    private DAOEmployeeImpl daoemployee;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
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
     * Test of createRequest method, of class DAORequestImpl.
     */
    @Test
    public void testCreateRequest() throws ParseException {
        // *** Correct part ***
        
        Employee employee = DAOEmployeeImplTest.newEmployee(null, "testname", "testpasswd", AccessRight.Admin);
        daoemployee.createEmployee(employee);
        
        Request request = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
        daorequest.createRequest(request);
        
        assertNotNull(request.getId());
        Request result = daorequest.getRequestById(request.getId());
        assertDeepEqualsRequest(request, result);
        
        // *** Incorrect part **
        
        try {
            daorequest.createRequest(null);
            fail("it was created null request");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (NullPointerException npe) {
            //OK
        }
        
        try {
            request = newRequest(1l, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
            daorequest.createRequest(request);
            fail("it was created request with set id");
        } catch (IllegalArgumentException ex) {
            //OK
        }


        try {
            request = newRequest(null, null, sdf.parse("10/10/1991"), "carname", employee);
            daorequest.createRequest(request);
            fail("it was created request with null date");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            request = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "", employee);
            daorequest.createRequest(request);
            fail("it was created request with empty carName");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            employee = DAOEmployeeImplTest.newEmployee(null, "testname", "", AccessRight.Admin);
            request = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "", employee);
            daorequest.createRequest(request);
            fail("it was created request with invalid employee");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    /**
     * Test of updateRequest method, of class DAORequestImpl.
     */
    @Test
    public void testUpdateRequest() throws ParseException {
        // *** Correct part ***
        
        Employee employee = DAOEmployeeImplTest.newEmployee(null, "testname", "testpasswd", AccessRight.Admin);
        daoemployee.createEmployee(employee);
        
        Request req1 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
        daorequest.createRequest(req1);
        
        req1.setDateFrom(sdf.parse("11/11/1990"));
        daorequest.updateRequest(req1);
        Request req2 = daorequest.getRequestById(req1.getId());
        assertDeepEqualsRequest(req1, req2);
        
        req1.setDescription("carname2");
        daorequest.updateRequest(req1);
        req2 = daorequest.getRequestById(req1.getId());
        assertDeepEqualsRequest(req1, req2);
                
        // *** Incorrect part ***
        
        try {
            daorequest.updateRequest(null);
            fail("it was updated null request");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (NullPointerException npe) {
            //OK
        }
        
        try {
            req2 = req1;
            req1.setId(req1.getId() + 1);
            daorequest.updateRequest(req1);
            fail("it was updated request with wrong id");
        } catch (NullPointerException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDateFrom(null);
            daorequest.updateRequest(req1);
            fail("it was updated request with null date");
        } catch (IllegalArgumentException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDescription(null);
            daorequest.updateRequest(req1);
            fail("it was updated request with null carName");
        } catch (IllegalArgumentException ex) {
            //OK
            req1 = req2;
        }
    }

    /**
     * Test of deleteRequest method, of class DAORequestImpl.
     */
    @Test
    public void testDeleteRequest() throws ParseException {
        // *** Correct part ***
        
        Employee employee = DAOEmployeeImplTest.newEmployee(null, "testname", "testpasswd", AccessRight.Admin);
        daoemployee.createEmployee(employee);

        Request req1 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
        Request req2 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "name of car", employee);
        
        daorequest.createRequest(req1);
        daorequest.createRequest(req2);
        
        assertTrue(daorequest.getAllRequest().size() == 2);
        
        daorequest.deleteRequest(req1);
        assertTrue(daorequest.getAllRequest().size() == 1);
        
        daorequest.deleteRequest(req2);
        assertTrue(daorequest.getAllRequest().size() == 0);
        
        // *** Incorrect part ***
        
        req1 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
        
        daorequest.createRequest(req1);
                
        try {
            daorequest.deleteRequest(null);
            fail("it was deleted null request");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (NullPointerException npe) {
            //OK
        }
        
        try {
            req2 = req1;
            req1.setId(req1.getId() + 1);
            daorequest.deleteRequest(req1);
            fail("it was deleted request with wrong id");
        } catch (IllegalArgumentException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDescription("wrong name");
            daorequest.deleteRequest(req1);
            fail("it was deleted request with wrong carName");
        } catch (IllegalArgumentException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDescription(null);
            daorequest.deleteRequest(req1);
            fail("it was updated request with null carName");
        } catch (IllegalArgumentException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            employee.setAccessRight(AccessRight.Employee);
            req1.setEmployee(employee);
            daorequest.deleteRequest(req1);
            fail("it was deleted request with wrong employee");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    /**
     * Test of getAllRequest method, of class DAORequestImpl.
     */
    @Test
    public void testGetAllRequest() throws ParseException {
               
        Employee employee = DAOEmployeeImplTest.newEmployee(null, "testname", "testpasswd", AccessRight.Admin);
        daoemployee.createEmployee(employee);
        
        Request req1 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
        Request req2 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "name of car", employee);
        
        assertTrue(daorequest.getAllRequest().isEmpty());
        
        daorequest.createRequest(req1);
        daorequest.createRequest(req2);

        assertTrue(daorequest.getAllRequest().size() == 2);

        List<Request> expected = Arrays.asList(req1, req2);
        List<Request> actual = daorequest.getAllRequest();
        
        Comparator<Request> idComparator = new Comparator<Request>() {

            @Override
            public int compare(Request o1, Request o2) {
                return Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId()));
            }
        };
        
        Collections.sort(actual, idComparator);
        Collections.sort(expected, idComparator);

        assertDeepEqualsRequest(expected, actual);
    }

    /**
     * Test of getRequestById method, of class DAORequestImpl.
     */
    @Test
    public void testGetRequestById() throws ParseException {
        
        Employee employee = DAOEmployeeImplTest.newEmployee(null, "testname", "testpasswd", AccessRight.Admin);
        daoemployee.createEmployee(employee);
        
        Request req1 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
        daorequest.createRequest(req1);
        
        Request req2 = daorequest.getRequestById(req1.getId());
        
        assertDeepEqualsRequest(req1, req2);
        
        try{
            daorequest.getRequestById(null);
            fail("it was finded request with null id");
        }catch(NullPointerException ex){
            //OK
        }
        
        try{
            daorequest.getRequestById(req1.getId() + 1);
            fail("it was finded request with wrong id");
        }catch(NoResultException ex){
            //OK
        }
    }
    
    static Request newRequest(Long id, Date from, Date to, String carName, Employee e) {
        Request r = new Request();
        r.setId(id);
        r.setDateFrom(from);
        r.setDateTo(to);
        r.setDescription(carName);
        r.setEmployee(e);
        return r;
    }
    
    static void assertDeepEqualsRequest(Request expected, Request actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDateFrom(), actual.getDateFrom());
        assertEquals(expected.getDateTo(), actual.getDateTo());
        assertEquals(expected.getDescription(), actual.getDescription());
        DAOEmployeeImplTest.assertDeepEqualsEmployee(expected.getEmployee(), actual.getEmployee());
    }
     
    static void assertDeepEqualsRequest(List<Request> expectedList, List<Request> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Request expected = expectedList.get(i);
            Request actual = actualList.get(i);
            assertDeepEqualsRequest(expected, actual);
        }
    } 
}
