package cz.muni.fi.pompe.crental;

import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Patrik Pompe xpompe00@stud.fit.vutbr.cz
 */
public class DAORentImpl implements DAORent{

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
    public void createRent(Rent rent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRent(Rent rent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateRent(Rent rent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rent> getAllRents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rent getRentById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
