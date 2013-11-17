package cz.muni.fi.pompe.crental.web;

import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.service.AbstractCarService;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 *
 * @author Patrik Pompe <patrik.pompe@gmail.com>
 */
@UrlBinding("/car/{$event}/{car.id}")
public class CarActionBean extends BaseActionBean {
    private final static String EMPLOYEE_FORM = "/car/form.jsp";

    @SpringBean
    private AbstractCarService carService;
    private List<DTOCar> cars;
    private DTOCar car;

    public DTOCar getCar() {
        return car;
    }

    public void setCar(DTOCar car) {
        this.car = car;
    }

    @ValidateNestedProperties(value = {
        @Validate(on = {"save"}, field = "carType", required = true),
        @Validate(on = {"save"}, field = "evidencePlate", required = true)
    })

    public List<DTOCar> getCars() {
        return cars;
    }

    public void setCars(List<DTOCar> cars) {
        this.cars = cars;
    }

    @DefaultHandler
    public Resolution list() {
        cars = carService.getAllCars();
        return new ForwardResolution("/car/list.jsp");
    }

    public Resolution edit() {
        return new ForwardResolution(EMPLOYEE_FORM);
    }


    public Resolution save() throws Exception {
        if (car.getId() == null ) {
            carService.createCar(car);
        } else {
            carService.updateCar(car);
        }

        return new RedirectResolution(this.getClass(), "list");
    }

    public Resolution delete() {
        carService.deleteCar(car);
        return new RedirectResolution(this.getClass(), "list");
    }

    //TODO
    public Resolution cancel() {
        return new RedirectResolution(this.getClass(), "list");
    }
}
