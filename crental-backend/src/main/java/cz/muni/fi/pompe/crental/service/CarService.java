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
public class CarService implements AbstractCarService {

    private DAOCar dao;

    public void setDao(DAOCar dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DTOCar> getAllCars() {
        List<DTOCar> DTOCars = new ArrayList<>();
        List<Car> entities = dao.getAllCars();
        if (entities.size() != 0) {
            DTOCars = entitiesToDTOs(entities);
        }
        return DTOCars;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DTOCar> getFreeCars(Date from, Date to) {
        List<DTOCar> DTOCars = new ArrayList<>();
        List<Car> entities = dao.getFreeCars(from, to);
        if (entities.size() != 0) {
            DTOCars = entitiesToDTOs(entities);
        }
        return DTOCars;
    }

    @Transactional(readOnly = true)
    @Override
    public DTOCar getCarById(Long id) {
        DTOCar result = null;
        Car c = dao.getCarById(id);
        if(c != null){
            result = entityToDTO(c);
        }
        
        return result;
    }

    @Transactional
    @Override
    public void createCar(DTOCar dto) {
        if (dto != null) {
            Car c = dtoToEntity(dto);
            dao.createCar(c);
            dto.setId(c.getId());
        }
    }

    @Transactional
    @Override
    public void deleteCar(DTOCar dto) {
        if (dto != null && dto.getId() != null) {
            dao.deleteCar(dto.getId());
        }
    }

    @Transactional
    @Override
    public void updateCar(DTOCar dto) {
        if (dto != null) {
            dao.updateCar(dtoToEntity(dto));
        }
    }

    protected List<DTOCar> entitiesToDTOs(List<Car> entities) {
        List<DTOCar> ret = new ArrayList<>();

        for (Car car : entities) {
            ret.add(entityToDTO(car));
        }

        return ret;
    }

    public static Car dtoToEntity(DTOCar dto) {
        Car entity = new Car();
        entity.setCarType(dto.getCarType());
        entity.setEvidencePlate(dto.getEvidencePlate());
        entity.setId(dto.getId());
        return entity;
    }

    public static DTOCar entityToDTO(Car entity) {
        DTOCar dto = new DTOCar();
        dto.setId(entity.getId());
        dto.setEvidencePlate(entity.getEvidencePlate());
        dto.setCarType(entity.getCarType());

        return dto;
    }
}
