package cz.muni.fi.pompe.crental.service;

import cz.muni.fi.pompe.crental.dto.AccessRight;
import cz.muni.fi.pompe.crental.dto.DTOEmployee;
import java.util.Arrays;
import java.util.List;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class ShiroRealm extends AuthorizingRealm {
    private AbstractEmployeeService employeeService;

    public void setEmployeeService(AbstractEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        System.out.println("autorizace: " + pc.toString());
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        boolean admin;
        Long eID = null;

        if (pc.getPrimaryPrincipal().equals("rest")) {
            admin = true;
        } else {
            DTOEmployee employee = this.employeeService.getEmployeeByName(pc.toString());
            admin = employee.getAccessRight() == AccessRight.Admin;
            eID = employee.getId();
        }

        if (admin) {
            authInfo.addRole("admin");
            authInfo.addStringPermission("*");
        } else {
            authInfo.addRole("employee");

            List<String> permissions = Arrays.asList(
                "rent:getAllOfEmployee:" + eID,
                "request:delete:" + eID,
                "request:update:" + eID,
                "request:get:" + eID,
                "employee:update:" + eID,
                "employee:get:" + eID,
                "employee:get:" + eID
            );
            
            authInfo.addStringPermissions(permissions);
        }

        return authInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) at;
        
        if (upToken.getUsername().equals("rest") && new String(upToken.getPassword()).equals("rest")) {
            return new SimpleAuthenticationInfo(upToken.getUsername(), upToken.getPassword(), this.getName());
        } else {

        }

        throw new AccountException("Authentication failed.");
    }
}
