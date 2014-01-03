package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dto.DTORequest;
import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.entity.Request;
import cz.muni.fi.pompe.crental.security.Principals;
import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Service
public class RequestService extends CrentalService implements AbstractRequestService {
    private DAORequest daoRequest;
    private DAOEmployee dAOEmployee;
     
    public void setDaoRequest(DAORequest dao) {
        this.daoRequest = dao;
    }
    
    public void setDaoEmployee(DAOEmployee dao) {
        this.dAOEmployee = dao;
    }
    
    @Transactional
    @Override
    public void createRequest(DTORequest dto) {
        checkAuthentication();
        
        if(dto != null){
            Request r = this.dtoToEntity(dto);
            this.daoRequest.createRequest(r);
            dto.setId(r.getId());
        }
    }
    
    @Transactional
    @Override
    public void deleteRequest(DTORequest dto) {
        if(dto != null){
            checkPermission("request:delete:" + dto.getEmployeeId());
            this.daoRequest.deleteRequest(this.dtoToEntity(dto));
        }
    }
    
    @Transactional
    @Override
    public void updateRequest(DTORequest dto) {
        if(dto != null){
            checkPermission("request:update:" + dto.getEmployeeId());
            this.daoRequest.updateRequest(this.dtoToEntity(dto));
        }
    }
    
    @Transactional(readOnly = true)
    @Override
    public DTORequest getRequestById(Long id) {
        if(id != null){
            DTORequest dto = entityToDTO(this.daoRequest.getRequestById(id));
            checkPermission("request:get:" + dto.getEmployeeId());
            return dto;
        } else{
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<DTORequest> getAllRequests() {
        checkAuthentication();
        return entitiesToDTOs(this.daoRequest.getAllRequests());
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<DTORequest> getUnconfirmedRequests() {
        checkAuthentication();
        List<Request> unconfirmed = daoRequest.getUnconfirmedRequests();
        
        Subject user = SecurityUtils.getSubject();
        if(user.hasRole("employee")){
            List<Request> toDelete = new ArrayList<>();
            Principals p = (Principals) user.getPrincipals().getPrimaryPrincipal();
            for(Request r : unconfirmed){
                if(!r.getEmployee().getId().equals(p.getId())){
                    toDelete.add(r);
                }
            }            
            unconfirmed.removeAll(toDelete);
        }
        
        return entitiesToDTOs(unconfirmed);
    }
    
    private List<DTORequest> entitiesToDTOs(List<Request> entities) {
        List<DTORequest> ret = new ArrayList<>();
        
        for (Request request : entities) {
            ret.add(entityToDTO(request));
        }
        
        return ret;
    }
    
    public Request dtoToEntity(DTORequest dto) {
        Request entity = new Request();
        Employee empl = dAOEmployee.getEmployeeById(dto.getEmployeeId());
        entity.setEmployee(empl);
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setDescription(dto.getDescription());
        entity.setId(dto.getId());
        return entity;
    }
    
    public DTORequest entityToDTO(Request entity) {
        DTORequest dto = new DTORequest();
            
        dto.setId(entity.getId());
        dto.setDateFrom(entity.getDateFrom());
        dto.setDateTo(entity.getDateTo());
        dto.setDescription(entity.getDescription());       
        dto.setEmployeeName(entity.getEmployee().getName());
        dto.setEmployeeId(entity.getEmployee().getId());
        
        return dto;
    }
}
