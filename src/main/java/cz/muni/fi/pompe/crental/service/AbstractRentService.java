/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.dto.DTORent;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jozef
 */
public interface AbstractRentService {

    /**
     * Method creates non stored rent
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtorent Non stored DTORent to be stored
     */
    void createRent(DTORent dtorent);

    /**
     * Method deletes stored rent
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtorent Stored DTORent to be deleted
     */
    void deleteRent(DTORent dtorent);

    /**
     * Method finds all stored rents
     * @throws DataAccessException subclass, if any error has occurred
     * @return List of stored DTORents, empty list is returned if no such rent exist
     */
    List<DTORent> getAllRents();

    /**
     * Method finds all rents, which start or end is between Date from and to
     * @throws DataAccessException subclass, if any error has occurred
     * @param from Start Date of interval
     * @param to End Date of interval
     * @return List of stored DTORents starting or ending in given interval, empty list is returned if no such rent exist
     */
    List<DTORent> getAllRentsIn(Date from, Date to);

    /**
     * Method finds all rents where was borrowed given car
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtocar DTOCar for witch we search rents
     * @return List of DTORents where was borrowed given car, empty list is returned if no such rent exist
     */
    List<DTORent> getAllRentsOfCar(DTOCar dtocar);

    /**
     * Method finds all rents, in which has borrowed given employee some car
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtoemp DTOEmployee for witch we search rents
     * @return List of DTORent in which has borrowed given employee some car, empty list is returned if no such rent exist
     */
    List<DTORent> getAllRentsOfEmployee(DTOEmployee dtoemp);

    /**
     * Method finds stored rent by given id
     * @throws DataAccessException subclass, if any error has occurred
     * @param id Id of searched rent
     * @return DTORent with given id, null if no such rent exists
     */
    DTORent getRentById(Long id);

    /**
     * Method updates stored rent
     * @throws DataAccessException subclass, if any error has occurred
     * @param dtorent Stored DTORent to be updated
     */
    void updateRent(DTORent dtorent);
}
