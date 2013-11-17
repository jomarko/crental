package cz.muni.fi.pompe.crental.web;

import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.service.AbstractCarService;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 *
 * @author Patrik Pompe <patrik.pompe@gmail.com>
 */
@UrlBinding("/car/{$event}/{car.id}")
public class CarActionBean extends BaseActionBean {
    @SpringBean
    private AbstractCarService carService;
    private List<DTOCar> cars;

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
}
