package cz.muni.fi.pompe.crental;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class DAORequestImpl implements DAORequest{
    private EntityManagerFactory emf;
    
    public DAORequestImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public void validate(Request r) {
        if (r.getDateFrom() == null) {
            throw new IllegalArgumentException("'DateFrom' is required");
        }
        
        if (r.getDateTo() == null) {
            throw new IllegalArgumentException("'DateTo' is required");
        }
        
        if (r.getDateFrom().after(r.getDateTo())) {
            throw new IllegalArgumentException("'DateFrom' cannt be after 'DateTo'");
        }
        
        if (r.getDescription() == null || r.getDescription().isEmpty()) {
            throw new IllegalArgumentException("'Description' cannt be null or empty");
        }
        
        if (r.getEmployee() == null) {
            throw new IllegalArgumentException("'Employee' is required");
        }
    }
       
    @Override
    public void deleteRequest(Request r) {
        EntityManager em = emf.createEntityManager();
        
        if (r.getId() == null) {
            throw new IllegalArgumentException("Requested entity cannot be deleted");
        }
        
        try{
            Request toDelete = em.find(Request.class, r.getId());

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
    public List<Request> getAllRequest() {
        EntityManager em = this.emf.createEntityManager();
        
        try {
             TypedQuery<Request> query = em.createQuery("from Request as r", Request.class);
             return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public Request getRequestById(Long id) {
        EntityManager em = this.emf.createEntityManager();
        
        try {
             TypedQuery<Request> query = em.createQuery("from Request as r where r.id = ?1", Request.class);
             query.setParameter(1, id);
             return query.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void createRequest(Request r) {
        validate(r);
        if(r.getId() != null){
            throw new IllegalArgumentException("try to save request with set id");
        }
        saveRequest(r);
    }

    @Override
    public void updateRequest(Request r) {
        validate(r);
        saveRequest(r);
    }
    
    private void saveRequest(Request r) {
        
        EntityManager em = emf.createEntityManager();
        System.out.println(r);
        System.out.println(r.getEmployee());
        try {
            em.getTransaction().begin();    
            em.persist(r);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
