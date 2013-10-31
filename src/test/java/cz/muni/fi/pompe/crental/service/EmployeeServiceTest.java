/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.entity.AccessRight;
import cz.muni.fi.pompe.crental.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
public class EmployeeServiceTest extends AbstractIntegrationTest{
    
    @Mock
    private DAOEmployee daoemp;
    
    @InjectMocks
    @Autowired
    private EmployeeService empservice;
    
    private Employee e;
    private DTOEmployee dtoe;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        e = new Employee();
        e.setAccessRight(AccessRight.Admin);
        e.setName("admin");
        e.setPassword("admin");
        
        dtoe = new DTOEmployee();
        dtoe.setAccessRight(AccessRight.Admin);
        dtoe.setName("admin");
        dtoe.setPassword("admin");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void createEmployeeTest(){
        empservice.createEmployee(dtoe);
        verify(daoemp).createEmployee(e);
        
        dtoe.setId(Long.MIN_VALUE);
        e.setId(Long.MIN_VALUE);
        doThrow(new DataIntegrityViolationException("fail")).when(daoemp).createEmployee(e);
        
        try{
            empservice.createEmployee(dtoe);
        }catch(DataAccessException ex){
            //ok
        }
    }
    
    @Test
    public void deleteEmployeeTest(){
        empservice.createEmployee(dtoe);
        empservice.deleteEmployee(dtoe);
        
        verify(daoemp).deleteEmployee(e);
        
        dtoe.setId(null);
        e.setId(null);
        doThrow(new DataIntegrityViolationException("fail")).when(daoemp).deleteEmployee(e);
        
        try{
            empservice.deleteEmployee(dtoe);
        }catch(DataAccessException ex){
            //ok
        }
    }
    
    @Test
    public void updateEmployeeTest(){
        empservice.createEmployee(dtoe);
        dtoe.setAccessRight(AccessRight.Employee);
        empservice.updateEmployee(dtoe);
        e.setAccessRight(AccessRight.Employee);
        verify(daoemp).updateEmployee(e);
        
        dtoe.setId(null);
        e.setId(null);
        doThrow(new DataIntegrityViolationException("fail")).when(daoemp).updateEmployee(e);
        
        try{
            empservice.updateEmployee(dtoe);
        }catch(DataAccessException ex){
            //ok
        }
    }
    
    @Test
    public void getAllEmployeesTest(){
        empservice.getAllEmployees();
        verify(daoemp).getAllEmployees();
        
        List<Employee> result = new ArrayList<Employee>();
        result.add(new Employee());
        
        List<DTOEmployee> resultService = new ArrayList<DTOEmployee>();
        resultService.add(new DTOEmployee());
        
        doReturn(result).when(daoemp).getAllEmployees();
        
        assertEquals(resultService.size(), empservice.getAllEmployees().size());
        assertEquals(resultService.get(0), empservice.getAllEmployees().get(0));
    }
    
    @Test
    public void getEmployeeByIdTest(){
        empservice.getEmployeeById(Long.MIN_VALUE);
        verify(daoemp).getEmployeeById(Long.MIN_VALUE);
        
        doReturn(new Employee()).when(daoemp).getEmployeeById(Long.MIN_VALUE);
        assertEquals(new DTOEmployee(), empservice.getEmployeeById(Long.MIN_VALUE));
        
        doReturn(null).when(daoemp).getEmployeeById(Long.MIN_VALUE);
        assertNull(empservice.getEmployeeById(Long.MIN_VALUE));
    }
}