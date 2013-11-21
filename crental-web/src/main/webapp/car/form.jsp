<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="car.create">
    <s:layout-component name="body">
    <s:errors/>
    <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" var="actionBean"/>
    <c:set var="insertUpdateTitle" value="${empty actionBean.car.getId() ? 'car.create':'car.edit'}"/>
    <s:form beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" action="/car/save">
        <fieldset><legend><f:message key="${insertUpdateTitle}" /></legend>
            <div class="row form-group">
                <s:label for="car.carType" name="car.carType"  class="col-sm-2 control-label"/>
                <div class="col-sm-10">
                    <s:text id="car.carType" name="car.carType" class="form-control" />
                </div>
            </div>
                
            <div class="row form-group">
                <s:label for="car.evidencePlate" name="car.evidencePlate" class="col-sm-2 control-label"/>
                <div class="col-sm-4">
                    <s:text id="car.evidencePlate" name="car.evidencePlate" class="form-control" />
                </div>
            </div>
                
            <s:hidden name="car.id"/>
            <s:submit name="save" class="btn btn-success"><f:message key="common.save"/></s:submit>
            <s:link beanclass="cz.muni.fi.pompe.crental.web.CarActionBean" event="list" class="btn btn-warning"><f:message key="common.cancel"/></s:link>
        </fieldset>
    </s:form>
    </s:layout-component>
</s:layout-render>