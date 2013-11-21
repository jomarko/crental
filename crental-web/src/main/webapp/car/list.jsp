<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="car.list.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" var="actionBean"/>
        <p class="text-right"><s:link beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" event="edit">+<f:message key="car.create"/></s:link></p>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th><f:message key="car.carType"/></th>
                <th><f:message key="car.evidencePlate"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${actionBean.cars}" var="car">
                <tr>
                    <td>${car.id}</td>
                    <td><c:out value="${car.carType}"/></td>
                    <td><c:out value="${car.evidencePlate}"/></td>
                    <td>
                        <s:link beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" event="edit" class="btn btn-success"><s:param name="car.id" value="${car.id}"/><f:message key="common.edit"/></s:link>
                        <s:link beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" event="delete" class="btn btn-danger"><s:param name="car.id" value="${car.id}"/><f:message key="common.delete"/></s:link>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </s:layout-component>
</s:layout-render>