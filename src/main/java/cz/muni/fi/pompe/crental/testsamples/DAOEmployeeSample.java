/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.testsamples;

import cz.muni.fi.pompe.crental.AccessRight;
import cz.muni.fi.pompe.crental.DAOEmployeeImpl;
import cz.muni.fi.pompe.crental.Employee;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class for testing code of DAOEmployeeImpl
 * @author jozef
 */
public class DAOEmployeeSample {
        
        public void run() {
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
            DAOEmployeeImpl daoemployee = new DAOEmployeeImpl();
            daoemployee.setEntityManagerFactory(emf);

            Employee e = new Employee();
            e.setAccessRight(AccessRight.Employee);
            e.setName("Anton Takac");
            e.setPassword("XXX");

            daoemployee.createEmployee(e);

            e = new Employee();
            e.setAccessRight(AccessRight.Employee);
            e.setName("Antonia Takacova");
            e.setPassword("ZZZ");

            daoemployee.createEmployee(e);

            System.out.println(daoemployee.getAllEmployees());
            System.out.println(daoemployee.getEmployeeById(e.getId()));

            e.setName("Tonka");
            daoemployee.updateEmployee(e);        

            System.out.println(daoemployee.getEmployeeById(e.getId()));

            daoemployee.deleteEmployee(e);
            System.out.println(daoemployee.getAllEmployees());
            
            emf.close();
        }
}
