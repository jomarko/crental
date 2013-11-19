/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.web;

import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.dto.DTORent;
import cz.muni.fi.pompe.crental.dto.DTORequest;
import cz.muni.fi.pompe.crental.entity.Rent;
import cz.muni.fi.pompe.crental.service.AbstractEmployeeService;
import cz.muni.fi.pompe.crental.service.AbstractRentService;
import cz.muni.fi.pompe.crental.service.AbstractRequestService;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 *
 * @author Yayo ukrajinsky zid
 */
public class RentActionBean extends BaseActionBean {

    private final static String FORM = "/rent/form.jsp";
    private final static String LIST = "/rent/list.jsp";
    @SpringBean
    private AbstractRequestService requestService;
    @SpringBean
    private AbstractEmployeeService employeeService;
    @SpringBean
    private AbstractRentService rentService;
    private List<DTORent> rents;
    private List<DTORequest> requests;
    private List<DTOEmployee> employees;
    private DTORent rent;

    public DTORent getRent() {
        return rent;
    }

    public void setRent(DTORent rent) {
        this.rent = rent;
    }

    public List<DTORent> getRents() {
        rents =  rentService.getAllRents();
        return rents;
    }

    public List<DTORequest> getRequests() {
        return requests;
    }

    public List<DTOEmployee> getEmployees() {
        return employees;
    }

    @DefaultHandler
    public Resolution list() {
        return new ForwardResolution(LIST);
    }

    public Resolution edit() {
        return new ForwardResolution(LIST);
    }

    public Resolution delete() {
        return new ForwardResolution(LIST);
    }

    public Resolution add() {
        rentService.createRent(rent);
        return new ForwardResolution(LIST);
    }

    public Resolution cancel() {
        return new ForwardResolution(LIST);
    }
}
