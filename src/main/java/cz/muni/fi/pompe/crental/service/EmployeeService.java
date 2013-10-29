/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
@Service
public class EmployeeService {
    
    private DAOEmployee daoemployee;

    public void setDaoemployee(DAOEmployee daoemployee) {
        this.daoemployee = daoemployee;
    }
    
    @Transactional
    void createEmployee(DTOEmployee dtoemployee){
        daoemployee.createEmployee(new Employee(dtoemployee));
    }
    
    @Transactional
    void deleteEmployee(DTOEmployee dtoemployee){
        daoemployee.deleteEmployee(new Employee(dtoemployee));
    }
    
    @Transactional
    void updateEmployee(DTOEmployee dtoemployee){
        daoemployee.updateEmployee(new Employee(dtoemployee));
    }
    
    @Transactional(readOnly = true)
    List<DTOEmployee> getAllEmployees(){
        List<DTOEmployee> result = new ArrayList<>();
        
        for(Employee e : daoemployee.getAllEmployees()){
            result.add(new DTOEmployee(e));
        }
        
        return result;
    }
    
    @Transactional(readOnly = true)
    DTOEmployee getEmployeeById(Long id){
        DTOEmployee result = null;
        Employee e = daoemployee.getEmployeeById(id);
        if(e != null){
            result = new DTOEmployee(e);
        }
        
        return result;
    }
}
