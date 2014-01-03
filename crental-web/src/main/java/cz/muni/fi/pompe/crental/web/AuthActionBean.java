/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.web;

import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import cz.muni.fi.pompe.crental.service.AbstractEmployeeService;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author jozef
 */
@UrlBinding("/auth/{$event}")
public class AuthActionBean extends BaseActionBean{
    private DTOEmployee employee;

    public DTOEmployee getEmployee() {
        return employee;
    }
    
    public void setEmployee(DTOEmployee employee) {
        this.employee = employee;
    }
    
    @DefaultHandler
    public Resolution form(){
        employee = null;
        return new ForwardResolution("login.jsp");
    }
    
    @HandlesEvent("login")
    public Resolution login(){
        if(employee != null && employee.getName() != null && employee.getPassword() != null){
            UsernamePasswordToken token = new UsernamePasswordToken(employee.getName(), employee.getPassword());
            Subject currentUser = SecurityUtils.getSubject();
            try{
                currentUser.login(token);
            }catch(AuthenticationException ex){
                getContext().getMessages().add(new LocalizableMessage("employee.login.fail"));       
                return new ForwardResolution("/login.jsp");
            }
            
            return new ForwardResolution("/index.jsp");
        }
        
        return new RedirectResolution(this.getClass(), "");
    }
    
    @HandlesEvent("logout")
    public Resolution logout(){
        
        Subject currentUser = SecurityUtils.getSubject();
            
        currentUser.logout();
        
        return new RedirectResolution(this.getClass(), "");
    }
}
