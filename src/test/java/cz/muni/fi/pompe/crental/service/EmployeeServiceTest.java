/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.entity.AccessRight;
import cz.muni.fi.pompe.crental.entity.Employee;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
    
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
        
    }
    
    @Test
    public void deleteEmployeeTest(){
        empservice.createEmployee(dtoe);
        empservice.deleteEmployee(dtoe);
        
        verify(daoemp).deleteEmployee(e);
    }
    
    @Test
    public void updateEmployeeTest(){
        empservice.createEmployee(dtoe);
        dtoe.setAccessRight(AccessRight.Employee);
        empservice.updateEmployee(dtoe);
        e.setAccessRight(AccessRight.Employee);
        verify(daoemp).updateEmployee(e);
    }
    
    @Test
    public void getAllEmployeesTest(){
        empservice.getAllEmployees();
        verify(daoemp).getAllEmployees();
    }
    
    @Test
    public void getEmployeeByIdTest(){
        empservice.getEmployeeById(Long.MIN_VALUE);
        verify(daoemp).getEmployeeById(Long.MIN_VALUE);
    }
}