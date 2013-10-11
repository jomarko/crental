package cz.muni.fi.pompe.crental;

import java.io.Serializable;
import java.util.Date;
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
