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
@Transactional
public class RequestService {
    private DAORequest daoRequest;
    private DAOEmployee dAOEmployee;
    
    public void setDaoRequest(DAORequest dao) {
        this.daoRequest = dao;
    }
    
    public void setDaoEmployee(DAOEmployee dao) {
        this.dAOEmployee = dao;
    }
    
    public void createRequest(DTORequest dto) {
        this.daoRequest.createRequest(this.getRequestEntity(dto));
    }
    
    public void deleteRequest(DTORequest dto) {
        this.daoRequest.deleteRequest(new Request(dto));
    }
    
    public void updateRequest(DTORequest dto) {
        this.daoRequest.updateRequest(this.getRequestEntity(dto));
    }
    
    public DTORequest getRequestById(Long id) {
        return new DTORequest(this.daoRequest.getRequestById(id));
    }
    
    public List<DTORequest> getAllRequests() {
        return entitiesToDTOs(this.daoRequest.getAllRequest());
    }
    
    /**
     * @todo optimalization: add support on dao layer
     */
    public List<DTORequest> getUnconfirmedRequests() {
        return null;
    }
    
    private List<DTORequest> entitiesToDTOs(List<Request> entities) {
        List<DTORequest> ret = new ArrayList<>();
        
        for (Request request : entities) {
            ret.add(new DTORequest(request));
        }
        
        return ret;
    }
    
    private Request getRequestEntity(DTORequest dto) {
        Request entity = new Request(dto);
        Employee empl = dAOEmployee.getEmployeeById(dto.getEmployeeId());
        entity.setEmployee(empl);
        return entity;
    }
}
