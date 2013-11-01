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
     * 
     * @param dtorent 
     */
    void createRent(DTORent dtorent);

    /**
     * 
     * @param dtorent 
     */
    void deleteRent(DTORent dtorent);

    /**
     * 
     * @return 
     */
    List<DTORent> getAllRents();

    /**
     * 
     * @param from
     * @param to
     * @return 
     */
    List<DTORent> getAllRentsIn(Date from, Date to);

    /**
     * 
     * @param dtocar
     * @return 
     */
    List<DTORent> getAllRentsOfCar(DTOCar dtocar);

    /**
     * 
     * @param dtoemp
     * @return 
     */
    List<DTORent> getAllRentsOfEmployee(DTOEmployee dtoemp);

    /**
     * 
     * @param id
     * @return 
     */
    DTORent getRentById(Long id);

    /**
     * 
     * @param dtorent 
     */
    void updateRent(DTORent dtorent);
}
