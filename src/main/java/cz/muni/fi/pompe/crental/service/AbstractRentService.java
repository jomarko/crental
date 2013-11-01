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

    void createRent(DTORent dtorent);

    void deleteRent(DTORent dtorent);

    List<DTORent> getAllRents();

    List<DTORent> getAllRentsIn(Date from, Date to);

    List<DTORent> getAllRentsOfCar(DTOCar dtocar);

    List<DTORent> getAllRentsOfEmployee(DTOEmployee dtoemp);

    DTORent getRentById(Long id);

    void updateRent(DTORent dtorent);
}
