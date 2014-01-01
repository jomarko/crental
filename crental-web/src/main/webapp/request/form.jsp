<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
        <div class="row form-group">
                <div class="col-lg-2">
                    <s:label for="request.dateFrom" name="request.dateFrom"/>
                </div>
                <div class="col-lg-6 ">
                    <s:text id="datepickerFrom" name="request.dateFrom" class="form-control datepicker"/>
                </div>
                <div class="col-lg-4"/>
            </div>
        </div>
        <div class="form-group">
            <div class="row form-group">
                <div class="col-lg-2">
                    <s:label for="request.dateTo" name="request.dateTo"/>
                </div>
                <div class="col-lg-6 ">
                    <s:text id="datepickerTo" name="request.dateTo" class="form-control datepicker"/>
                </div>
                
                <div class="col-lg-4"/>
            </div>
        </div>
          
        <div class="form-group">
            <div class="row form-group">
                <div class="col-lg-2">
                    <s:label for="request.description" name="request.description" />       
                </div>
                <div class="col-lg-6">
                    <s:text id="request.description" name="request.description" class="form-control"/>
                </div>
                <div class="col-lg-4"/>
            </div>
        </div>  
    
