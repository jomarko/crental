
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="login.title">
    <s:layout-component name="body">

        <div class="row">
        <h1>Login form</h1>
        <s:form beanclass="cz.muni.fi.pompe.crental.web.AuthActionBean" action="/auth/login" method="post">
        <table align="left" border="0" cellspacing="0" cellpadding="3">
            <tr>
                <td><f:message key="employee.name"/></td>
                <td><s:text name="employee.name"/></td>
            </tr>
            <tr>
                <td><f:message key="employee.password"/></td>
                <td><s:password name="employee.password"/></td>
            </tr>
            
            <tr>
                <td colspan="2" align="right"><s:submit name="login"><f:message key="navigation.login"/></s:submit></td>
            </tr>
        </table> 
        </s:form>
        </div>
    </s:layout-component>
</s:layout-render>
