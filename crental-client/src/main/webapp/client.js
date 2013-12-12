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
            errors.push("Pole 'Popis' je povinné");
        }
        
        if (values.carType.length == 0) {
            errors.push("Pole 'SPZ' je povinné");
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
            error: function() {alert('Záznam s id="'+id+'" nepodařilo odstranit');},
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
            error: function() {alert('Záznam se nepodařilo modifikovat');},
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
            error: function() {alert('Záznam se nepodařilo vytvořit');},
            timeout: 5000,
            type: 'POST'
        });
    },
    
    employeeSave: function($dialog) {
        var values = rc.arrayToObject($dialog.find('form').serializeArray());
        var errors = new Array();
        
        if (values.name.length == 0) {
            errors.push("Pole 'Jméno' je povinné");
        }
        
        if (values.password.length == 0) {
            errors.push("Pole 'Heslo' je povinné");
        }
        
        if (values.accessRight.length == 0) {
            errors.push("Pole 'Přístupová práva' je povinné");
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
		{display: 'Popis', name : 'carType', align: 'center'},
		{display: 'SPZ', name : 'evidencePlate',  align: 'left'},
		],
        buttons : [
		{name: 'Nový vůz', bclass: 'add', onpress : rc.addCar},
		{name: 'Odstranit', bclass: 'delete', onpress : rc.deleteCar},
		],
	title: 'Vozidla',
    });
    
    rc.employeeTable = $("#employee").flexigrid({
	url: rc.restUrl + 'employee/all',
	dataType: 'json',
        method: 'get',
        onDoubleClick: rc.employeeEdit,
	colModel : [
		{display: 'ID', name : 'id', width : 40, align: 'center'},
		{display: 'Jméno', name : 'name', align: 'center'},
		{display: 'Heslo', name : 'password',  align: 'left'},
		{display: 'Přístupová práva', name : 'accessRight',  align: 'left'},
		],
        buttons : [
		{name: 'Nový zaměstnanec', bclass: 'add', onpress : rc.addEmployee},
		{name: 'Odstranit', bclass: 'delete', onpress : rc.deleteEmployee},
		],
	title: 'Zaměstnanci',
    });
    
    rc.carDialog = $("#car-form").dialog({
      autoOpen: false,
      height: 300,
      width: 350,
      modal: true,
      buttons: {
        "Uložit": function() {
            rc.carSave($(this));
        },
        'Zrušit': function() {
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
      width: 350,
      modal: true,
      buttons: {
        "Uložit": function() {
            rc.employeeSave($(this));
        },
        'Zrušit': function() {
          $(this).dialog( "close" );
        }
      },
      close: function() {
        $(this).find("input").val("");
      }
    });
});


