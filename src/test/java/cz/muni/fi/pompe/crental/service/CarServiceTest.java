package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOCar;
import cz.muni.fi.pompe.crental.entity.Car;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class CarServiceTest extends AbstractIntegrationTest {
//    @Mock
//    private DAOCar DAOcar;
//    @InjectMocks
    @Autowired
    private CarService carService;
    
    @Test
    public void testCreateNewCar() {
        /*this.carService.createNewCar("BMW 7", "xxx");
        List<Car> cars = this.carService.getAllCars();
        assertEquals(1, cars.size());
        System.out.println("created cars: " + cars.toString());*/
    }
}
