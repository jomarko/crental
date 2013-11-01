/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import java.util.List;

/**
 *
 * @author jozef
 */
public interface AbstractEmployeeService {
    /**
     * Method stores non stored employee
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtoemployee DTOEmployee to be stored
     */
    void createEmployee(DTOEmployee dtoemployee);
    
    /**
     * Method deletes stored employee
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtoemployee Stored DTOEmployee to be deleted
     */
    void deleteEmployee(DTOEmployee dtoemployee);
    
    /**
     * Method updates stored employee
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtoemployee Stored DTOEmployee to be updated
     */
    void updateEmployee(DTOEmployee dtoemployee);
    
    /**
     * Method finds all stored employees
     * @throws DataAccessException subclass, if any error has occurred
     * @return List of all stored DTOEmployees, if no such employee exist, empty list is returned
     */
    List<DTOEmployee> getAllEmployees();
    
    /**
     * Method finds DTOEmployee
     * @throws DataAccessException subclass, if any error has occurred
     * @param id Id of searched employee
     * @return DTOEmployee with given id, null if no such employee exists
     */
    DTOEmployee getEmployeeById(Long id);
}
