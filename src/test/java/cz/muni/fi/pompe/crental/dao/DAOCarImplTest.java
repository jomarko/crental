package cz.muni.fi.pompe.crental.dao;

import cz.muni.fi.pompe.crental.dao.impl.DAOCarImpl;
import cz.muni.fi.pompe.crental.entity.Car;
import javax.persistence.NoResultException;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.junit.Test;
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
public class DAOCarImplTest {
    @Autowired
    private DAOCar daocar;
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreateCar() {
        Car car = newCar(null, "cartype", "spz");
        daocar.createCar(car);
        
        Car result = daocar.getCarById(car.getId());
        assertEquals(car.getId(), result.getId());
        assertEquals(car.getCarType(), result.getCarType());
        assertEquals(car.getEvidencePlate(), result.getEvidencePlate());
               
        try{
            daocar.createCar(null);
            fail("null car");
        }catch(DataAccessException ex){    }
        
        try{
            car = newCar(null, "", null);
            daocar.createCar(car);
            fail("invalid car");
        }catch(DataAccessException ex){    }
        
    }
    
    @Test
    public void testUpdateCar() {
        Car car = newCar(null, "cartype", "spz");
        daocar.createCar(car);
        
        car.setEvidencePlate("spzka");
        daocar.updateCar(car);
        
        Car result = daocar.getCarById(car.getId());
        assertEquals(car.getId(), result.getId());
        assertEquals(car.getCarType(), result.getCarType());
        assertEquals(car.getEvidencePlate(), result.getEvidencePlate());
               
        try{
            daocar.updateCar(null);
            fail("null car");
        }catch(DataAccessException ex){    }
        
        try{
            car.setCarType("");
            daocar.updateCar(car);
            fail("invalid car");
        }catch(DataAccessException ex){    }
        
        try{
            car = daocar.getCarById(car.getId());
            car.setId(car.getId()+1);
            daocar.updateCar(car);
            fail("wrong id of car");
        }catch(DataAccessException ex){    }
    }
    
    @Test
    public void testDeleteCar() {
        Car car = newCar(null, "cartype", "spz");
        Car auto = newCar(null, "autotype", "autospz");
        daocar.createCar(car);
        daocar.createCar(auto);
        
        assertEquals(daocar.getAllCars().size(), 2);
        
        daocar.deleteCar(car);
        
        assertEquals(daocar.getAllCars().size(), 1);
        
        assertEquals(daocar.getAllCars().get(0), auto);

        daocar.deleteCar(auto);
        
        assertEquals(daocar.getAllCars().size(), 0);
        
        try{
            daocar.deleteCar(null);
            fail("null car");
        }catch(DataAccessException ex){    }
        
        auto = newCar(null, "typ", "spz");
        daocar.createCar(auto);
        car = newCar(11452l, "cartype", "spz");
        try{
            daocar.deleteCar(car);
            fail("invalid car");
        }catch(DataAccessException ex){    }
    }
    
    @Test
    public void testGetAllCars() {
        Car car = newCar(null, "cartype", "spz");
        daocar.createCar(car);
        Car auto = newCar(null, "autotype", "spz");
        daocar.createCar(auto);
        
        assertEquals(daocar.getAllCars().size(), 2);
        assertEquals(daocar.getAllCars().get(0), car);
        assertEquals(daocar.getAllCars().get(1), auto);
               
        daocar.deleteCar(car);
        daocar.deleteCar(auto);
        
        assertEquals(daocar.getAllCars().size(), 0);
    }
    
    @Test
    public void testGetCarById() {
        Car car = newCar(null, "cartype", "spz");
        daocar.createCar(car);
        Car auto = newCar(null, "autotype", "spz");
        daocar.createCar(auto);
        
        assertEquals(daocar.getCarById(car.getId()), car);
        assertEquals(daocar.getCarById(auto.getId()), auto);
        
        try{
            daocar.getCarById(null);
        }catch(DataAccessException ex){
            
        }
        
        try{
            daocar.getCarById(745l);
        }catch(DataAccessException ex){
            
        }
    }
    
    private Car newCar(Long id, String carType, String evidencePlate){
        Car c = new Car();
        c.setId(id);
        c.setCarType(carType);
        c.setEvidencePlate(evidencePlate);
        
        return c;
    }
}