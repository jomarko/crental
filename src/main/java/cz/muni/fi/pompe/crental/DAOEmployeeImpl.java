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
public class DAOEmployeeImpl {
    
    private EntityManagerFactory emf;
        
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
        
    /**
     * Method stores employee to database.
     * @throws NullPointerException if employee is null
     * @throws IllegalArgumentException if employee has id, hasn't name, password or accessRight set
     * @param employee Instance of Employee to be stored. Instance can't be stored before.
     */
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
    
    /**
     * Method delete employee from database.
     * @throws NullPointerException if employee is null
     * @throws IllegalArgumentException if employee hasn't id, name, password or accessRight set
     * @param employee Instance of Employee to be deleted. Instance must be stored before method is called.
     */
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
    
    /**
     * Method update record in database, which has the same id as employee.
     * @throws NullPointerException if employee is null
     * @throws IllegalArgumentException if employee hasn't id, name, password or accessRight set
     * @param employee Instance of Employee to be updated. Instance must be stored before method is called.
     */
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
    
    /**
     * Method returns List of all employees in database
     * @return List of all employees in database. If no employees is stored, empty list is returned;
     */
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
    
    /**
     * Method returns employee with id in database
     * @throws NullPointerException If id is null
     * @throws NoResultException If id isn't in database
     * @param id Id of wanted employee
     * @return Instance of Employee with wanted id
     */
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
