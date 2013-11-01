package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dto.DTORequest;
import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.entity.Request;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Service
public class RequestService {
    private DAORequest daoRequest;
    private DAOEmployee dAOEmployee;
    
    public void setDaoRequest(DAORequest dao) {
        this.daoRequest = dao;
    }
    
    public void setDaoEmployee(DAOEmployee dao) {
        this.dAOEmployee = dao;
    }
    
    @Transactional
    public void createRequest(DTORequest dto) {
        this.daoRequest.createRequest(this.dtoToEntity(dto));
    }
    
    @Transactional
    public void deleteRequest(DTORequest dto) {
        this.daoRequest.deleteRequest(this.dtoToEntity(dto));
    }
    
    @Transactional
    public void updateRequest(DTORequest dto) {
        this.daoRequest.updateRequest(this.dtoToEntity(dto));
    }
    
    @Transactional(readOnly = true)
    public DTORequest getRequestById(Long id) {
        return entityToDTO(this.daoRequest.getRequestById(id));
    }
    
    @Transactional(readOnly = true)
    public List<DTORequest> getAllRequests() {
        return entitiesToDTOs(this.daoRequest.getAllRequests());
    }
    
    @Transactional(readOnly = true)
    public List<DTORequest> getUnconfirmedRequests() {
        return entitiesToDTOs(daoRequest.getUnconfirmedRequests());
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
