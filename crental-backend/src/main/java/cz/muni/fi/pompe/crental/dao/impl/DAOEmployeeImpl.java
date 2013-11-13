package cz.muni.fi.pompe.crental.dao.impl;

import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class must have set EntityManagerFactory for correct working.
 * Otherwise is throws IllegalStateException by calling it's methods
 * @author jozef
 */
@Repository // for PersistenceExceptionTranslationPostProcessor to translate exceptions to DataAccessException
public class DAOEmployeeImpl implements DAOEmployee{
    
    @PersistenceContext(unitName = "pa165")
    private EntityManager em;
    
    private static final Logger logger = Logger.getLogger(DAOEmployeeImpl.class.getName());

    public DAOEmployeeImpl() {
    }
    
    @Override
    public void createEmployee(Employee employee){
        checkEmployeeWithoutId(employee);
        try{
            em.persist(employee);
        }finally{
            em.close();
        }
        
        logger.log(Level.INFO, "Created 'Employee': {0}", employee);
    }
    
    @Override
    public void deleteEmployee(Employee employee){
        checkEmployeeWithId(employee);
        
        try{
            Employee employeeToRemove = em.find(Employee.class, employee.getId());
            em.remove(employeeToRemove);
        }catch(NoResultException ex){
            logger.log(Level.SEVERE, "No such 'Employee': " + employee + " in database");
            throw new IllegalArgumentException("No such 'Employee': " + employee + " in database", ex);
        }finally{
            em.close();
        }
        logger.log(Level.INFO, "Deleted 'Employee': {0}", employee);
    }
    
    @Override
    public void updateEmployee(Employee employee){
        checkEmployeeWithId(employee);
        
        try{
            Employee employeeToUpdate = em.find(Employee.class, employee.getId());
            if(employeeToUpdate == null) {
                throw new IllegalArgumentException("No such: " + employee + " in database");
            }
            employeeToUpdate.setAccessRight(employee.getAccessRight());
            employeeToUpdate.setName(employee.getName());
            employeeToUpdate.setPassword(employee.getPassword());
        }finally{
            em.close();
        }
        logger.log(Level.INFO, "Updated 'Employee': {0}", employee);
    }
    
    @Override
    public List<Employee> getAllEmployees(){
        List<Employee> resultList = new ArrayList<>();
        
        try{
            TypedQuery<Employee> query = em.createNamedQuery("Employee.SelectAllEmployee", Employee.class);
            resultList = query.getResultList();
        }finally{
            em.close();
        }
        
        return resultList;
    }
    
    @Override
    public Employee getEmployeeById(Long id){
        if(id == null) {
            logger.log(Level.SEVERE, "Id was null");
            throw new IllegalArgumentException("Id was null");
        }
        
        Employee result = null;
        
        try{
            TypedQuery<Employee> query = em.createNamedQuery("Employee.SelectEmployeeById", Employee.class);
            query.setParameter("id", id);
            result = query.getSingleResult();
        }catch(NoResultException ex){
            logger.log(Level.WARNING, "Query for ''Employee'' with unknown id: {0}", id);
        }finally{
            em.close();
        }
                
        return result;
    }
    
    private void checkEmployee(Employee e){
        if(e == null){
            logger.log(Level.SEVERE, "Employe instance was null");
            throw new IllegalArgumentException("Employee instance was null");
        }
        
        if(e.getAccessRight() == null){
            logger.log(Level.SEVERE, "Employe instance: {0} hasn''t setted accessRight", e);
            throw new IllegalArgumentException("Employe instance: " + e + " hasn't setted accessRight");
        }
        
        if(e.getName() == null || e.getName().length() == 0){
            logger.log(Level.SEVERE, "Employe instance: {0} has invalid name", e);
            throw new IllegalArgumentException("Employe instance: " + e + " has invalid name");
        }
        
        if(e.getPassword()== null || e.getPassword().length() == 0){
            logger.log(Level.SEVERE, "Employe instance: {0} has invalid password", e);
            throw new IllegalArgumentException("Employe instance: " + e + " has invalid password");
        }
    }
    
    private void checkEmployeeWithId(Employee e){
        checkEmployee(e);
        
        if(e.getId() == null){
            logger.log(Level.SEVERE, "Employe instance: {0} hasn't setted id", e);
            throw new IllegalArgumentException("Employe instance: " + e + " hasn't setted id");
        }
    }
    
    private void checkEmployeeWithoutId(Employee e){
        checkEmployee(e);
        
        if(e.getId() != null){
            logger.log(Level.SEVERE, "Employe instance: {0} has set id", e);
            throw new IllegalArgumentException("Employe instance: " + e + " has set id");
        }
    }
}
