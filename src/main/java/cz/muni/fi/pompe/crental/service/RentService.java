/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dao.DAORent;
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

    public void setDaorent(DAORent daorent) {
        this.daorent = daorent;
    }
    
    @Transactional
    public void createRent(DTORent dtorent) {
        if(dtorent != null) {
            daorent.createRent(new Rent(dtorent));
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
            daorent.updateRent(new Rent(dtorent));
        }
    }

    @Transactional(readOnly = true)
    public List<DTORent> getAllRents() {
        List<DTORent> result = new ArrayList<>();
        
        for(Rent r : daorent.getAllRents()){
            result.add(new DTORent(r));
        }
        
        return result;
    }

    @Transactional(readOnly = true)
    public DTORent getRentById(Long id) {
        DTORent result = null;
        Rent r = daorent.getRentById(id);
        if(r != null){
            result = new DTORent(r);
        }
        
        return result;
    }
    
}
