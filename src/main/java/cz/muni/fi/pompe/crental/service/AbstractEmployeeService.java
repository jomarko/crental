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
    void createEmployee(DTOEmployee dtoemployee);
    void deleteEmployee(DTOEmployee dtoemployee);
    void updateEmployee(DTOEmployee dtoemployee);
    List<DTOEmployee> getAllEmployees();
    DTOEmployee getEmployeeById(Long id);
}
