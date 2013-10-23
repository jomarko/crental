package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOCar;
import cz.muni.fi.pompe.crental.entity.Car;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Service
public class CarService {
    private DAOCar dao;

    public CarService(DAOCar dao) {
        this.dao = dao;
    }

    @Transactional
    public void createNewCar(String carType, String evidencePlate) {
        Car car = new Car();
        car.setCarType(carType);
        car.setEvidencePlate(evidencePlate);
        dao.createCar(car);
    }
    
    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return dao.getAllCars();
    }
}
