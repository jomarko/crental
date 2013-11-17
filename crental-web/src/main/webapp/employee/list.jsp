<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="employee.list.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.EmployeeActionBean" var="actionBean"/>

        <p><f:message key="employee.list.allemployees"/></p>

        <table class="basic">
            <tr>
                <th>id</th>
                <th><f:message key="employee.name"/></th>
                <th><f:message key="employee.password"/></th>
                <th><f:message key="employee.accessright"/></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${actionBean.employees}" var="employee">
                <tr>
                    <td>${employee.id}</td>
                    <td><c:out value="${employee.name}"/></td>
                    <td><c:out value="${employee.password}"/></td>
                    <td><c:out value="${employee.accessRight}"/></td>
                    <td>
                     <s:link beanclass="cz.muni.fi.pompe.crental.web.EmployeeActionBean" event="edit"><s:param name="employee.id" value="${employee.id}"/>edit</s:link>
                    </td>
                    <td>
                        <s:form beanclass="cz.muni.fi.pompe.crental.web.EmployeeActionBean" action="delete">
                            <s:hidden name="employee.id" value="${employee.id}"/>
                            <s:submit name="delete"><f:message key="employee.list.delete"/></s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <s:form beanclass="cz.muni.fi.pompe.crental.web.EmployeeActionBean" action="add">
            <fieldset><legend><f:message key="employee.list.newemployee"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="add"><f:message key="employee.list.newemployee"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>