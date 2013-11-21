
$(function() {
    var $datepicker = $( "input.datepicker" );
    $datepicker.datepicker();     
    
    $('.btn-danger').on('click', function() {
       return confirm(cr.i18n('common.deleteConfirm')); 
    });
});