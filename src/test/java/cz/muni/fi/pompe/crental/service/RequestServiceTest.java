package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dto.DTORequest;
import cz.muni.fi.pompe.crental.entity.AccessRight;
import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.entity.Request;
import java.util.Date;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest extends AbstractIntegrationTest {
    @Mock
    DAORequest daoRequest;
    
    @Mock
    DAOEmployee daoEmployee;
    
    @InjectMocks
    @Autowired
    RequestService requestService;
    
    private Employee employee;
    private Request request;
    private DTORequest dto;
       
    public RequestServiceTest() {
        employee = new Employee();
        employee.setAccessRight(AccessRight.Employee);
        employee.setId(1L);
        employee.setName("Tester Testaros");
        employee.setPassword("xxx");
    }
    
    @Before
    public void setUp() {    
        doReturn(employee).when(daoEmployee).getEmployeeById(anyLong());
        
        request = new Request();
        request.setDateFrom(new Date(113, 11, 1));
        request.setDateTo(new Date(113, 11, 2));
        request.setDescription("Chci oktavii na den");
        request.setEmployee(employee);

        dto = new DTORequest();
        dto.setDateFrom(request.getDateFrom());
        dto.setDateTo(request.getDateTo());
        dto.setDescription(request.getDescription());
        dto.setEmployeeId(employee.getId());
        dto.setEmployeeName(employee.getName());
    }
    
    @Test
    public void testCreateRequest() {
        requestService.createRequest(dto);
        verify(daoRequest).createRequest(request);
    }
    
    @Test
    public void testCreateNullRequest() {
        try{
            requestService.createRequest(null);
            fail("null dto");
        } catch(NullPointerException ex){    }
    }
    
    @Test 
    public void testDeleteRequest() {
        dto.setId(1L);
        request.setId(1L);
        requestService.deleteRequest(dto);
        verify(daoRequest).deleteRequest(request);
    }
    
    @Test
    public void testDeleteNullRequest() {
        try{
            requestService.deleteRequest(null);
            fail("null dto");
        } catch(NullPointerException ex){    }
    }
}
