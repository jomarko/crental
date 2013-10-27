package cz.muni.fi.pompe.crental.dao.impl;

import cz.muni.fi.pompe.crental.entity.Request;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class DAORequestImpl implements DAORequest {

    private EntityManagerFactory emf;
    private static final Logger LOG = Logger.getLogger(DAOCarImpl.class.getName());

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

        try {
            Request toDelete = em.find(Request.class, r.getId());

            em.getTransaction().begin();
            em.remove(toDelete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        LOG.log(Level.INFO, "Request with id {0} was deleted from the database", r.getId());
    }

    @Override
    public List<Request> getAllRequest() {
        EntityManager em = this.emf.createEntityManager();
        List<Request> result = new ArrayList<>();
        try {
            TypedQuery<Request> query = em.createQuery("from Request as r", Request.class);
            result = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return result;
    }

    @Override
    public Request getRequestById(Long id) {
        EntityManager em = this.emf.createEntityManager();
        if (id == null) {
            throw new NullPointerException("given id was null");
        }
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

        if (r.getId() != null) {
            throw new IllegalArgumentException("try to save request with set id");
        }

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        LOG.log(Level.INFO, "Request {0} was persisted into the database", r);
    }

    @Override
    public void updateRequest(Request r) {
        validate(r);

        if (r.getId() == null) {
            throw new IllegalArgumentException("try to update request without id");
        }

        EntityManager em = emf.createEntityManager();
        Request toUpdate = em.find(Request.class, r.getId());

        try {
            em.getTransaction().begin();
            toUpdate.setDateFrom(r.getDateFrom());
            toUpdate.setDateTo(r.getDateTo());
            toUpdate.setDescription(r.getDescription());
            toUpdate.setEmployee(r.getEmployee());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        LOG.log(Level.INFO, "Request {0} was updated", r);
    }
}
