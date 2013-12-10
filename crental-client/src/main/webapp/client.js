var rc = {
    addEmployee: function(a, b, c){
        console.log('addEmployee',a, b, c);
    }
    , deleteEmployee: function(a, b, c){
        console.log('deleteEmployee',a, b, c);
    }
};

$(document).ready(function(){
    $("#employee").flexigrid({
	url: 'http://localhost:8080/pa165/rest/car/all',
	dataType: 'json',
        method: 'get',
//        crossDomain: true,
	colModel : [
		{display: 'ID', name : 'id', width : 40, align: 'center'},
		{display: 'Typ vozu', name : 'carType', align: 'center'},
		{display: 'SPZ', name : 'evidencePlate',  align: 'left'},
		],
	searchitems : [
                {display: 'SPZ', name : 'evidencePlate'},
		{display: 'ID', name : 'id'},
		{display: 'Typ vozu', name : 'carType'},
		],
        buttons : [
		{name: 'Add', bclass: 'add', onpress : rc.addEmployee},
		{name: 'Delete', bclass: 'delete', onpress : rc.addEmployee},
		{separator: true}
		],
	title: 'Vozidla',
//	onSubmit: rc.addEmployee,
});

});


