<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="car.list.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" var="actionBean"/>
        <p class="text-right"><s:link beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" event="edit"><f:message key="car.create"/></s:link></p>
        <table class="table">
            <tr>
                <th>id</th>
                <th><f:message key="car.carType"/></th>
                <th><f:message key="car.evidencePlate"/></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${actionBean.cars}" var="car">
                <tr>
                    <td>${car.id}</td>
                    <td><c:out value="${car.carType}"/></td>
                    <td><c:out value="${car.evidencePlate}"/></td>
                    <td>
                     <s:link beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" event="edit"><s:param name="car.id" value="${car.id}"/><f:message key="common.edit"/></s:link>
                    </td>
                    <td>
                        <s:form beanclass="cz.muni.fi.pompe.crental.web.CarActionBean">
                            <s:hidden name="car.id" value="${car.id}"/>
                            <s:submit name="delete"><f:message key="common.delete"/></s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </s:layout-component>
</s:layout-render>