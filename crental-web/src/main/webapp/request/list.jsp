<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<s:layout-render name="/layout.jsp" titlekey="request.list.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.RequestActionBean" var="actionBean"/>
        <div class="page-header">
            <h3><f:message key="request.list.title"/></h3>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th><f:message key="request.employeeId"/></th>
                <th><f:message key="request.dateFrom"/></th>
                <th><f:message key="request.dateTo"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${actionBean.requests}" var="request">
                <tr>
                    <td>${request.id}</td>
                    <td><c:out value="${request.employeeName}"/></td>
                    <td><f:formatDate type="date" dateStyle="short" value="${request.dateFrom}" /></td>
                    <td><f:formatDate type="date" dateStyle="short" value="${request.dateTo}"/></td>
                    <td>
                     
                     <s:link beanclass="cz.muni.fi.pompe.crental.web.RequestActionBean" event="edit" class="btn btn-success"><s:param name="request.id" value="${request.id}"/><f:message key="common.edit"/></s:link>
                     
                     <shiro:hasRole name="admin">
                     <s:link beanclass="cz.muni.fi.pompe.crental.web.RentActionBean" event="edit" class="btn btn-success"><s:param name="rent.requestId" value="${request.id}"/><f:message key="request.approve"/></s:link>
                     </shiro:hasRole>
                    </td>
                    <td>
                        <s:form beanclass="cz.muni.fi.pompe.crental.web.RequestActionBean" action="delete">
                            <s:hidden name="request.id" value="${request.id}"/>
                            <s:submit name="delete" class="btn btn-danger"><f:message key="common.delete"/></s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <s:errors/>
        <s:form beanclass="cz.muni.fi.pompe.crental.web.RequestActionBean" action="add">
            <fieldset><legend><f:message key="request.list.newrequest"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="add" class="btn btn-success"><f:message key="request.list.newrequest"/></s:submit>
            </fieldset>
        </s:form>
        
    </s:layout-component>
</s:layout-render>