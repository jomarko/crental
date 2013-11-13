package cz.muni.fi.pompe.crental.dao;

import cz.muni.fi.pompe.crental.entity.Request;
import java.util.List;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public interface DAORequest {
    /**
     * Create record of Request in persitence unit
     * @param request
     */
    public void createRequest(Request r);
    
    /**
     * Update record of Request allready stored in persitence unit
     * @param request
     */
    public void updateRequest(Request r);
    
    /**
     * Delete record of Request from persitence unit
     * @param request
     */
    public void deleteRequest(Request r);
    
    /**
     * Get all records of Request in persistance unit
     * @return
     */
    public List<Request> getAllRequests();

    /**
     * Get record of Request with specific id
     * @param id
     * @return
     */
    public Request getRequestById(Long id);

    /**
     * Get list of request which haven't been confirmed as a rent yet
     * @return 
     */
    public List<Request> getUnconfirmedRequests();
}
