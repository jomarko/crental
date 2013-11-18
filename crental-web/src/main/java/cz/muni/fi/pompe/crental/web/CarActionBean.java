package cz.muni.fi.pompe.crental.web;

import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.service.AbstractCarService;
import static cz.muni.fi.pompe.crental.web.BaseActionBean.escapeHTML;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
@UrlBinding("/car/{$event}/{car.id}")
public class CarActionBean extends BaseActionBean {
    private final static String EMPLOYEE_FORM = "/car/form.jsp";

    @SpringBean
    private AbstractCarService carService;
    
    private List<DTOCar> cars;
    
    @ValidateNestedProperties(value = {
        @Validate(on = {"save"}, field = "carType", required = true),
        @Validate(on = {"save"}, field = "evidencePlate", required = true)
    })
    private DTOCar car;

    public DTOCar getCar() {
        return car;
    }

    public void setCar(DTOCar car) {
        this.car = car;
    }

    public List<DTOCar> getCars() {
        return cars;
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit"})
    public void loadCar() {
        String ids = getContext().getRequest().getParameter("car.id");
        if (ids == null) return;
        this.log.debug("loadCar(), car={}", car);
        car = carService.getCarById(Long.parseLong(ids));
    }

    @DefaultHandler
    public Resolution list() {
        cars = carService.getAllCars();
        this.log.debug("list()");
        return new ForwardResolution("/car/list.jsp");
    }

    public Resolution edit() {
        this.log.debug("edit(), car={}", car);
        return new ForwardResolution(EMPLOYEE_FORM);
    }

    public Resolution save() throws Exception {
        this.log.debug("save(), car={}", car);
        String message_key = "common";
        
        if (car.getId() == null ) {
            message_key += ".create";
            try {
                carService.createCar(car);
                message_key += ".success";
            } catch (Exception e) {
                message_key += ".fail";
            }
            
        } else {
            message_key += ".update";
            
            try {
                carService.updateCar(car);
                message_key += ".success";
            } catch(Exception e) {
                message_key += ".fail";
            }
        }
        
        getContext().getMessages().add(new LocalizableMessage(message_key, car.getId()));
        return new RedirectResolution(this.getClass(), "list");
    }

    @HandlesEvent("delete")
    public Resolution delete() {
        String message_key = "common.delete";
        
        try {
            carService.deleteCar(car);
            message_key += ".success";
            this.log.debug("delete(), car={}", car);
        } catch (IllegalArgumentException e) {
            message_key += ".fail";
        }
        
        getContext().getMessages().add(new LocalizableMessage(message_key, car.getId()));
        return new RedirectResolution(this.getClass(), "list");
    }

    public Resolution cancel() {
        return new RedirectResolution(this.getClass(), "list");
    }
}
