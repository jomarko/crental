/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import java.util.List;

/**
 *
 * @author jozef
 */
public interface DAOEmployee {
    /**
    * Method stores employee to database.
    * @throws NullPointerException if employee is null
    * @throws IllegalArgumentException if employee has id, hasn't name, password or accessRight set
    * @param employee Instance of Employee to be stored. Instance can't be stored before.
    */
    void createEmployee(Employee employee);
    
    /**
     * Method delete employee from database.
     * @throws NullPointerException if employee is null
     * @throws IllegalArgumentException if employee hasn't id, name, password or accessRight set, or
     *         employee isn't in database
     * @param employee Instance of Employee to be deleted. Instance must be stored before method is called.
     */
    void deleteEmployee(Employee employee);
    
    /**
     * Method update record in database, which has the same id as employee.
     * @throws NullPointerException if employee is null, or employee's id isn't in database
     * @throws IllegalArgumentException if employee hasn't id, name, password or accessRight set, 
     *         or employee with such id isn't in database
     * @param employee Instance of Employee to be updated. Instance must be stored before method is called.
     */
    void updateEmployee(Employee employee);
    
    /**
     * Method returns List of all employees in database
     * @return List of all employees in database. If no employees is stored, empty list is returned;
     */
    List<Employee> getAllEmployees();
    
    /**
     * Method returns employee with id in database
     * @throws NullPointerException If id is null
     * @param id Id of wanted employee
     * @return Instance of Employee with wanted id, if such 'Employee' doesn't exist in database null is returned
     */
    Employee getEmployeeById(Long id);
}
