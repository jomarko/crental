/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOCar;
import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORent;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dto.DTORent;
import cz.muni.fi.pompe.crental.entity.Rent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
@Service
public class RentService {
    
    private DAORent daorent;
    private DAOCar daocar;
    private DAOEmployee daoemp;
    private DAORequest daoreq;

    public void setDaorent(DAORent daorent) {
        this.daorent = daorent;
    }

    public void setDaocar(DAOCar daocar) {
        this.daocar = daocar;
    }

    public void setDaoemp(DAOEmployee daoemp) {
        this.daoemp = daoemp;
    }

    public void setDaoreq(DAORequest daoreq) {
        this.daoreq = daoreq;
    }
    
    @Transactional
    public void createRent(DTORent dtorent) {
        if(dtorent != null) {
            daorent.createRent(dtoToEntity(dtorent));
        }
    }

    @Transactional
    public void deleteRent(DTORent dtorent) {
        if(dtorent != null && dtorent.getId() != null) {
            daorent.deleteRent(dtorent.getId());
        }
    }

    @Transactional
    public void updateRent(DTORent dtorent) {
        if(dtorent != null) {
            daorent.updateRent(dtoToEntity(dtorent));
        }
    }

    @Transactional(readOnly = true)
    public List<DTORent> getAllRents() {
        List<DTORent> result = new ArrayList<>();
        
        for(Rent r : daorent.getAllRents()){
            result.add(entityToDto(r));
        }
        
        return result;
    }

    @Transactional(readOnly = true)
    public DTORent getRentById(Long id) {
        DTORent result = null;
        Rent r = daorent.getRentById(id);
        if(r != null){
            result = entityToDto(r);
        }
        
        return result;
    }
    
    private Rent dtoToEntity(DTORent dtorent) {
        Rent r = new Rent();
        
        r.setId(dtorent.getId());
        r.setConfirmedAt(dtorent.getConfirmedAt());
        r.setRentedCar(daocar.getCarById(dtorent.getRentedCarId()));
        r.setConfirmedBy(daoemp.getEmployeeById(dtorent.getConfirmedById()));
        r.setRequest(daoreq.getRequestById(dtorent.getRequestId()));
        
        return r;
    }
    
    private DTORent entityToDto(Rent r){
        DTORent dto = new DTORent();
        dto.setId(r.getId());
        dto.setConfirmedAt(r.getConfirmedAt());
        dto.setRequestId(r.getRequest().getId());
        dto.setRentedCarId(r.getRentedCar().getId());
        dto.setConfirmedById(r.getConfirmedBy().getId());
        
        return dto;
    }
}
