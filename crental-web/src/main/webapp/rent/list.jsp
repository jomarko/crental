<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:form beanclass="cz.muni.fi.pompe.crental.web.RentActionBean">
<html>
    <head>
        Rents
    </head>
    <body>
        <table>
            <tr>
                <th>Request</th>
                <th>ConfiremdAt</th>
                <th>ConfirmedBy</th>
                <th>RentedCar</th>
            </tr>
            <c:forEach var="rent" items="${actionBean.rents}">
                <tr>
                    <td>${rent.request}</td>
                    <td>${rent.confirmedAt}</td>
                    <td>${rent.confirmedBy}</td>
                    <td>${rent.rentedCar}</td>
                    <td>
                        <s:link beanclass="cz.muni.fi.pompe.crental.web.RentActionBean" event="edit"><s:param name="rent.id" value="${rent.id}"/>edit</s:link>
                    </td>
                    <td>
                        <s:hidden name="rent.id" value="${rent.id}"/>
                        <s:submit name="delete" value="delete"/>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
        <%@include file="form.jsp"%>
    </body>
</html>
</s:form>

