package cz.muni.fi.pompe.crental.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

/**
 * Parent for every project service
 * holds common methods
 * 
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
abstract public class CrentalService {
    protected void checkAdmin() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.isAuthenticated();
        currentUser.checkRole("admin");
    }
    
    protected void checkPermission(String permission) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.checkPermission(permission);
    }
    
    protected void checkAuthentication() {
        Subject currentUser = SecurityUtils.getSubject();
        
        if (!currentUser.isAuthenticated()) {
            throw new AuthorizationException("Calling this method require authentication");
        }
    }
}
