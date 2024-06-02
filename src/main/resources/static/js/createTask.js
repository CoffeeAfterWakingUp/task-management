$(document).ready(function () {
    $("#createTaskModal").on('show.bs.modal', function(event) {
        let button = $(event.relatedTarget);
        let cardId = button.data('card-id');
        $("#cardId").val(cardId);
        console.log(cardId);
    });

    $("#createTaskForm").on("submit", function (event) {
        event.preventDefault();
        let dto = {
            taskTitle: $("#taskTitle").val(),
            taskDescription: $("#taskDescription").val(),
            worker: $("#worker").val(),
            priority: $("#priority").val(),
            deadlineTime: $("#deadlineTime").val(),
            cardId: $("#cardId").val(),
            author: $("#username").val()
        };

        $("#createTaskFormMsg").hide();

        $.ajax({
            url: '/api/v1/tasks',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function (response) {
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspaces/' + $("#workspaceId").val() + '/boards/' + $("#boardId").val();
            },
            error: function (error) {
                $("#createTaskFormMsg").html(error.responseJSON.errorMsg);
                $("#createTaskFormMsg").show();
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });

});