package cz.muni.fi.pompe.crental;

import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Patrik Pompe xpompe00@stud.fit.vutbr.cz
 */
public class DAOCarImpl implements DAOCar{

    private EntityManagerFactory emf;
    
    /**
     * Method set EntityManagerFactory for this class.
     * This class wouldn't work without EntityManagerFactory.
     * @param emf EntityManagerFactory to be set
     */
    public void setEntityManagerFactory(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    @Override
    public void createCar(Car car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCar(Car car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCar(Car car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> getAllCars() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Car getCarById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
