package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOCar;
import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.entity.Car;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Service
@Transactional
public class CarService {
    private DAOCar dao;
    
    public void setDao(DAOCar dao) {
        this.dao = dao;
    }
    
    public List<DTOCar> getAllCars() {
        List<Car> entities = dao.getAllCars();
        return this.entitiesToDTOs(entities);
    }
    
    public List<DTOCar> getFreeCars(Date from, Date to) {
        List<Car> entities = dao.getFreeCars(from, to);
        return this.entitiesToDTOs(entities);
    }
    
    public DTOCar getCarById(Long id) {
        Car entity = this.dao.getCarById(id);
        
        return new DTOCar(entity);
    }

    public void createCar(DTOCar dto) {
        Car car = new Car(dto);
        dao.createCar(car);
    }
    
    public void deleteCar(DTOCar dto) {
        dao.deleteCar(new Car(dto));
    }
    
    public void updateCar(DTOCar dto) {
        dao.updateCar(new Car(dto));
    }
    
    protected List<DTOCar> entitiesToDTOs(List<Car> entities) {
        List<DTOCar> ret = new ArrayList<>();
        
        for (Car car : entities) {
            ret.add(new DTOCar(car));
        }
        
        return ret;
    }
}