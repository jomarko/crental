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
        if(dtoemployee != null){
            daoemployee.createEmployee(dtoToEntity(dtoemployee));
        }
    }
    
    @Transactional
    void deleteEmployee(DTOEmployee dtoemployee){
        if(dtoemployee != null){
            daoemployee.deleteEmployee(dtoToEntity(dtoemployee));
        }
    }
    
    @Transactional
    void updateEmployee(DTOEmployee dtoemployee){
        if(dtoemployee != null){
            daoemployee.updateEmployee(dtoToEntity(dtoemployee));
        }
    }
    
    @Transactional(readOnly = true)
    List<DTOEmployee> getAllEmployees(){
        List<DTOEmployee> result = new ArrayList<>();
        
        for(Employee e : daoemployee.getAllEmployees()){
            result.add(entityToDto(e));
        }
        
        return result;
    }
    
    @Transactional(readOnly = true)
    DTOEmployee getEmployeeById(Long id){
        DTOEmployee result = null;
        Employee e = daoemployee.getEmployeeById(id);
        if(e != null){
            result = entityToDto(e);
        }
        
        return result;
    }
    
    public static DTOEmployee entityToDto(Employee employee) {
        DTOEmployee dto = new DTOEmployee();
        
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPassword(employee.getPassword());
        dto.setAccessRight(employee.getAccessRight());
        
        return dto;
    }
    
    public static Employee dtoToEntity(DTOEmployee dtoemployee) {
        Employee e = new Employee();
        
        e.setId(dtoemployee.getId());
        e.setName(dtoemployee.getName());
        e.setPassword(dtoemployee.getPassword());
        e.setAccessRight(dtoemployee.getAccessRight());
        
        return e;
    }
}
