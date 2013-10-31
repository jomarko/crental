package cz.muni.fi.pompe.crental.entity;

import cz.muni.fi.pompe.crental.dao.DAOCar;
import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dao.impl.DAOCarImpl;
import cz.muni.fi.pompe.crental.dao.impl.DAOEmployeeImpl;
import cz.muni.fi.pompe.crental.dao.impl.DAORequestImpl;
import cz.muni.fi.pompe.crental.dto.DTORent;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Entity
@NamedQueries(value={
    @NamedQuery(name = "Rent.SelectAllRents", query = "SELECT r FROM Rent r"),
    @NamedQuery(name = "Rent.SelectRentById", query = "SELECT r FROM Rent r WHERE r.id = :id")
})
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne(optional = false)
    private Request request;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date confirmedAt;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee confirmedBy;
    
    
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car rentedCar;

    public Long getId() {
        return id;
    }

    public Request getRequest() {
        return request;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public Employee getConfirmedBy() {
        return confirmedBy;
    }

    public Car getRentedCar() {
        return rentedCar;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public void setConfirmedBy(Employee confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.confirmedAt);
        hash = 67 * hash + Objects.hashCode(this.confirmedBy);
        hash = 67 * hash + Objects.hashCode(this.rentedCar);
        hash = 67 * hash + Objects.hashCode(this.request);
        return hash;
    }

   @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rent other = (Rent) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.confirmedAt, other.confirmedAt)) {
            return false;
        }
        if (!Objects.equals(this.confirmedBy, other.confirmedBy)) {
            return false;
        }
        if (!Objects.equals(this.rentedCar, other.rentedCar)) {
            return false;
        }
        if (!Objects.equals(this.request, other.request)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rent{" + "id=" + id + ", request=" + request + ", confirmedAt=" + confirmedAt + ", confirmedBy=" + confirmedBy + ", rentedCar=" + rentedCar + '}';
    }
}
