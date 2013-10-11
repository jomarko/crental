/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jozef
 */
public class DAOCarImplTest {
    private EntityManagerFactory emf;
    private DAOCarImpl daocar;
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CarRentalPUInMemory");
        daocar = new DAOCarImpl();
        daocar.setEntityManagerFactory(emf);
    }
    
    @After
    public void tearDown() {
        emf.close();
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
        }catch(NullPointerException ex){    }
        
        try{
            car = newCar(null, "", null);
            daocar.createCar(car);
            fail("invalid car");
        }catch(IllegalArgumentException ex){    }
        
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
        }catch(NullPointerException ex){    }
        
        try{
            car.setCarType("");
            daocar.updateCar(car);
            fail("invalid car");
        }catch(IllegalArgumentException ex){    }
        
        try{
            car = daocar.getCarById(car.getId());
            car.setId(car.getId()+1);
            daocar.updateCar(car);
            fail("wrong id of car");
        }catch(NullPointerException ex){    }
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
        }catch(NullPointerException ex){    }
        
        auto = newCar(null, "typ", "spz");
        daocar.createCar(auto);
        car = newCar(11452l, "cartype", "spz");
        try{
            daocar.deleteCar(car);
            fail("invalid car");
        }catch(IllegalArgumentException ex){    }
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
        }catch(NullPointerException ex){
            
        }
        
        try{
            daocar.getCarById(745l);
        }catch(NoResultException ex){
            
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
