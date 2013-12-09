/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.rest;

import java.util.Set;
import javax.ws.rs.core.Application;
 
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {
 
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cz.muni.fi.pompe.crental.rest.CarResource.class);
        resources.add(cz.muni.fi.pompe.crental.rest.EmployeeResource.class);
    }
}