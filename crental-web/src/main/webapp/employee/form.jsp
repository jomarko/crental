<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div class="form-group">
            <div class="row form-group">
                <div class="col-lg-2">
                    <s:label for="employee.name" name="employee.name"/>
                </div>
                <div class="col-lg-6 ">
                    <s:text id="employee.name" name="employee.name" class="form-control"/>
                </div>
                <div class="col-lg-4"/>
            </div>
        </div>
        <div class="form-group">
            <div class="row form-group">
                <div class="col-lg-2">
                    <s:label for="employee.password" name="employee.password"/>
                </div>
                <div class="col-lg-6 ">
                    <s:password id="employee.password" name="employee.password" class="form-control"/>
                </div>
                
                <div class="col-lg-4"/>
            </div>
        </div>
        <div class="form-group">
            <div class="row form-group">
                <div class="col-lg-2">
                    <s:label for="employee.accessRight" name="employee.accessRight" />       
                </div>
                <div class="col-lg-6">
                    <s:select id="employee.accessRight" name="employee.accessRight" class="form-control"><s:options-enumeration enum="cz.muni.fi.pompe.crental.dto.AccessRight"/></s:select>
                </div>
                <div class="col-lg-4"/>
            </div>
        </div>  
    
