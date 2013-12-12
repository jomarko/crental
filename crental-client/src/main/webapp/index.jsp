<%-- 
    Document   : index
    Created on : Dec 12, 2013, 9:06:32 PM
    Author     : jozef
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<f:setBundle basename="ClientResources"/>
<f:message key="button.cancel" var="buttonCancel"/>
<f:message key="button.save" var="buttonSave"/>
<!DOCTYPE html>
<html>
    <head>
        <title>AJAX client</title>
        <link rel="stylesheet" href="client.css" />
        <link rel="stylesheet" href="jquery-ui-1.10.3.custom.min.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="jquery-ui-1.10.3.custom.min.js"></script>
        <script type="text/javascript" src="grid.js"></script>
        <script type="text/javascript" >
            var rc = {
            carTable: null,
            employeeTable: null,
            restUrl: 'http://localhost:8080/pa165/rest/',

            arrayToObject: function(arr) {
                var ret = {};

                for (var i=0; i < arr.length; i++) {
                    ret[arr[i].name] = arr[i].value;
                }

                return ret;
            },

            carSave: function($dialog) {
                var values = rc.arrayToObject($dialog.find('form').serializeArray());
                var errors = new Array();

                if (values.carType.length == 0) {
                    errors.push("'<f:message key="car.description"/>' <f:message key="mandatory"/>");
                }

                if (values.carType.length == 0) {
                    errors.push("'<f:message key="car.plate"/>' <f:message key="mandatory"/>");
                }

                if (errors.length == 0) {
                    if (values.id.length == 0) {
                        rc.apiCreate('car',values, rc.carSaved);
                    } else {
                        rc.apiUpdate('car',values, rc.carSaved);
                    }

                    $dialog.dialog("close");
                } else {
                    alert(errors.join("\n"));
                }
            },

            carEdit: function(rowEl,row,grid) {
                var $row = $(rowEl);
                var data = row.rawData[$row.data('id')];

                var $form = rc.carDialog.find('form');

                for (var input in data) {
                    $form.find('#car_'+input).val(data[input]);
                }

                rc.carDialog.dialog("open");
            },

            carSaved: function() {
                rc.carTable.flexReload();
            },

            addCar: function(a, b, c){
                rc.carDialog.dialog("open");
            }
            , deleteCar: function(a, b, c){
                rc.carTable.find('.trSelected').each(function(i, val){
                    var $row = $(val);
                    rc.apiDelete('car', $row.data('id'), function(){
                        $row.remove();
                    });
                });
            }, 

            apiDelete: function(uri, id, success) {
                $.ajax(rc.restUrl + uri + '/delete/' + id, {
                    cache: false,
                    success: success,
                    error: function() {alert('ID: '+id+' <f:message key="record.delete"/>');},
                    timeout: 5000,
                    type: 'DELETE'
                });
            },

            apiUpdate: function(uri,values, success) {
                $.ajax(rc.restUrl + uri + '/put/' + values.id, {
                    cache: false,
                    success: success,
                    contentType: "application/json",
                    data: JSON.stringify(values),
                    error: function() {alert('<f:message key="record.modify"/>');},
                    timeout: 5000,
                    type: 'PUT'
                });
            },

            apiCreate: function(uri, data, success) {
                data.id = null;
                $.ajax(rc.restUrl + uri, {
                    cache: false,
                    success: success,
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    error: function() {alert('<f:message key="record.create"/>');},
                    timeout: 5000,
                    type: 'POST'
                });
            },

            employeeSave: function($dialog) {
                var values = rc.arrayToObject($dialog.find('form').serializeArray());
                var errors = new Array();

                if (values.name.length == 0) {
                    errors.push("'<f:message key="employee.name"/>' <f:message key="mandatory"/>");
                }

                if (values.password.length == 0) {
                    errors.push("'<f:message key="employee.password"/>' <f:message key="mandatory"/>");
                }

                if (values.accessRight.length == 0) {
                    errors.push("'<f:message key="employee.access"/>' <f:message key="mandatory"/>");
                }

                if (errors.length == 0) {
                    if (values.id.length == 0) {
                        rc.apiCreate('employee',values, rc.employeeSaved);
                    } else {
                        rc.apiUpdate('employee',values, rc.employeeSaved);
                    }

                    $dialog.dialog("close");
                } else {
                    alert(errors.join("\n"));
                }
            },

            employeeEdit: function(rowEl,row,grid) {
                var $row = $(rowEl);
                var data = row.rawData[$row.data('id')];

                var $form = rc.employeeDialog.find('form');

                for (var input in data) {
                    $form.find('#employee_'+input).val(data[input]);
                }

                rc.employeeDialog.dialog("open");
            },

            employeeSaved: function() {
                rc.employeeTable.flexReload();
            },

            addEmployee: function(a, b, c){
                rc.employeeDialog.dialog("open");
            }
            , deleteEmployee: function(a, b, c){
                rc.employeeTable.find('.trSelected').each(function(i, val){
                    var $row = $(val);
                    rc.apiDelete('employee', $row.data('id'), function(){
                        $row.remove();
                    });
                });
            }
        };

        $(document).ready(function(){
            rc.carTable = $("#car").flexigrid({
                url: rc.restUrl + 'car/all',
                dataType: 'json',
                method: 'get',
                onDoubleClick: rc.carEdit,
                colModel : [
                        {display: 'ID', name : 'id', width : 40, align: 'center'},
                        {display: '<f:message key="car.description"/>', name : 'carType', align: 'center'},
                        {display: '<f:message key="car.plate"/>', name : 'evidencePlate',  align: 'left'},
                        ],
                buttons : [
                        {name: '<f:message key="car.new"/>', bclass: 'add', onpress : rc.addCar},
                        {name: '<f:message key="button.delete"/>', bclass: 'delete', onpress : rc.deleteCar},
                        ],
                title: '<f:message key="cars"/>',
            });

            rc.employeeTable = $("#employee").flexigrid({
                url: rc.restUrl + 'employee/all',
                dataType: 'json',
                method: 'get',
                onDoubleClick: rc.employeeEdit,
                colModel : [
                        {display: 'ID', name : 'id', width : 40, align: 'center'},
                        {display: '<f:message key="employee.name"/>', name : 'name', align: 'center'},
                        {display: '<f:message key="employee.password"/>', name : 'password',  align: 'left'},
                        {display: '<f:message key="employee.access"/>', name : 'accessRight',  align: 'left'},
                        ],
                buttons : [
                        {name: '<f:message key="employee.new"/>', bclass: 'add', onpress : rc.addEmployee},
                        {name: '<f:message key="button.delete"/>', bclass: 'delete', onpress : rc.deleteEmployee},
                        ],
                title: '<f:message key="employees"/>',
            });

            rc.carDialog = $("#car-form").dialog({
              autoOpen: false,
              height: 300,
              width: 350,
              modal: true,
              buttons: {
                "${buttonSave}": function() {
                    rc.carSave($(this));
                },
                '${buttonCancel}': function() {
                  $(this).dialog( "close" );
                }
              },
              close: function() {
                $(this).find("input").val("");
              }
            });

            rc.employeeDialog = $("#employee-form").dialog({
              autoOpen: false,
              height: 400,
              width: 300,
              modal: true,
              buttons: {
                "${buttonSave}": function() {
                    rc.employeeSave($(this));
                },
                '${buttonCancel}': function() {
                  $(this).dialog( "close" );
                }
              },
              close: function() {
                $(this).find("input").val("");
              }
            });
        });


        </script>
    </head>
    <body>
        <h1>Crental REST client</h1>
        <p><f:message key="instructions"/></p>
        <div id="employee"></div>
        <div id="car"></div>
        
        <div id="car-form" title="<f:message key="car"/>">
            <p class="validateTips"><f:message key="form.mandatory.all"/></p>

            <form>
                <fieldset>
                    <label for="car_carType"><f:message key="car.description"/></label>
                    <input type="text" name="carType" id="car_carType" class="text ui-widget-content ui-corner-all">
                    <label for="car_evidencePlate"><f:message key="car.plate"/></label>
                    <input type="text" name="evidencePlate" id="car_evidencePlate" value="" class="text ui-widget-content ui-corner-all">
                    <input type="hidden" name="id" id="car_id" value="">
                </fieldset>
            </form>
        </div>
        <div id="employee-form" title="<f:message key="employee"/>">
            <p class="validateTips"><f:message key="form.mandatory.all"/></p>

            <form>
                <fieldset>
                    <label for="employee_name"><f:message key="employee.name"/></label>
                    <input type="text" name="name" id="employee_name" class="text ui-widget-content ui-corner-all">
                    <label for="employee_password"><f:message key="employee.password"/></label>
                    <input type="password" name="password" id="employee_password" value="" class="text ui-widget-content ui-corner-all">
                    <label for="employee_accessRight"><f:message key="employee.access"/></label>
                    <select name="accessRight" id="employee_accessRight">
                        <option value="Employee"><f:message key="employee"/></option>
                        <option value="Admin"><f:message key="admin"/></option>
                    </select>
                    
                    <input type="hidden" name="id" id="employee_id" value="">
                </fieldset>
            </form>
        </div>
    </body>
</html>
