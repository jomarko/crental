package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dto.AccessRight;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.entity.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
@Service
public class EmployeeService extends CrentalService implements AbstractEmployeeService{
    
    private DAOEmployee daoemployee;

    public DAOEmployee getDaoemployee() {
        return daoemployee;
    }

    public void setDaoemployee(DAOEmployee daoemployee) {
        this.daoemployee = daoemployee;
    }
    
    @Override
    @Transactional
    public void createEmployee(DTOEmployee dtoemployee){
        checkAdmin();
        
        if(dtoemployee != null){
            Employee e = dtoToEntity(dtoemployee);
            daoemployee.createEmployee(e);
            dtoemployee.setId(e.getId());
        }
    }
    
    @Override
    @Transactional
    public void deleteEmployee(DTOEmployee dtoemployee){
        checkAdmin();
        
        if(dtoemployee != null){
            daoemployee.deleteEmployee(dtoToEntity(dtoemployee));
        }
    }
    
    @Override
    @Transactional
    public void updateEmployee(DTOEmployee dtoemployee){
        if(dtoemployee != null){
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.checkPermission("employee:update:" + dtoemployee.getId());
            Employee e = dtoToEntity(dtoemployee);
            daoemployee.updateEmployee(dtoToEntity(dtoemployee));
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DTOEmployee> getAllEmployees(){
        checkAdmin();
        List<DTOEmployee> result = new ArrayList<>();
        
        for(Employee e : daoemployee.getAllEmployees()){
            result.add(entityToDto(e));
        }
        
        return result;
    }
    
    @Override
    @Transactional(readOnly = true)
    public DTOEmployee getEmployeeById(Long id){       
        checkPermission("employee:get:" + id);
        DTOEmployee result = null;
        Employee e = daoemployee.getEmployeeById(id);
        if(e != null){
            result = entityToDto(e);
        }
        
        return result;
    }
    
    @Override
    public DTOEmployee getEmployeeByName(String name) {
        checkAdmin();
        DTOEmployee result = null;
        Employee e = daoemployee.getEmployeeByName(name);
        
        if (e != null) {
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

    @Override
    public HashMap<Long, String> getAdminsNamesMap() {
        checkAuthentication();
        HashMap<Long, String> adminMap = new HashMap<>();
        
        for (Employee e :daoemployee.getAllEmployees()) {
            if (e.getAccessRight() == AccessRight.Admin) {
                adminMap.put(e.getId(), e.getName());
            }
        }
        
        return adminMap;
    }
}
