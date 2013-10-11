package cz.muni.fi.pompe.crental;

import java.util.List;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
interface DAORequest {
    /**
     * @param Request r
     */
    public void createRequest(Request r);
    
    /**
     * @param Request r
     */
    public void updateRequest(Request r);
    
    /**
     * @param Request r
     */
    public void deleteRequest(Request r);
    
    /**
     * @return List<Request>
     */
    public List<Request> getAllRequest();

    /**
     * @param id
     * @return Request
     */
    public Request getRequestById(Long id);
}
