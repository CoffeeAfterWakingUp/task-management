$(document).ready(function () {
    $("#deleteTaskForm").on("submit", function (event) {
        event.preventDefault();

        let taskId = $("#taskId").val();
        console.log(taskId)

        $.ajax({
            url: '/api/v1/tasks/' + taskId,
            type: 'DELETE',
            contentType: 'application/json',
            success: function(response) {
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspaces/' + $("#workspaceId").val() + '/boards/' + $("#boardId").val();
            },
            error: function(error) {
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});