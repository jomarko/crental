package cz.muni.fi.pompe.crental.entity;

import cz.muni.fi.pompe.crental.dto.DTORequest;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@Entity
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dateFrom;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dateTo;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.dateFrom);
        hash = 73 * hash + Objects.hashCode(this.dateTo);
        hash = 73 * hash + Objects.hashCode(this.description);
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
        final Request other = (Request) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateFrom, other.dateFrom)) {
            return false;
        }
        if (!Objects.equals(this.dateTo, other.dateTo)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
    private String description;
    
    @ManyToOne(targetEntity=Employee.class)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Long getId() {
        return id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getDescription() {
        return description;
    }

    public Employee getEmployee() {
        return employee;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    @Override
    public String toString() {
        return "Request{" + "id=" + id + ", description=" + description + ", eployee_id=" + employee.getId() + ",dateFrom=" + dateFrom + ", dateTo=" + dateTo + '}';
    }
}
