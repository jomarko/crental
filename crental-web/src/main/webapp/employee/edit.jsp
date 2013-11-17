<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="employee.edit.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.EmployeeActionBean" var="actionBean"/>
            <s:form beanclass="cz.muni.fi.pompe.crental.web.EmployeeActionBean" action="/employee/save" method="post">
                <s:hidden name="employee.id"/>
                <fieldset><legend><f:message key="employee.edit.edit"/></legend>
                    <%@include file="form.jsp"%>
                    <s:submit class="btn btn-success" name="save" value="save"><f:message key="button.save"/></s:submit>
                    <input class="btn btn-danger" type="submit" formaction="${pageContext.request.contextPath}/employee/list" value="<f:message key="button.cancel"/>">
                </fieldset>
            </s:form>
    </s:layout-component>
</s:layout-render>