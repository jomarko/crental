package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAOCar;
import cz.muni.fi.pompe.crental.dao.DAOEmployee;
import cz.muni.fi.pompe.crental.dao.DAORent;
import cz.muni.fi.pompe.crental.dao.DAORequest;
import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.dto.DTORent;
import cz.muni.fi.pompe.crental.entity.Rent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jozef
 */
@Service
public class RentService extends CrentalService implements AbstractRentService {
    
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
    @Override
    public void createRent(DTORent dtorent) {
        checkAdmin();
        
        if(dtorent != null) {
            Rent r = dtoToEntity(dtorent);
            daorent.createRent(r);
            dtorent.setId(r.getId());
        }
    }

    @Transactional
    @Override
    public void deleteRent(DTORent dtorent) {
        checkAdmin();
        
        if(dtorent != null && dtorent.getId() != null) {
            daorent.deleteRent(dtorent.getId());
        }
    }

    @Transactional
    @Override
    public void updateRent(DTORent dtorent) {
        checkAdmin();
        
        if(dtorent != null) {
            daorent.updateRent(dtoToEntity(dtorent));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<DTORent> getAllRents() {
        checkAuthentication();
        List<DTORent> result = new ArrayList<>();
        
        for(Rent r : daorent.getAllRents()){
            result.add(entityToDto(r));
        }
        
        return result;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<DTORent> getAllRentsIn(Date from, Date to) {
        checkAdmin();
        List<DTORent> result = new ArrayList<>();
        if(from != null && to != null && to.compareTo(from)>= 0){
            for(Rent r : daorent.getAllRents()){
                Date reqFrom = r.getRequest().getDateFrom();
                Date reqTo = r.getRequest().getDateTo();
                if((reqFrom.compareTo(from)>= 0 &&
                   reqFrom.compareTo(to)<= 0) ||
                   (reqTo.compareTo(from)>= 0 &&
                   reqTo.compareTo(to)<= 0)){
                   result.add(entityToDto(r));
                }
            }
        }
        
        return result;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<DTORent> getAllRentsOfCar(DTOCar dtocar) {
        checkAdmin();
        List<DTORent> result = new ArrayList<>();
        if(dtocar != null && dtocar.getId() != null){
            for(Rent r : daorent.getAllRents()){
                if(r.getRentedCar().equals(CarService.dtoToEntity(dtocar))){
                   result.add(entityToDto(r));
                }
            }
        }
        
        return result;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<DTORent> getAllRentsOfEmployee(DTOEmployee dtoemp) {
        List<DTORent> result = new ArrayList<>();
        if(dtoemp != null && dtoemp.getId() != null){
            checkPermission("rent:getAllOfEmployee:" + dtoemp.getId());
            
            for(Rent r : daorent.getAllRents()){
                if(r.getRequest().getEmployee().equals(EmployeeService.dtoToEntity(dtoemp))){
                   result.add(entityToDto(r));
                }
            }
        }
        
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public DTORent getRentById(Long id) {
        checkAdmin();
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
