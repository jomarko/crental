/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.dto;

import cz.muni.fi.pompe.crental.entity.Rent;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jozef
 */
public class DTORent {
    private Long id;
    
    private Date confirmedAt;
    
    private Long requestId;
    
    private Long confirmedById;
    
    private Long rentedCarId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getConfirmedById() {
        return confirmedById;
    }

    public void setConfirmedById(Long confirmedById) {
        this.confirmedById = confirmedById;
    }

    public Long getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(Long rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.confirmedAt);
        hash = 37 * hash + Objects.hashCode(this.requestId);
        hash = 37 * hash + Objects.hashCode(this.confirmedById);
        hash = 37 * hash + Objects.hashCode(this.rentedCarId);
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
        final DTORent other = (DTORent) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.confirmedAt, other.confirmedAt)) {
            return false;
        }
        if (!Objects.equals(this.requestId, other.requestId)) {
            return false;
        }
        if (!Objects.equals(this.confirmedById, other.confirmedById)) {
            return false;
        }
        if (!Objects.equals(this.rentedCarId, other.rentedCarId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTORent{" + "id=" + id + ", confirmedAt=" + confirmedAt + ", requestId=" + requestId + ", confirmedById=" + confirmedById + ", rentedCarId=" + rentedCarId + '}';
    }
}
