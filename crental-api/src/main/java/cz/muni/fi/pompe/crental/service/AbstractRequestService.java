package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dto.DTORequest;
import java.util.List;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public interface AbstractRequestService {

    /**
     * Create new request
     * @throws DataAccessException subclass, if any error has occurred
     * @param dto 
     */
    void createRequest(DTORequest dto);

    /**
     * delete passed request
     * @throws DataAccessException subclass, if any error has occurred
     * @param dto 
     */
    void deleteRequest(DTORequest dto);

    /**
     * get list of all requests
     * @throws DataAccessException subclass, if any error has occurred
     * @return list of stored DTORequest, in no such exists, empty list is returned
     */
    List<DTORequest> getAllRequests();

    /**
     * get request with passed id
     * @throws DataAccessException subclass, if any error has occurred
     * @param id
     * @return DTORequest with passed id, if no such is found, null is returned
     */
    DTORequest getRequestById(Long id);

    /**
     * get list of all unconfirmed requests
     * @throws DataAccessException subclass, if any error has occurred
     * @return list of stored DTORequest which have not been confirmed as rents, empty list returned if no such request exists
     */
    List<DTORequest> getUnconfirmedRequests();
    
    /**
     * update passed request
     * @throws DataAccessException subclass, if any error has occurred
     * @param dto 
     */
    void updateRequest(DTORequest dto);
    
}
