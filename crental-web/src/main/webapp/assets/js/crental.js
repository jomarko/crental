
$(function() {
    var $datepicker = $( "input.datepicker" );
    $datepicker.datepicker({
        minDate: +1
    });     
    
    $('.btn-danger').on('click', function() {
       return confirm(cr.i18n('common.deleteConfirm')); 
    });
});