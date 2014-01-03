package cz.muni.fi.pompe.crental.rest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Abstract parent for all project resources
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
abstract class Resource {

    public Resource() {
        authenticate();
    }

    /**
     * hardcoded autentization of rest api layer
     */
    private void authenticate() {
        UsernamePasswordToken token = new UsernamePasswordToken("rest", "rest");
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
    }
}
