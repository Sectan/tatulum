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
        console.log("Chip added: ", chip["tag"]);
    });

    $('.chips').on('chip.delete', function(e, chip){
        console.log("Chip removed: ", chip["tag"]);
    });

    $('.chips').on('chip.select', function(e, chip){
        console.log("Chip selected: ", chip["tag"]);
    });
});