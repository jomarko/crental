/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.testsamples;

import cz.muni.fi.pompe.crental.AccessRight;
import cz.muni.fi.pompe.crental.DAOEmployeeImpl;
import cz.muni.fi.pompe.crental.DAORequestImpl;
import cz.muni.fi.pompe.crental.Employee;
import cz.muni.fi.pompe.crental.Request;
import java.util.Date;
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

            System.out.println(daoemployee.getAllEmployees());
            
            ///  Request
            DAORequestImpl daoR = new DAORequestImpl(emf);
            
            Request r = new Request();
            r.setEmployee(e);
            r.setDateFrom(new Date(2013, 10, 25));
            r.setDateTo(new Date(2013, 10, 26));
            r.setDescription("Chci oktavku");
            System.out.println(r);
            
            daoR.createRequest(r);
            System.out.println(daoR.getAllRequest());
            
            
            
            emf.close();
        }
}
