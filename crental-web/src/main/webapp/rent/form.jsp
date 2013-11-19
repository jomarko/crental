<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<%--
START:update
    <s:form beanclass="stripesbook.action.ContactFormActionBean">
END:update
--%>
<html>
    <body>
    <s:form beanclass="cz.muni.fi.pompe.crental.web.RentActionBean">


      <div><s:hidden name="rent.id"/></div>

      <table class="form">

        <tr>
          <td>Request:</td>
          <td><s:text name="rent.request"/></td><!-- (2) -->
        </tr>
        <tr>
          <td>ConfirmedBy:</td>
          <td><s:text name="rent.ConfirmedBy"/></td>
        </tr>
        <tr>
          <td>ConfirmedAt:</td>
          <td><s:text name="rent.confirmedAt"/></td>
        </tr>
        <tr>
          <td>RentedCar:</td>
          <td><s:text name="rent.rentedCar"/></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <s:submit name="save" value="Save"/><!-- (3) -->
            <s:submit name="cancel" value="Cancel"/>
          </td>
        </tr>
      </table>
    </s:form>
    </body>
</html>
