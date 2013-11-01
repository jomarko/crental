package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dto.DTOCar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public interface AbstractCarService {

    /**
     * Create new row in DAO layer
     * @param dto 
     */
    void createCar(DTOCar dto);

    /**
     * Delete row from DAO layer
     * @param dto 
     */
    void deleteCar(DTOCar dto);

    /**
     * Get list of all cars from DAO layer
     * @return 
     */
    List<DTOCar> getAllCars();

    /**
     * Get car with passed id
     * @param id
     * @return 
     */
    DTOCar getCarById(Long id);

    /**
     * Get list of cars available between passed dates
     * @param from
     * @param to
     * @return 
     */
    List<DTOCar> getFreeCars(Date from, Date to);

    /**
     * Update passed car
     * @param dto 
     */
    void updateCar(DTOCar dto);
    
}
