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
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
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
    private EmployeeService empservice;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        empservice = new EmployeeService();
        empservice.setDaoemployee(daoemp);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void createEmployeeTest(){
        Employee e = new Employee();
        e.setAccessRight(AccessRight.Admin);
        e.setName("admin");
        e.setPassword("admin");
        
        DTOEmployee dtoe = new DTOEmployee();
        dtoe.setAccessRight(AccessRight.Admin);
        dtoe.setName("admin");
        dtoe.setPassword("admin");
        
        empservice.createEmployee(dtoe);
        
        verify(daoemp).createEmployee(e);
        
    }
    
    @Test
    public void deleteEmployeeTest(){
    }
    
    @Test
    public void updateEmployeeTest(){
    }
    
    @Test
    public void getAllEmployeesTest(){
    }
    
    @Test
    public void getEmployeeByIdTest(){
    }
}