/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author jozef
 */
public class DAOEmployeeImpl implements DAOEmployee{
    
    private EntityManagerFactory emf;
        
    /**
     * Method set EntityManagerFactory for this class.
     * This class wouldn't work without EntityManagerFactory.
     * @param emf EntityManagerFactory to be set
     */
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
        
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
    }
    
    public void deleteEmployee(Employee employee){
        checkEntityManagerFactory();
        checkEmployeeWithId(employee);
        
        EntityManager em = emf.createEntityManager();
        
        try{
            Employee employeeToRemove = em.find(Employee.class, employee.getId());

            em.getTransaction().begin();
            em.remove(employeeToRemove);
            em.getTransaction().commit();
        }finally{
            closeEntityManager(em);
        }
    }
    
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
    }
    
    public List<Employee> getAllEmployees(){
        checkEntityManagerFactory();
        
        EntityManager em = emf.createEntityManager();
        List<Employee> resultList = new ArrayList<Employee>();
        
        try{
            TypedQuery<Employee> query = em.createNamedQuery("Employee.SelectAllEmployee", Employee.class);
            resultList = query.getResultList();
        }finally{
            closeEntityManager(em);
        }
        
        return resultList;
    }
    
    public Employee getEmployeeById(Long id){
        checkEntityManagerFactory();
        
        if(id == null) {
            throw new NullPointerException("Id was null");
        }
        
        EntityManager em = emf.createEntityManager();
        Employee result = null;
        
        try{
            TypedQuery<Employee> query = em.createNamedQuery("Employee.SelectEmployeeById", Employee.class);
            query.setParameter("id", id);
            result = query.getSingleResult();
        }finally{
            closeEntityManager(em);
        }
                
        return result;
    }
    
    private void checkEmployee(Employee e){
        if(e == null){
            throw new NullPointerException("Employee instance was null");
        }
        
        if(e.getAccessRight() == null){
            throw new IllegalArgumentException("Employe instance: " + e + " hasn't setted accessRight");
        }
        
        if(e.getName() == null || e.getName().length() == 0){
            throw new IllegalArgumentException("Employe instance: " + e + " has invalid name");
        }
        
        if(e.getPassword()== null || e.getPassword().length() == 0){
            throw new IllegalArgumentException("Employe instance: " + e + " has invalid password");
        }
    }
    
    private void checkEmployeeWithId(Employee e){
        checkEmployee(e);
        
        if(e.getId() == null){
            throw new IllegalArgumentException("Employe instance: " + e + " hasn't setted id");
        }
    }
    
    private void checkEmployeeWithoutId(Employee e){
        checkEmployee(e);
        
        if(e.getId() != null){
            throw new IllegalArgumentException("Employe instance: " + e + " has setted id");
        }
    }
    
    private void closeEntityManager(EntityManager em){
        if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                //commit fails -> rolback
            }
            em.close();
    }
    
    private void checkEntityManagerFactory() {
        if(emf == null) {
            throw new IllegalStateException("EntityManagerFactory emf was not set");
        }
    }
}
