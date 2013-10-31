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

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest extends AbstractIntegrationTest {
    @Mock
    private DAOCar MockDAOcar;
    
    @InjectMocks
    @Autowired
    private CarService carService;
    
    private DTOCar testCarDTO;
    private Car testCar;
    
    @Before
    public void init() {
        testCarDTO = new DTOCar();
        testCarDTO.setCarType("BMW X5");
        testCarDTO.setEvidencePlate("123456");
        testCar = new Car();
        testCar.setCarType("BMW X5");
        testCar.setEvidencePlate("123456");
        
        List<Car> allCars = new ArrayList<>();
        allCars.add(testCar);
        
        doReturn(allCars).when(MockDAOcar).getAllCars();
        doThrow(new NullPointerException()).when(MockDAOcar).createCar(null);
    }
    
    @Test
    public void testCreateNewCar() {
        carService.createCar(testCarDTO);
        List<DTOCar> carDTOs = this.carService.getAllCars();
        assertEquals(1, carDTOs.size());      
    }
    
    @Test
    public void testCreateNullCar() {
        try{
            carService.createCar(null);
            fail("null car");
        } catch(NullPointerException ex){    }
    }
}
