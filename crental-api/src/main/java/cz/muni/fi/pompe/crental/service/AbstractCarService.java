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
     * @throws DataAccessException subclass, if any error has occurred
     * @param dto 
     */
    void createCar(DTOCar dto);

    /**
     * Delete row from DAO layer
     * @throws DataAccessException subclass, if any error has occurred
     * @param dto 
     */
    void deleteCar(DTOCar dto);

    /**
     * Get list of all cars from DAO layer
     * @throws DataAccessException subclass, if any error has occurred
     * @return List of stored DTOCars, if no one exists, empty list is returned
     */
    List<DTOCar> getAllCars();

    /**
     * Get car with passed id
     * @throws DataAccessException subclass, if any error has occurred
     * @param id
     * @return DTOCar with passed id, if no such is found, null is returned
     */
    DTOCar getCarById(Long id);

    /**
     * Get list of cars available between passed dates
     * @throws DataAccessException subclass, if any error has occurred
     * @param from
     * @param to
     * @return List of DTOCars, which are assigned to any rents between passed dates
     */
    List<DTOCar> getFreeCars(Date from, Date to);

    /**
     * Update passed car
     * @throws DataAccessException subclass, if any error has occurred
     * @param dto 
     */
    void updateCar(DTOCar dto);
    
}
