$(document).ready(function () {
    $(".tasks").sortable({
        connectWith: ".tasks",
        receive: function( event, ui ) {
            console.log(event);
            console.log(ui);

            let taskId = ui.item[0].dataset.taskId;
            let cardId = ui.item[0].offsetParent.dataset.cardId;
            let dto = {
                cardId: cardId
            };
            $.ajax({
                url: '/api/v1/tasks/' + taskId + '/card',
                type: 'PATCH',
                contentType: 'application/json',
                data: JSON.stringify(dto),
                success: function (response) {
                    console.log('Data sent successfully:', response.body);
                },
                error: function (error) {
                    console.error('Error sending data:', error.responseJSON);
                }
            });
        }
    });
    $(".scroll").sortable({
        stop: function( event, ui ) {
            let cardIds = [];
            let boardId = $("#boardId").val();
            $('.card').each(function() {
                console.log($(this).data('cardId') + ' ' + $(this).data('cardOrder'));
                cardIds.push($(this).data('cardId'));
            });
            let dto = {
                boardId: boardId,
                cardIds: cardIds
            };
            console.log(cardIds);
            $.ajax({
                url: '/api/v1/boards/' + boardId + '/track-cards-orders',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(dto),
                success: function (response) {
                    console.log('Data sent successfully:', response.body);
                },
                error: function (error) {
                    $("#addCardFormMsg").html(error.responseJSON.errorMsg);
                    $("#addCardFormMsg").show();
                    console.error('Error sending data:', error.responseJSON);
                }
            });

        }
    });
});