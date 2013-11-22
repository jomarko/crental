package cz.muni.fi.pompe.crental.dto;

import java.util.Objects;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class DTOCar {
    private Long id;
    private String carType;
    private String evidencePlate;

    public DTOCar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEvidencePlate() {
        return evidencePlate;
    }

    public void setEvidencePlate(String evidencePlate) {
        this.evidencePlate = evidencePlate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.carType);
        hash = 43 * hash + Objects.hashCode(this.evidencePlate);
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
        final DTOCar other = (DTOCar) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.carType, other.carType)) {
            return false;
        }
        if (!Objects.equals(this.evidencePlate, other.evidencePlate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTOCar{" + "id=" + id + ", carType=" + carType + ", evidencePlate=" + evidencePlate + '}';
    }
}