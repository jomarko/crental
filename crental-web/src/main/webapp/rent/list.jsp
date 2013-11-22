<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="rent.list.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.RentActionBean" var="actionBean"/>
        <div class="page-header">
            <h3><f:message key="rent.list.title"/></h3>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th><f:message key="request.employee"/></th>
                <th><f:message key="request.from"/></th>
                <th><f:message key="request.to"/></th>
                <th><f:message key="rent.car"/></th>
                <th><f:message key="rent.confirmedBy"/></th>
                <th><f:message key="rent.confirmedAt"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${actionBean.rents}" var="rent">
                <c:set var="car" value="${actionBean.carMap.get(rent.rentedCarId)}"/>
                <c:set var="admin" value="${actionBean.adminMap.get(rent.confirmedById)}"/>
                <c:set var="request" value="${actionBean.requestMap.get(rent.requestId)}"/>
                <tr>
                    <td>${request.id}</td>
                    <td><c:out value="${request.employeeName}"/></td>
                    <td><c:out value="${request.dateFrom}"/></td>
                    <td><c:out value="${request.dateTo}"/></td>
                    <td><c:out value="${car.evidencePlate} - ${car.carType}"/></td>
                    <td><c:out value="${admin.name}"/></td>
                    <td><c:out value="${rent.confirmedAt}"/></td>
                    <td>
                     <s:link beanclass="cz.muni.fi.pompe.crental.web.RentActionBean" event="edit" class="btn btn-success"><s:param name="rent.id" value="${rent.id}"/><f:message key="common.edit"/></s:link>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        
    </s:layout-component>
</s:layout-render>