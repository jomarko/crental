package cz.muni.fi.pompe.crental;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jozef
 */
public class DAORentImplTest {
    private EntityManagerFactory emf;
    private DAORentImpl daoRent;
    private DAOCarImpl daoCar;
    private DAOEmployeeImpl daoEmployee;
    private DAORequestImpl daoRequest;
    
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
        daoRent = new DAORentImpl(emf);
        daoRequest = new DAORequestImpl(emf);
        
        daoEmployee = new DAOEmployeeImpl();
        daoEmployee.setEntityManagerFactory(emf);
        
        daoCar = new DAOCarImpl();
        daoCar.setEntityManagerFactory(emf);
    }
    
    @After
    public void tearDown() {
        emf.close();
    }
    
    @Test
    public void testCreateRent() {
        Employee admin = DAOEmployeeImplTest.newEmployee(null, "Patrik Pomope", "XXXX", AccessRight.Admin);
        daoEmployee.createEmployee(admin);
        
        Employee employee = DAOEmployeeImplTest.newEmployee(null, "Just an Employee", "XXXX", AccessRight.Employee);
        daoEmployee.createEmployee(employee);
        Rent rent = getSampleRent();
        
        rent.setConfirmedBy(employee);
        
        try {
            daoRent.createRent(rent);
            fail("cannt be confirmed by employee");
        } catch (Exception e) { }
        
        rent.setConfirmedBy(admin);
        
        int before = daoRent.getAllRents().size();
        daoRent.createRent(rent);
        assertEquals(before + 1, daoRent.getAllRents().size());
    }
    
    @Test
    public void testUpdateRent() {
        Rent rent = getSampleRent();
        daoRent.createRent(rent);
        
        Employee admin = DAOEmployeeImplTest.newEmployee(null, "Admin Adminuv", "XXX", AccessRight.Admin);
        daoEmployee.createEmployee(admin);
        
        Car octavia = new Car();
        octavia.setCarType("Skoda octavia");
        octavia.setEvidencePlate("SPZ");
        daoCar.createCar(octavia);
        
        Request request = new Request();
        request.setDateFrom(new Date(113,9,15));
        request.setDateTo(new Date(113,9,16));
        request.setDescription("test");
        request.setEmployee(admin);
        daoRequest.createRequest(request);
        
        rent.setConfirmedAt(new Date(113, 9, 12));
        rent.setConfirmedBy(admin);
        rent.setRentedCar(octavia);
        rent.setRequest(request);
        daoRent.updateRent(rent);
        
        Rent updated = daoRent.getRentById(rent.getId());
        
        assertEquals(updated.getConfirmedAt(), rent.getConfirmedAt());
        assertEquals(updated.getConfirmedBy(), rent.getConfirmedBy());
        assertEquals(updated.getId(), rent.getId());
        assertEquals(updated.getRentedCar(), rent.getRentedCar());
        assertEquals(updated.getRequest().getId(), rent.getRequest().getId());
    }
    
    @Test
    public void testDeleteRent() {
        int counBefore = this.daoRent.getAllRents().size();
        Rent rent = getSampleRent();
        daoRent.createRent(rent);
        daoRent.deleteRent(rent);
        
        assertEquals(counBefore, daoRent.getAllRents().size());
    }
    
    @Test
    public void testGetAllRequest() {
        List<Rent> list = daoRent.getAllRents();
        Rent rent = getSampleRent();
        daoRent.createRent(rent);
        list.add(rent);
        List<Rent> expected = daoRent.getAllRents();
        
        assertEquals(expected.size(), list.size());
    }
    
    @Test
    public void testGetRentById() {
        Rent rent = getSampleRent();
        daoRent.createRent(rent);
        
        Rent rent2 = daoRent.getRentById(rent.getId());
        
        assertEquals(rent2.getConfirmedAt().getTime(), rent.getConfirmedAt().getTime());
        assertEquals(rent2.getConfirmedBy(), rent.getConfirmedBy());
        assertEquals(rent2.getId(), rent.getId());
        assertEquals(rent2.getRentedCar(), rent.getRentedCar());
        assertEquals(rent2.getRequest().getId(), rent.getRequest().getId());
        
        try{
            daoRent.getRentById(Long.MAX_VALUE);
            fail("rent with MAX_VALUE_ID should not exist");
        } catch (Exception ex) { }
        
        try{
            daoRent.getRentById(null);
            fail("rent with nullable id found");
        } catch (Exception ex) { }
    }
    
    public Rent getSampleRent() {
        Employee admin = DAOEmployeeImplTest.newEmployee(null, "Patrik Pomope", "XXXX", AccessRight.Admin);
        daoEmployee.createEmployee(admin);
        
        Employee employee = DAOEmployeeImplTest.newEmployee(null, "Just an Employee", "XXXX", AccessRight.Employee);
        daoEmployee.createEmployee(employee);
        
        Car car = new Car();
        car.setCarType("Fabia");
        car.setEvidencePlate("SPZka");
        daoCar.createCar(car);
        
        Request req = new Request();
        req.setDateFrom(new Date(113,9,20));
        req.setDateTo(new Date(113,9,20));
        req.setDescription("Chci fabii");
        req.setEmployee(employee);
        
        daoRequest.createRequest(req);
        
        Rent rent = new Rent();
        rent.setRequest(req);
        rent.setConfirmedAt(new Date(113,9,20));
        rent.setRentedCar(car);
        rent.setConfirmedBy(admin);
        
        return rent;
    }
}
