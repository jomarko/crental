package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dto.DTORequest;
import cz.muni.fi.pompe.crental.dto.AccessRight;
import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.entity.Request;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

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
        request.setId(1l);
        request.setDateFrom(new Date(113, 11, 1));
        request.setDateTo(new Date(113, 11, 2));
        request.setDescription("Chci oktavii na den");
        request.setEmployee(employee);

        dto = requestService.entityToDTO(request);
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
        } catch(NullPointerException ex){
            fail("service don't call dao if has got null argument");
        }
    }
    
    @Test 
    public void testDeleteRequest() {
        requestService.deleteRequest(dto);
        verify(daoRequest).deleteRequest(request);
    }
    
    @Test
    public void testDeleteNullRequest() {
        try{
            requestService.deleteRequest(null);
        } catch(NullPointerException ex){  
            fail("service don't call dao if has got null argument");
        }
    }
    
    @Test
    public void testUpdateRequest() {
        dto.setDateTo(new Date(113, 11, 4));
        dto.setDescription("Test");
        request.setDateTo(dto.getDateTo());
        request.setDescription(dto.getDescription());
        
        
        requestService.updateRequest(dto);
        verify(daoRequest).updateRequest(request);
        
        dto.setId(null);
        request.setId(dto.getId());
        doThrow(new DataIntegrityViolationException("fail")).when(daoRequest).updateRequest(request);
        
        try {
            requestService.updateRequest(dto);
            fail("update dto with null id");
        } catch(DataAccessException ex){    }
    }
    
    @Test
    public void testGetRequestById() {
        doReturn(request).when(daoRequest).getRequestById(anyLong());
        doThrow(new DataIntegrityViolationException("fail")).when(daoRequest).getRequestById(null);
        DTORequest dto2 = requestService.getRequestById(1L);        
        verify(daoRequest).getRequestById(1L);
        assertEquals(dto, dto2);
        
        assertNull(requestService.getRequestById(null));
    }
    
    @Test
    public void testGetAllRequests() {
        List<Request> retReq = new ArrayList<>();
        retReq.add(request);
        
        doReturn(retReq).when(daoRequest).getAllRequests();
        
        List <DTORequest> retDTO = new ArrayList<>();
        retDTO.add(dto);
        
        assertEquals(retDTO, requestService.getAllRequests());
        verify(daoRequest).getAllRequests();
    }
    
    @Test
    public void testGetUnconfirmedRequests() {
        List<Request> retReq = new ArrayList<>();
        retReq.add(request);
        
        doReturn(retReq).when(daoRequest).getUnconfirmedRequests();
        
        List <DTORequest> retDTO = new ArrayList<>();
        retDTO.add(dto);
        
        assertEquals(retDTO, requestService.getUnconfirmedRequests());
        verify(daoRequest).getUnconfirmedRequests();
    }
}
