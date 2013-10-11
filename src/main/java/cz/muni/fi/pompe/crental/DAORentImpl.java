package cz.muni.fi.pompe.crental;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Patrik Pompe xpompe00@stud.fit.vutbr.cz
 */
public class DAORentImpl implements DAORent{

    private EntityManagerFactory emf;
    
    public DAORentImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public void createRent(Rent rent) {
        validate(rent);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();    
            em.persist(rent);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void deleteRent(Rent rent) {
        EntityManager em = emf.createEntityManager();
        
        if (rent.getId() == null) {
            throw new IllegalArgumentException("Rent entity cannot be deleted");
        }
        
        try{
            Rent toDelete = em.find(Rent.class, rent.getId());

            em.getTransaction().begin();
            em.remove(toDelete);
            em.getTransaction().commit();
        } finally{
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void updateRent(Rent rent) {
        validate(rent);
        
        if (rent.getId() == null) {
            throw new IllegalArgumentException("'Rent without id cannt be updated'");
        }
        
        EntityManager em = emf.createEntityManager();
        Rent rentToUpdate = em.find(Rent.class, rent.getId());
        
        try {
            em.getTransaction().begin();    
            rentToUpdate.setConfirmedAt(rent.getConfirmedAt());
            rentToUpdate.setConfirmedBy(rent.getConfirmedBy());
            rent.setRentedCar(rent.getRentedCar());
            rent.setRequest(rent.getRequest());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Rent> getAllRents() {
        EntityManager em = this.emf.createEntityManager();
        
        try {
             TypedQuery<Rent> query = em.createQuery("from Rent as r", Rent.class);
             return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Rent getRentById(Long id) {
        EntityManager em = this.emf.createEntityManager();
        
        try {
             TypedQuery<Rent> query = em.createQuery("from Rent as r where r.id = ?1", Rent.class);
             query.setParameter(1, id);
             return query.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void validate(Rent r) {
        if (r == null) {
            throw new NullPointerException("Rent is null");
        }
        
        if (r.getConfirmedAt()== null) {
            throw new IllegalArgumentException("'ConfirmedDate' is required");
        }
        
        if (r.getConfirmedBy() == null) {
            throw new IllegalArgumentException("'ConfirmedBy' is required");
        }
        
        if (r.getRentedCar() == null) {
            throw new IllegalArgumentException("'RentedCar is required'");
        }
        
        if (r.getRequest()== null) {
            throw new IllegalArgumentException("'Request' is required");
        }
    }
}
