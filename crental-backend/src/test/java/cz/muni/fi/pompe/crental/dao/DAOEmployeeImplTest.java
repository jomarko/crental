package cz.muni.fi.pompe.crental.dao;

import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.dto.AccessRight;
import cz.muni.fi.pompe.crental.dao.impl.DAOEmployeeImpl;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class DAOEmployeeImplTest {
    
    @Resource( name="employeeDao")
    private DAOEmployee daoemployee;
    
    @Test
    public void testCreateEmployee() {
        int countBefore = daoemployee.getAllEmployees().size();
        
        Employee e = new Employee();
        e.setName("Patrik Pompe");
        
        try {
            daoemployee.createEmployee(e);
             fail("create invalid Employee successful");
        } catch(DataAccessException ex){    }

        e.setPassword("xxx");
        
        try {
            daoemployee.createEmployee(e);
             fail("create invalid Employee successful");
        } catch(DataAccessException ex){    }
        
        e.setAccessRight(AccessRight.Admin);
        daoemployee.createEmployee(e);
        
        Employee e2 = new Employee();
        e2.setName("Petr Novotny");
        e2.setPassword("xxxxx");
        e2.setAccessRight(AccessRight.Employee);
        
        daoemployee.createEmployee(e2);
        
        assertEquals(countBefore + 2, daoemployee.getAllEmployees().size());
        
        try {
            daoemployee.createEmployee(null);
            fail("null has been created?!");
        } catch (DataAccessException ex) { }
        
        try {
            daoemployee.createEmployee(e2);
            fail("object with id cannot be created");
        } catch (DataAccessException ex) { }
    }
    
    @Test
    public void testUpdateEmployee() {
        Employee e = new Employee();
        e.setName("Patrik Pompe");
        e.setPassword("XXXXX");
        e.setAccessRight(AccessRight.Employee);
        
        daoemployee.createEmployee(e);
        
        e.setName("Kirtap Epmop");
        e.setPassword("YYYYY");
        e.setAccessRight(AccessRight.Employee);
        
        daoemployee.updateEmployee(e);
        
        Employee e2 = daoemployee.getEmployeeById(e.getId());
        
        assertDeepEqualsEmployee(e, e2);
        
        e2.setName(null);
        
        try {
            daoemployee.updateEmployee(e2);
            fail("Invalid employee updated");
        } catch (DataAccessException ex){ }
        
        e.setId(null);
        
        try {
            daoemployee.updateEmployee(e);
            fail("employee without id updated");
        } catch (DataAccessException ex){ }
    }
    
    @Test
    public void testDeleteEmployee() {
        int countBefore = daoemployee.getAllEmployees().size();
        
        Employee e = new Employee();
        e.setName("Patrik Pompe");
        e.setPassword("XXXXX");
        e.setAccessRight(AccessRight.Employee);
        
        daoemployee.createEmployee(e);
        
        assertEquals(countBefore + 1, daoemployee.getAllEmployees().size());
        
        daoemployee.deleteEmployee(e);
        assertEquals(countBefore, daoemployee.getAllEmployees().size());
    }
    
    @Test
    public void testGetAllEmployees() {
        List<Employee> beforeList = daoemployee.getAllEmployees();
        Employee e = newEmployee(null, "Petr Pompe", "Pass", AccessRight.Admin);
        daoemployee.createEmployee(e);
        
        List<Employee> afterList = daoemployee.getAllEmployees();
        
        
        if (beforeList.equals(afterList)) {
            fail("equals not lists expected");
        }
        
        beforeList.add(e);
        
        if (!beforeList.equals(afterList)) {
            fail("equals lists expected");
        }
    }
    
    @Test
    public void testGetEmployeeById() {
        Employee e = newEmployee(null, "Jan Kaplicky", "DDDD", AccessRight.Admin);
        daoemployee.createEmployee(e);
        
        Employee e2 = daoemployee.getEmployeeById(e.getId());
        
        assertDeepEqualsEmployee(e, e2);
        
        assertNull(daoemployee.getEmployeeById(Long.MAX_VALUE));
            
        try{
            daoemployee.getEmployeeById(null);
            fail("employee with nullable id found");
        } catch (DataAccessException ex) { }
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
