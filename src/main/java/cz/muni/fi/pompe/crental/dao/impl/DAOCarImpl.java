package cz.muni.fi.pompe.crental.dao.impl;

import cz.muni.fi.pompe.crental.entity.Car;
import cz.muni.fi.pompe.crental.dao.DAOCar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Roman Stehlik
 */
@Repository // for PersistenceExceptionTranslationPostProcessor to translate exceptions to DataAccessException
public class DAOCarImpl implements DAOCar {

    @PersistenceContext(unitName = "CarRentalPUInMemory")
    private EntityManager em;
    private static final Logger LOG = Logger.getLogger(DAOCarImpl.class.getName());

    public DAOCarImpl() {
    }

    @Override
    public void createCar(Car car) {
        checkCarWithoutId(car);

        try {
            em.persist(car);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to persist car {0} into the database", car);
            throw new IllegalArgumentException("Error when trying to persist car " + car + " into the database", ex);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Car {0} was persisted into the database", car);
    }

    @Override
    public void deleteCar(Long id) {
        if (id == null) {
            LOG.log(Level.SEVERE, "Cannot delete car because attribute id is null");
            throw new IllegalArgumentException("Cannot delete car because attribute id is null");
        }

        try {
            Car newCar = em.find(Car.class, id);
            if (newCar == null) {
                LOG.log(Level.SEVERE, "Car with id {0} does not exist", id);
                throw new IllegalArgumentException("Car with id " + id + " does not exist");
            }
            em.remove(newCar);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to remove car with id {0} from the database", id);
            throw new IllegalArgumentException("Error when trying to remove car with id " + id + " from the database", ex);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Car with id {0} was deleted from the database", id);
    }

    @Override
    public void updateCar(Car car) {
        checkCarWithId(car);

        try {
            Car newCar = em.find(Car.class, car.getId());
            if (newCar == null) {
                LOG.log(Level.SEVERE, "Car {0} does not exist", car);
                throw new IllegalArgumentException("Car " + car + " does not exist");
            }
            newCar.setCarType(car.getCarType());
            newCar.setEvidencePlate(car.getEvidencePlate());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to update car {0}", car);
            throw new IllegalArgumentException("Error when trying to update car " + car, ex);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Car {0} was updated", car);
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> resultList = new ArrayList<>();
        try {
            Query q = em.createNamedQuery("Car.SelectAllCars", Car.class);
            resultList = q.getResultList();
            LOG.log(Level.INFO, "All cars were retrieved");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to retrieve all rents from the database");
            throw new IllegalArgumentException("Error when trying to retrieve all rents from the database", ex);
        } finally {
            em.close();
        }
        return resultList;
    }

    @Override
    public Car getCarById(Long id) {
        if (id == null) {
            LOG.log(Level.SEVERE, "Cannot retrieve car because attribute id is null");
            throw new IllegalArgumentException("Cannot retrieve car because attribute id is null");
        }
        Car result = null;
        try {
            Query q = em.createNamedQuery("Car.SelectCarById", Car.class);
            q.setParameter("id", id);
            result = (Car) q.getSingleResult();
            LOG.log(Level.INFO, "Car with id {0} was retrieved", id);
        } catch (NoResultException ex) {
            LOG.log(Level.SEVERE, "There is no car with id {0} in the database");
            throw new IllegalArgumentException("There is no car with id " + id + " in the database", ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error while retrieving car with id {0} from the database", id);
            throw new IllegalArgumentException("Error while retrieving car with id " + id + " from the database", ex);
        } finally {
            em.close();
        }
        return result;
    }

    @Override
    public List<Car> getFreeCars(Date from, Date to) {
        List<Car> result = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Car c WHERE c NOT IN (SELECT r.rentedCar FROM Rent r JOIN r.request rq WHERE rq.dateFrom <= :from AND rq.dateTo >= :to)", Car.class);
            q.setParameter("from", from);
            q.setParameter("to", to);
            result = q.getResultList();
            LOG.log(Level.INFO, "All free cars were retrieved");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to retrieve all free cars from the database");
            throw new IllegalArgumentException("Error when trying to get all free cars from the database", ex);
        } finally {
            em.close();
        }
        return result;
    }

    private void validate(Car c) {
        if (c == null) {
            LOG.log(Level.SEVERE, "Instance of Car is null");
            throw new IllegalArgumentException("Instance of Car is null");
        }

        if (c.getCarType() == null || c.getCarType().isEmpty()) {
            LOG.log(Level.SEVERE, "Attribute carType of Car {0} is null or empty", c);
            throw new IllegalArgumentException("Attribute carType of Car " + c + " is null or empty");
        }

        if (c.getEvidencePlate() == null || c.getEvidencePlate().isEmpty()) {
            LOG.log(Level.SEVERE, "Attribute evidencePlate of Car {0} is null or empty", c);
            throw new IllegalArgumentException("Attribute evidencePlate of Car " + c + " is null or empty");
        }
    }

    public void checkCarWithId(Car c) {
        validate(c);

        if (c.getId() == null) {
            LOG.log(Level.SEVERE, "Attribute id of Car {0} is null", c);
            throw new IllegalArgumentException("Attribute id of Car " + c + " is null");
        }
    }

    public void checkCarWithoutId(Car c) {
        validate(c);

        if (c.getId() != null) {
            LOG.log(Level.SEVERE, "Attribute id of Car {0} is not null", c);
            throw new IllegalArgumentException("Attribute id of Car " + c + " is not null");
        }
    }
}
