package cz.muni.fi.pompe.crental.dao;

import cz.muni.fi.pompe.crental.exception.CarRentalException;
import cz.muni.fi.pompe.crental.entity.Employee;
import cz.muni.fi.pompe.crental.entity.Car;
import cz.muni.fi.pompe.crental.entity.AccessRight;
import cz.muni.fi.pompe.crental.entity.Rent;
import cz.muni.fi.pompe.crental.entity.Request;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class DAORentImplTest {

    @Autowired
    private DAORent daoRent;
    @Autowired
    private DAOCar daoCar;
    @Autowired
    private DAOEmployee daoEmployee;
    @Autowired
    private DAORequest daoRequest;

    @Test
    public void testCreateRent() throws CarRentalException {
        Employee admin = DAOEmployeeImplTest.newEmployee(null, "Patrik Pomope", "XXXX", AccessRight.Admin);
        daoEmployee.createEmployee(admin);

        Employee employee = DAOEmployeeImplTest.newEmployee(null, "Just an Employee", "XXXX", AccessRight.Employee);
        daoEmployee.createEmployee(employee);
        Rent rent = getSampleRent();

        //test of creating valid instance of Rent
        int before = daoRent.getAllRents().size();
        daoRent.createRent(rent);
        assertEquals(before + 1, daoRent.getAllRents().size());

        //tests of creating invalid instance of Rent
        rent.setConfirmedBy(employee);
        try {
            daoRent.createRent(rent);
            fail("Rent cannot be confirmed by employee");
        } catch (DataAccessException e) {
        }

        rent.setId(5l);
        rent.setConfirmedBy(admin);
        try {
            daoRent.createRent(rent);
            fail("cannot persist rent with setted id");
        } catch (DataAccessException e) {
        }

        rent.setId(null);
        rent.setRentedCar(null);
        rent.setConfirmedAt(null);
        try {
            daoRent.createRent(rent);
            fail("cannot persist rent with wrong attributes");
        } catch (DataAccessException e) {
        }

        try {
            daoRent.createRent(null);
            fail("cannot persist when instance of Rent is null");
        } catch (DataAccessException e) {
        }

    }

    @Test
    public void testUpdateRent() throws CarRentalException {
        Rent rent = getSampleRent();
        daoRent.createRent(rent);

        Employee admin = DAOEmployeeImplTest.newEmployee(null, "Admin Adminuv", "XXX", AccessRight.Admin);
        daoEmployee.createEmployee(admin);

        Car octavia = new Car();
        octavia.setCarType("Skoda octavia");
        octavia.setEvidencePlate("SPZ");
        daoCar.createCar(octavia);

        Request request = new Request();
        request.setDateFrom(new Date(113, 9, 15));
        request.setDateTo(new Date(113, 9, 16));
        request.setDescription("test");
        request.setEmployee(admin);
        daoRequest.createRequest(request);

        rent.setConfirmedAt(new Date(113, 9, 12));
        rent.setConfirmedBy(admin);
        rent.setRentedCar(octavia);
        rent.setRequest(request);
        daoRent.updateRent(rent);

        Rent updated = daoRent.getRentById(rent.getId());

        assertDeepEquals(rent, updated);
    }

    @Test
    public void testDeleteRent() throws CarRentalException {
        int countBefore = this.daoRent.getAllRents().size();
        Rent rent = getSampleRent();

        //test of deleting valid instance of Rent
        daoRent.createRent(rent);
        daoRent.deleteRent(rent.getId());
        assertEquals(countBefore, daoRent.getAllRents().size());

        //test of deleting invalid instance of Rent
        rent.setId(null);
        daoRent.createRent(rent);
        
        try {
            daoRent.deleteRent(null);
            fail("cannot delete when instance of Rent is null");
        } catch (DataAccessException e) {
        }
        
        try {
            daoRent.deleteRent(rent.getId() + 1);
            fail("cannot delete Rent when given id is not in the database");
        } catch (DataAccessException e) {
        }
        
    }

    @Test
    public void testGetAllRequest() throws CarRentalException {
        List<Rent> list = daoRent.getAllRents();
        Rent rent = getSampleRent();
        daoRent.createRent(rent);
        list.add(rent);
        List<Rent> expected = daoRent.getAllRents();

        assertEquals(expected.size(), list.size());
    }

    @Test
    public void testGetRentById() throws CarRentalException {
        Rent rent = getSampleRent();
        daoRent.createRent(rent);

        Rent rent2 = daoRent.getRentById(rent.getId());

        assertDeepEquals(rent, rent2);

        try {
            daoRent.getRentById(Long.MAX_VALUE);
            fail("rent with MAX_VALUE_ID should not exist");
        } catch (DataAccessException ex) {
        }

        try {
            daoRent.getRentById(null);
            fail("rent with nullable id found");
        } catch (DataAccessException ex) {
        }
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
        req.setDateFrom(new Date(113, 9, 20));
        req.setDateTo(new Date(113, 9, 20));
        req.setDescription("Chci fabii");
        req.setEmployee(employee);

        daoRequest.createRequest(req);

        Rent rent = new Rent();
        rent.setRequest(req);
        rent.setConfirmedAt(new Date(113, 9, 20));
        rent.setRentedCar(car);
        rent.setConfirmedBy(admin);

        return rent;
    }

    static void assertDeepEquals(Rent expected, Rent actual) {
        assertEquals(expected.getConfirmedAt(), actual.getConfirmedAt());
        assertEquals(expected.getConfirmedBy(), actual.getConfirmedBy());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getRentedCar(), actual.getRentedCar());
        assertEquals(expected.getRequest().getId(), actual.getRequest().getId());
    }
}

