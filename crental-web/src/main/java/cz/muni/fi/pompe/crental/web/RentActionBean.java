package cz.muni.fi.pompe.crental.web;

import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.dto.DTORent;
import cz.muni.fi.pompe.crental.dto.DTORequest;
import cz.muni.fi.pompe.crental.security.Principals;
import cz.muni.fi.pompe.crental.service.AbstractCarService;
import cz.muni.fi.pompe.crental.service.AbstractEmployeeService;
import cz.muni.fi.pompe.crental.service.AbstractRentService;
import cz.muni.fi.pompe.crental.service.AbstractRequestService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Roman Stehlik
 */
@UrlBinding("/rent/{$event}/{rent.id}/{rent.requestId}")
public class RentActionBean extends BaseActionBean {
    private final static String EDIT = "/rent/edit.jsp";
    private final static String LIST = "/rent/list.jsp";
    
    @SpringBean
    private AbstractRentService rentService;
    
    @SpringBean
    private AbstractRequestService requestService;
    
    @SpringBean
    private AbstractEmployeeService employeeService;    
    
    @SpringBean
    private AbstractCarService carService;    
    
    private List<DTORent> rents;
    
    @ValidateNestedProperties(value = {
        @Validate(on = {"save"}, field = "rentedCarId", required = true)
    })
    private DTORent rent;
    private DTORequest request;
    private DTOEmployee employee;
    private HashMap<Long, String> adminMap;
    private HashMap<Long, DTOCar> carMap;
    private HashMap<Long, DTORequest> requestMap = new HashMap<>();
    private Date today;

    public HashMap<Long, DTORequest> getRequestMap() {
        return requestMap;
    }

    public HashMap<Long, String> getAdminMap() {
        return adminMap;
    }
    
    public HashMap<Long, DTOCar> getCarMap() {
        return carMap;
    }

    public DTORequest getRequest() {
        return request;
    }

    public DTOEmployee getEmployee() {
        return employee;
    }
    
    public DTORent getRent() {
        return rent;
    }

    public void setRent(DTORent rent) {
        this.rent = rent;
    }

    public List<DTORent> getRents() {
        return rents;
    }

    public Date getToday() {
        return today;
    }

    @DefaultHandler
    public Resolution list() {
        setCarMap(carService.getAllCars());
        setAdminMap();
        setRequestMap();
        
        Subject user = SecurityUtils.getSubject();
        Principals p = (Principals) user.getPrincipal();
        
        if (user.hasRole("admin")) {
            rents = rentService.getAllRents();
        } else {
            rents = rentService.getAllRentsOfEmployee(employeeService.getEmployeeById(p.getId()));
        }
        
        setToday();
        return new ForwardResolution(LIST);
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "delete", "save"})
    public void loadRent() {
        String ids = getContext().getRequest().getParameter("rent.id");
        if (ids == null || ids.isEmpty()) return;
        rent = rentService.getRentById(Long.parseLong(ids));
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadRequest() {
        Long requestId;
        
        if (rent == null || rent.getRequestId() == null) {
            String ids = getContext().getRequest().getParameter("rent.requestId");
            if (ids == null || ids.isEmpty()) return;
            requestId = Long.parseLong(ids);
        } else {
            requestId = rent.getRequestId();
        }
        
        request = requestService.getRequestById(requestId);
        employee = employeeService.getEmployeeById(request.getEmployeeId());
        setAdminMap();
        setCarMap(carService.getFreeCars(request.getDateFrom(), request.getDateTo()));
        
        if (rent != null && rent.getId() != null) {
            carMap.put(rent.getRentedCarId(), carService.getCarById(rent.getRentedCarId()));
        }
    }
    
    public Resolution edit() {
        return new ForwardResolution(EDIT);
    }

    public Resolution delete() {
        setToday();
        String message_key = "common.delete";
        request = requestService.getRequestById(rent.getRequestId());
        
        if (request.getDateFrom().before(today)) {
            message_key = "rent.delete.dateMissMatch";
        } else {
            try {
                rentService.deleteRent(rent);
                message_key += ".success";
            } catch (Exception e) {
                message_key += ".fail";
            }
        }
        
        getContext().getMessages().add(new LocalizableMessage(message_key, rent.getId()));
        return new RedirectResolution(this.getClass(), "list");
    }

    public Resolution save() {
        Subject user = SecurityUtils.getSubject();
        Principals p = (Principals) user.getPrincipal();
        rent.setConfirmedById(p.getId());
        rent.setConfirmedAt(new Date());
        log.debug("Saved Rent {}", rent);
        String message_key = "common";
        
        if (rent.getId() == null) {
            message_key += ".create";
            
            try {
                this.rentService.createRent(rent);
                message_key += ".success";
            } catch (Exception e) {
                message_key += ".fail";
            }
        } else {
            message_key += ".update";
            
            try {
                this.rentService.updateRent(rent);
                message_key += ".success";
            } catch (Exception e) {
                message_key += ".fail";
            }
        }
        
        getContext().getMessages().add(new LocalizableMessage(message_key, rent.getId()));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    private void setAdminMap() {
        adminMap = employeeService.getAdminsNamesMap();
    }

    private void setCarMap(List<DTOCar> cars) {
        carMap = new HashMap<>();
        
        for (DTOCar c:cars) {
            carMap.put(c.getId(), c);
        }
    }

    private void setRequestMap() {
        requestMap = new HashMap<>();
        
        for (DTORequest r:requestService.getAllRequests()) {
            requestMap.put(r.getId(), r);
        }
    }

    private void setToday() {
        today = calcToday();
    }
}
