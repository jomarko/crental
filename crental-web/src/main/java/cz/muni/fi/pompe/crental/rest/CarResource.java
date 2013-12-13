/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.rest;

import cz.muni.fi.pompe.crental.dto.DTOCar;
import cz.muni.fi.pompe.crental.service.AbstractCarService;
import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 *
 * @author jozef
 */
@Component
@Path("/car")
public class CarResource {
    @Autowired
    private AbstractCarService carService;
    
    @Context
    private UriInfo context;
 
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DTOCar> getAll() {
        return carService.getAllCars();
    }
    
    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DTOCar getById(@PathParam("id") Long id) {
        return carService.getCarById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCar(DTOCar car) {
        try{
            carService.createCar(car);
        }catch(DataAccessException ex){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK).build();
    }
    
    @PUT
    @Path("put/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putCar(DTOCar car) {
        Response response;
        if (car.getId() != null && carService.getCarById(car.getId()) != null) {
            try{
                carService.updateCar(car);
            }catch(DataAccessException ex){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            response = Response.status(Response.Status.OK).build();
        } else {
            response = Response.noContent().build();
        }
        return response;
    }
    
    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        
        if (id != null && carService.getCarById(id) != null) {
            try{
                carService.deleteCar(carService.getCarById(id));
            }catch(DataAccessException ex){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            return Response.status(Response.Status.OK).build();
        }
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
