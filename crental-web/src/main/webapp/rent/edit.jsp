<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="rent.approveTitle">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pompe.crental.web.RentActionBean" var="actionBean"/>
            <div class="page-header">
                <h3 id="grid"><f:message key="rent.approveTitle" /></h3>
            </div>
        
            <div class="row form-group">
                <div class="col-lg-2">
                    <f:message key="request.employeeId"/>
                </div>
                <div class="col-lg-6 ">
                    <c:out value="${actionBean.employee.name}"/>
                </div>
            </div>
            <div class="row form-group">
                <div class="col-lg-2">
                    <f:message key="request.dateFrom"/>
                </div>
                <div class="col-lg-6 ">
                    <c:out value="${actionBean.request.dateFrom}"/>
                </div>
            </div>
            <div class="row form-group">
                <div class="col-lg-2">
                    <f:message key="request.dateTo"/>
                </div>
                <div class="col-lg-6 ">
                    <c:out value="${actionBean.request.dateTo}"/>
                </div>
            </div>
            
            <div class="row form-group">
                <div class="col-lg-2">
                    <f:message key="request.description"/>
                </div>
                <div class="col-lg-6 ">
                    <c:out value="${actionBean.request.description}"/>
                </div>
            </div>
            <s:form beanclass="cz.muni.fi.pompe.crental.web.RentActionBean" action="/rent/save" method="post">
                <s:errors/>
                <div class="form-group">
                    <div class="row form-group">
                        <div class="col-lg-2">
                            <s:label for="rent.confirmedById" name="rent.confirmedById"/>
                        </div>
                        <div class="col-lg-6 ">
                            <s:select id="rent.confirmedById" name="rent.confirmedById" class="form-control">
                            <c:forEach items="${actionBean.adminMap.keySet()}" var="adminId">
                                <c:set var="admin" value="${actionBean.adminMap.get(adminId)}" />
                                <s:option value="${admin.id}" label="${admin.name}" />
                            </c:forEach>
                            </s:select>
                        </div>
                    </div>
                </div>
                            
                <div class="form-group">
                    <div class="row form-group">
                        <div class="col-lg-2">
                            <s:label for="rent.rentedCarId" name="rent.rentedCarId"/>
                        </div>
                        <div class="col-lg-6 ">
                            <s:select id="rent.rentedCarId" name="rent.rentedCarId" class="form-control">
                            <c:forEach items="${actionBean.carMap.keySet()}" var="carId">
                                <c:set var="car" value="${actionBean.carMap.get(carId)}"/>
                                <s:option value="${car.id}" label="${car.carType} - ${car.evidencePlate}"/>
                            </c:forEach>
                            </s:select>
                        </div>
                    </div>
                </div>
                
                <s:hidden name="rent.id"/>    
                <s:hidden name="rent.requestId"/>    
                <s:submit class="btn btn-success" name="save" value="save"><f:message key="request.approve"/></s:submit>
                <s:link beanclass="cz.muni.fi.pompe.crental.web.RequestActionBean" event="list" class="btn btn-warning"><f:message key="common.cancel"/></s:link>
            </s:form>
    </s:layout-component>
</s:layout-render>