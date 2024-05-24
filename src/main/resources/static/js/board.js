$(document).ready(function () {
    $(".tasks").sortable({
        connectWith: ".tasks",
        receive: function( event, ui ) {
            ui.item.css("color", "red");
            console.log(event);
            console.log(ui);
        }
    });
});

$(document).ready(function () {
    $(".row").sortable({
        receive: function( event, ui ) {
            ui.item.css("color", "red");
            console.log(event);
            console.log(ui);
        }
    });
});