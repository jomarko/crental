package cz.muni.fi.pompe.crental.dao.impl;

import cz.muni.fi.pompe.crental.entity.Request;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Repository // for PersistenceExceptionTranslationPostProcessor to translate exceptions to DataAccessException
public class DAORequestImpl implements DAORequest {

    @PersistenceContext(unitName = "CarRentalPUInMemory")
    private EntityManager em;
    private static final Logger LOG = Logger.getLogger(DAOCarImpl.class.getName());

    public DAORequestImpl() {
    }

    public void validate(Request r) {
        if (r == null) {
            throw new IllegalArgumentException("'Ruquest' is null");
        }
        
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
        validate(r);

        try {
            Request toDelete = em.find(Request.class, r.getId());
            em.remove(toDelete);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Request with id {0} was deleted from the database", r.getId());
    }

    @Override
    public List<Request> getAllRequests() {
        List<Request> result = new ArrayList<>();
        try {
            TypedQuery<Request> query = em.createQuery("from Request as r", Request.class);
            result = query.getResultList();
        } finally {
            em.close();
        }

        return result;
    }

    @Override
    public Request getRequestById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("given id was null");
        }
        try {
            TypedQuery<Request> query = em.createQuery("from Request as r where r.id = ?1", Request.class);
            query.setParameter(1, id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public void createRequest(Request r) {
        validate(r);

        if (r.getId() != null) {
            throw new IllegalArgumentException("try to save request with set id");
        }

        try {
            em.persist(r);
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Request {0} was persisted into the database", r);
    }

    @Override
    public void updateRequest(Request r) {
        validate(r);

        if (r.getId() == null) {
            throw new IllegalArgumentException("try to update request without id");
        }
        
        try {
            Request toUpdate = em.find(Request.class, r.getId());
            if(toUpdate == null){
                throw new IllegalArgumentException("No such:" + r + " in database");
            }
            toUpdate.setDateFrom(r.getDateFrom());
            toUpdate.setDateTo(r.getDateTo());
            toUpdate.setDescription(r.getDescription());
            toUpdate.setEmployee(r.getEmployee());
            
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Request {0} was updated", r);
    }

    @Override
    public List<Request> getUnconfirmedRequests() {
        List<Request> result = new ArrayList<>();
        try {
            TypedQuery<Request> query = em.createQuery("FROM Request AS rq WHERE rq NOT IN (SELECT rt.request FROM Rent rt)", Request.class);
            result = query.getResultList();
        } finally {
            em.close();
        }

        return result;
    }
}
