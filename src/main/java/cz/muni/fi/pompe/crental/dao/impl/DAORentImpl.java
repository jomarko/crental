package cz.muni.fi.pompe.crental.dao.impl;

import cz.muni.fi.pompe.crental.exception.CarRentalException;
import cz.muni.fi.pompe.crental.entity.AccessRight;
import cz.muni.fi.pompe.crental.entity.Rent;
import cz.muni.fi.pompe.crental.dao.DAORent;
import cz.muni.fi.pompe.crental.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jaro
 */
@Repository // for PersistenceExceptionTranslationPostProcessor to translate exceptions to DataAccessException
public class DAORentImpl implements DAORent {

    @PersistenceContext(unitName = "CarRentalPUInMemory")
    private EntityManager em;
    private static final Logger LOG = Logger.getLogger(DAORentImpl.class.getName());

    public DAORentImpl() {
    }

    @Override
    public void createRent(Rent rent) {
        checkRentWithoutId(rent);
        
        try {
            em.persist(rent);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to persist rent {0} into the database", rent);
            throw new IllegalArgumentException("Error when trying to persist rent " + rent + " into the database", ex);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Rent {0} was persisted into the database", rent);
    }

    @Override
    public void deleteRent(Long id) {
        if (id == null) {
            LOG.log(Level.SEVERE, "Cannot delete rent because attribute id is null");
            throw new IllegalArgumentException("Cannot delete rent because attribute id is null");
        }
        try {
            Rent rentToDelete = em.find(Rent.class, id);
            if (rentToDelete == null) {
                LOG.log(Level.SEVERE, "Rent with id {0} does not exist", id);
                throw new IllegalArgumentException("Rent with id " + id + " does not exist");
            }
            em.remove(rentToDelete);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to delete rent with id: {0} from the database", id);
            throw new IllegalArgumentException("Error when trying to delete rent with id: " + id + " from the database", ex);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Rent with id {0} was deleted from the database", id);
    }

    @Override
    public void updateRent(Rent rent) {
        checkRentWithId(rent);

        try {
            Rent rentToUpdate = em.find(Rent.class, rent.getId());
            if (rentToUpdate == null) {
                LOG.log(Level.SEVERE, "Rent with id {0} does not exist", rent.getId());
                throw new IllegalArgumentException("Rent with id " + rent.getId() + " does not exist");
            }
            rentToUpdate.setConfirmedAt(rent.getConfirmedAt());
            rentToUpdate.setConfirmedBy(rent.getConfirmedBy());
            rentToUpdate.setRentedCar(rent.getRentedCar());
            rentToUpdate.setRequest(rent.getRequest());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to update rent {0}", rent);
            throw new IllegalArgumentException("Error when trying to update rent " + rent + " from the database", ex);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Rent {0} was updated", rent);
    }

    @Override
    public List<Rent> getAllRents() {
        List<Rent> resultList = new ArrayList<>();

        try {
            TypedQuery<Rent> query = em.createNamedQuery("Rent.SelectAllRents", Rent.class);
            resultList = query.getResultList();
            LOG.log(Level.INFO, "All rents were retrieved");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error when trying to retrieve all rents from the database");
            throw new IllegalArgumentException("Error when trying to get all rents from the database", ex);
        } finally {
            em.close();
        }
        return resultList;
    }

    @Override
    public Rent getRentById(Long id) {
        if (id == null) {
            LOG.log(Level.SEVERE, "Cannot retrieve rent because attribute id is null");
            throw new IllegalArgumentException("Cannot retrieve rent because attribute id is null");
        }
        Rent result = null;
        try {
            TypedQuery<Rent> query = em.createNamedQuery("Rent.SelectRentById", Rent.class);
            query.setParameter("id", id);
            result = query.getSingleResult();
            LOG.log(Level.INFO, "Rentwith id {0} was retrieved", id);
        } catch (NoResultException ex) {
            LOG.log(Level.SEVERE, "There is no rent with id {0} in the database");
            throw new IllegalArgumentException("There is no rent with id " + id + " in the database", ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error while retrieving rent with id {0} from the database", id);
            throw new IllegalArgumentException("Error while retrieving rent with id " + id + " from the database", ex);
        } finally {
            em.close();
        }
        return result;
    }

    public void validate(Rent r) {
        if (r == null) {
            LOG.log(Level.SEVERE, "Instance of Rent is null");
            throw new IllegalArgumentException("Instance of Rent is null");
        }

        if (r.getConfirmedAt() == null) {
            LOG.log(Level.SEVERE, "Attribute confirmedAt of Rent {0} is null", r);
            throw new IllegalArgumentException("Attribute confirmedDate of Rent " + r + " is null");
        }

        if (r.getConfirmedBy() == null) {
            LOG.log(Level.SEVERE, "Attribute confirmedBy of Rent {0} is null", r);
            throw new IllegalArgumentException("Attribute confirmedBy of Rent " + r + " is null");
        }

        if (r.getConfirmedBy().getAccessRight() != AccessRight.Admin) {
            LOG.log(Level.SEVERE, "Request can be confirmed only by admin");
            throw new IllegalArgumentException("Request can be confirmed only by admin");
        }

        if (r.getRentedCar() == null) {
            LOG.log(Level.SEVERE, "Attribute rentedCar of Rent {0} is null", r);
            throw new IllegalArgumentException("Attribute rentedCar of Rent " + r + " is null");
        }
        
        if (r.getRequest() == null) {
            LOG.log(Level.SEVERE, "Attribute request of Rent {0} is null", r);
            throw new IllegalArgumentException("Attribute request of Rent " + r + " is null");
        }
    }

    public void checkRentWithId(Rent r) {
        validate(r);

        if (r.getId() == null) {
            LOG.log(Level.SEVERE, "Attribute id of Rent {0} is null", r);
            throw new IllegalArgumentException("Attribute id of Rent " + r + " is null");
        }
    }

    public void checkRentWithoutId(Rent r) {
        validate(r);

        if (r.getId() != null) {
            LOG.log(Level.SEVERE, "Attribute id of Rent {0} is not null", r);
            throw new IllegalArgumentException("Attribute id of Rent " + r + " is not null");
        }
    }
}
