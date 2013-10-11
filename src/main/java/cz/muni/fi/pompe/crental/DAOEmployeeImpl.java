/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

/**
 * Class must have set EntityManagerFactory for correct working.
 * Otherwise is throws IllegalStateException by calling it's methods
 * @author jozef
 */
public class DAOEmployeeImpl implements DAOEmployee{
    
    private EntityManagerFactory emf;
    private Logger logger;

    public DAOEmployeeImpl() {
        logger = Logger.getLogger(DAOEmployeeImpl.class.getName());
    }
    
    /**
     * Method set EntityManagerFactory for this class.
     * This class wouldn't work without EntityManagerFactory.
     * @param emf EntityManagerFactory to be set
     */
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
        
    @Override
    public void createEmployee(Employee employee){
        checkEntityManagerFactory();
        checkEmployeeWithoutId(employee);
        
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        }finally{
            closeEntityManager(em);
        }
        logger.log(Level.INFO, "Created 'Employee': {0}", employee);
    }
    
    @Override
    public void deleteEmployee(Employee employee){
        checkEntityManagerFactory();
        checkEmployeeWithId(employee);
        
        EntityManager em = emf.createEntityManager();
        
        try{
            Employee employeeToRemove = em.find(Employee.class, employee.getId());

            em.getTransaction().begin();
            em.remove(employeeToRemove);
            em.getTransaction().commit();
        }catch(NoResultException ex){
            logger.log(Level.SEVERE, "No such 'Employee': " + employee + " in database");
            throw ex;
        }finally{
            closeEntityManager(em);
        }
        logger.log(Level.INFO, "Deleted 'Employee': {0}", employee);
    }
    
    @Override
    public void updateEmployee(Employee employee){
        checkEntityManagerFactory();
        checkEmployeeWithId(employee);
        
        EntityManager em = emf.createEntityManager();
        
        try{
            Employee employeeToUpdate = em.find(Employee.class, employee.getId());

            em.getTransaction().begin();
            employeeToUpdate.setAccessRight(employee.getAccessRight());
            employeeToUpdate.setName(employee.getName());
            employeeToUpdate.setPassword(employee.getPassword());
            em.getTransaction().commit();
        }finally{
            closeEntityManager(em);
        }
        logger.log(Level.INFO, "Updated 'Employee': {0}", employee);
    }
    
    @Override
    public List<Employee> getAllEmployees(){
        checkEntityManagerFactory();
        
        EntityManager em = emf.createEntityManager();
        List<Employee> resultList = new ArrayList<>();
        
        try{
            TypedQuery<Employee> query = em.createNamedQuery("Employee.SelectAllEmployee", Employee.class);
            resultList = query.getResultList();
        }finally{
            closeEntityManager(em);
        }
        
        return resultList;
    }
    
    @Override
    public Employee getEmployeeById(Long id){
        checkEntityManagerFactory();
        
        if(id == null) {
            logger.log(Level.SEVERE, "Id was null");
            throw new NullPointerException("Id was null");
        }
        
        EntityManager em = emf.createEntityManager();
        Employee result = null;
        
        try{
            TypedQuery<Employee> query = em.createNamedQuery("Employee.SelectEmployeeById", Employee.class);
            query.setParameter("id", id);
            result = query.getSingleResult();
        }catch(NoResultException ex){
            logger.log(Level.WARNING, "Query for ''Employee'' with unknown id: {0}", id);
        }finally{
            closeEntityManager(em);
        }
                
        return result;
    }
    
    private void checkEmployee(Employee e){
        if(e == null){
            logger.log(Level.SEVERE, "Employe instance was null");
            throw new NullPointerException("Employee instance was null");
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
    
    private void closeEntityManager(EntityManager em){
        if(em.getTransaction().isActive()){
            logger.log(Level.SEVERE, "Executed rollback, because of active Transaction");
            em.getTransaction().rollback();
        }
        em.close();
    }
    
    private void checkEntityManagerFactory() {
        if(emf == null) {
            logger.log(Level.SEVERE, "Unset'EntityManagerFactory' in DAOEmployeeImpl");
            throw new IllegalStateException("EntityManagerFactory emf was not set");
        }
    }
}
