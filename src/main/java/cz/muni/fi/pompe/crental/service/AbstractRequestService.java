/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dto.DTORequest;
import java.util.List;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public interface AbstractRequestService {

    /**
     * Create new request
     * @param dto 
     */
    void createRequest(DTORequest dto);

    /**
     * delete passed request
     * @param dto 
     */
    void deleteRequest(DTORequest dto);

    /**
     * get list of all requests
     * @return 
     */
    List<DTORequest> getAllRequests();

    /**
     * get request with passed id
     * @param id
     * @return 
     */
    DTORequest getRequestById(Long id);

    /**
     * get list of all unconfirmed requests
     * @return 
     */
    List<DTORequest> getUnconfirmedRequests();

    void setDaoEmployee(DAOEmployee dao);

    void setDaoRequest(DAORequest dao);

    /**
     * update passed request
     * @param dto 
     */
    void updateRequest(DTORequest dto);
    
}
