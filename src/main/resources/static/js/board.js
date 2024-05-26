$(document).ready(function () {
    $(".tasks").sortable({
        connectWith: ".tasks",
        receive: function( event, ui ) {
            console.log(event);
            console.log(ui);
        }
    });
});

$(document).ready(function () {
    $(".row").sortable({
        receive: function( event, ui ) {
            console.log(event);
            console.log(ui);
        }
    });
});