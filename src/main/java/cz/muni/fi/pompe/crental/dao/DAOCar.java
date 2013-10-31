package cz.muni.fi.pompe.crental.dao;

import cz.muni.fi.pompe.crental.entity.Car;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jozef
 */
public interface DAOCar {
    /**
     * Create instance of car in srorage
     * @param car 
     */
    void createCar(Car car);
    
    /**
     * Delete instance of car in storage
     * @param car
     */
    void deleteCar(Car car);
    
    /**
     * Update instance of car in storage
     * @param car
     */
    void updateCar(Car car);
    
    /**
     * Get list of cars from storage
     * @return
     */
    List<Car> getAllCars();
    
    /**
     * Get instance of car with passed id
     * @param id
     * @return
     */
    Car getCarById(Long id);

    /**
     * Get list of available cars in passed date
     * @param from
     * @param to
     * @return 
     */
    List<Car> getFreeCars(Date from, Date to);
}
