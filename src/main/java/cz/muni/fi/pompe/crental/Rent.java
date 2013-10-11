package cz.muni.fi.pompe.crental;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Entity
public class Rent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne(optional = false)
    private Request request;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date confirmedAt;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee confirmedBy;
    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id")
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
    public String toString() {
        return "Rent{id="+id+", confirmedAt="+confirmedAt+", employee_id="+confirmedBy.getId()+", car_id="+rentedCar.getId()+", request_id="+request.getId()+'}';
    }
}
