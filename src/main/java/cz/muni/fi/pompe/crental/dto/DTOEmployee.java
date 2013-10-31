/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.dto;

import cz.muni.fi.pompe.crental.entity.AccessRight;
import cz.muni.fi.pompe.crental.entity.Employee;

/**
 *
 * @author jozef
 */
public class DTOEmployee {
    private Long id;

    private String name;
    
    private String password;
    
    private AccessRight accessRight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (this.password != null ? this.password.hashCode() : 0);
        hash = 37 * hash + (this.accessRight != null ? this.accessRight.hashCode() : 0);
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
        final DTOEmployee other = (DTOEmployee) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        if (this.accessRight != other.accessRight) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTOEmployee{" + "id=" + id + ", name=" + name + ", password=" + password + ", accessRight=" + accessRight + '}';
    }
}
