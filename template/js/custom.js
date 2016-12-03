$(function() {
    Materialize.fadeInImage('img');
    
    /* Responsive Menu */
    $(".button-collapse").sideNav();
    
    /* Collapsable Episodes in Series */
    $('.collapsible').collapsible();
    
    /* Serie Cards */
    $('.chips-placeholder').material_chip({
        placeholder:            '+Tag',
        secondaryPlaceholder:   'Restrict series to'
    })
    $('#tag-search').append("<i class='material-icons'>search</i>");
    
    /**
     ** ################## User Action ################## */
    $('.chips').on('chip.add', function(e, chip){
        Materialize.toast(chip['tag'] + " what a wonderful idea", 4000);
    });

    $('.chips').on('chip.delete', function(e, chip){
        Materialize.toast(chip['tag'] + " removed", 4000);
    });
});