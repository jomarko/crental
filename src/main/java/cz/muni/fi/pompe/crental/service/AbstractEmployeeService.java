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
     * Method
     * @param dtoemployee 
     */
    void createEmployee(DTOEmployee dtoemployee);
    
    /**
     * 
     * @param dtoemployee 
     */
    void deleteEmployee(DTOEmployee dtoemployee);
    
    /**
     * 
     * @param dtoemployee 
     */
    void updateEmployee(DTOEmployee dtoemployee);
    
    /**
     * 
     * @return 
     */
    List<DTOEmployee> getAllEmployees();
    
    /**
     * 
     * @param id
     * @return 
     */
    DTOEmployee getEmployeeById(Long id);
}
