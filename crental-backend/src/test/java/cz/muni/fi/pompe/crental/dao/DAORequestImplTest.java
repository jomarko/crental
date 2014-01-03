package cz.muni.fi.pompe.crental.dao;

import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.dto.AccessRight;
import cz.muni.fi.pompe.crental.entity.Request;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class DAORequestImplTest {

    @Autowired
    private DAORequest daorequest;
    @Autowired
    private DAOEmployee daoemployee;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    
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
        } catch (DataAccessException ex) {
            //OK
        }
        
        try {
            request = newRequest(1l, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
            daorequest.createRequest(request);
            fail("it was created request with set id");
        } catch (DataAccessException ex) {
            //OK
        }


        try {
            request = newRequest(null, null, sdf.parse("10/10/1991"), "carname", employee);
            daorequest.createRequest(request);
            fail("it was created request with null date");
        } catch (DataAccessException ex) {
            //OK
        }

        try {
            request = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "", employee);
            daorequest.createRequest(request);
            fail("it was created request with empty carName");
        } catch (DataAccessException ex) {
            //OK
        }
        
        try {
            employee = DAOEmployeeImplTest.newEmployee(null, "testname", "", AccessRight.Admin);
            request = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "", employee);
            daorequest.createRequest(request);
            fail("it was created request with invalid employee");
        } catch (DataAccessException ex) {
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
        } catch (DataAccessException ex) {
            //OK
        }
        
        try {
            req2 = req1;
            req1.setId(req1.getId() + 1);
            daorequest.updateRequest(req1);
            fail("it was updated request with wrong id");
        } catch (DataAccessException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDateFrom(null);
            daorequest.updateRequest(req1);
            fail("it was updated request with null date");
        } catch (DataAccessException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDescription(null);
            daorequest.updateRequest(req1);
            fail("it was updated request with null carName");
        } catch (DataAccessException ex) {
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
        
        assertTrue(daorequest.getAllRequests().size() == 2);
        
        daorequest.deleteRequest(req1);
        assertTrue(daorequest.getAllRequests().size() == 1);
        
        daorequest.deleteRequest(req2);
        assertTrue(daorequest.getAllRequests().size() == 0);
        
        // *** Incorrect part ***
        
        req1 = newRequest(null, sdf.parse("10/10/1990"), sdf.parse("10/10/1991"), "carname", employee);
        
        daorequest.createRequest(req1);
                
        try {
            daorequest.deleteRequest(null);
            fail("it was deleted null request");
        } catch (DataAccessException ex) {
            //OK
        }
        
        try {
            req2 = req1;
            req1.setId(req1.getId() + 1);
            daorequest.deleteRequest(req1);
            fail("it was deleted request with wrong id");
        } catch (DataAccessException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDescription("wrong name");
            daorequest.deleteRequest(req1);
            fail("it was deleted request with wrong carName");
        } catch (DataAccessException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            req2 = req1;
            req1.setDescription(null);
            daorequest.deleteRequest(req1);
            fail("it was updated request with null carName");
        } catch (DataAccessException ex) {
            //OK
            req1 = req2;
        }
        
        try {
            employee.setAccessRight(AccessRight.Employee);
            req1.setEmployee(employee);
            daorequest.deleteRequest(req1);
            fail("it was deleted request with wrong employee");
        } catch (DataAccessException ex) {
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
        
        assertTrue(daorequest.getAllRequests().isEmpty());
        
        daorequest.createRequest(req1);
        daorequest.createRequest(req2);

        assertTrue(daorequest.getAllRequests().size() == 2);

        List<Request> expected = Arrays.asList(req1, req2);
        List<Request> actual = daorequest.getAllRequests();
        
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
        }catch(DataAccessException ex){
            //OK
        }
        
        try{
            daorequest.getRequestById(req1.getId() + 1);
            fail("it was finded request with wrong id");
        }catch(DataAccessException ex){
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
