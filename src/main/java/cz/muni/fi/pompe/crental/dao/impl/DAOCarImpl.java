package cz.muni.fi.pompe.crental.dao.impl;

import cz.muni.fi.pompe.crental.entity.Car;
import cz.muni.fi.pompe.crental.dao.DAOCar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Roman Stehlik
 */
public class DAOCarImpl implements DAOCar {

    private EntityManagerFactory emf;
    private static final Logger LOG = Logger.getLogger(DAOCarImpl.class.getName());
    
    public DAOCarImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    //Creates new car
    public void createCar(Car car) {
        EntityManager em = emf.createEntityManager();
        checkCar(car);
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Car {0} was persisted into the database", car);
    }

    @Override
    public void deleteCar(Car car) {
        checkCar(car);
        EntityManager em = emf.createEntityManager();

        try {
            Car newCar = em.find(Car.class, car.getId());

            em.getTransaction().begin();
            em.remove(newCar);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Car with id {0} was deleted from the database", car.getId());
    }

    @Override
    public void updateCar(Car car) {
        checkCar(car);
        EntityManager em = emf.createEntityManager();

        try {
            Car newCar = em.find(Car.class, car.getId());

            em.getTransaction().begin();
            newCar.setCarType(car.getCarType());
            newCar.setEvidencePlate(car.getEvidencePlate());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Car {0} was updated", car);
    }

    @Override
    public List<Car> getAllCars() {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Car.SelectAllCars", Car.class);
            List<Car> result = q.getResultList();
            return result;
        } finally {
            em.close();
        }
    }

    @Override
    public Car getCarById(Long id) {
        if(id == null){
            throw new NullPointerException("given id was null");
        }        
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Car.SelectCarById", Car.class);
            q.setParameter("id", id);
            return (Car) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    private void checkCar(Car car){
        if(car == null){
            throw new NullPointerException("car was null");
        }
        
        if(car.getCarType() == null || car.getCarType().isEmpty()){
            throw new IllegalArgumentException("carType cannt be null or empty");
        }
        
        if(car.getEvidencePlate()== null || car.getEvidencePlate().isEmpty()){
            throw new IllegalArgumentException("evidencePlate cannt be null or empty");
        }
    }
}
