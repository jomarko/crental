package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOCar;
import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.entity.Car;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest extends AbstractIntegrationTest {

    @Mock
    private DAOCar MockDAOCar;
    @InjectMocks
    @Autowired
    private CarService carService;
    private DTOCar testCarDTO;
    private Car testCar;

    @Before
    public void setUp() {
        testCarDTO = new DTOCar();
        testCarDTO.setCarType("BMW X5");
        testCarDTO.setEvidencePlate("123456");
        testCar = new Car();
        testCar.setCarType("BMW X5");
        testCar.setEvidencePlate("123456");
    }

    @Test
    public void testCreateNewCar() {

        // test of persisting valid instance of Car
        carService.createCar(testCarDTO);
        verify(MockDAOCar, times(1)).createCar(testCar);     //check that method createCar() was invoked one time
        assertEquals(1, carService.getAllCars().size());

        //tests of persisting invalid instance of Car
        try {
            carService.createCar(null);
            fail("cannot persist null instance of car");
        } catch (NullPointerException ex) {
            //ok
        }
    }

    /*@Test
    public void testDeleteCar() {
        
        carService.createCar(testCarDTO);
        carService.deleteCar(testCarDTO);
        verify(MockDAOCar, times(1)).deleteCar(testCar);
        
        assertEquals(0, carService.getAllCars().size());
        
        try {
            carService.deleteCar(null);
            fail("cannot delete null instance of car");
        } catch (NullPointerException ex) {
            
        }
        
        try {
            carService.deleteCar(testCarDTO);
            fail("cannot delete car that is not in database");
        } catch (Exception ex) {
            
        }
        
    }*/
}
