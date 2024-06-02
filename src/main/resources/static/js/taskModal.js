$(document).ready(function () {
    $("#taskModal").on('show.bs.modal', function(event) {
        let button = $(event.relatedTarget);
        let taskId = button.data('task-id');
        console.log(taskId);

        $.ajax({
            url: '/api/v1/tasks/' + taskId,
            type: 'GET',
            contentType: 'application/json',
            success: function(response) {
                let task = response.body;
                console.log('Data sent successfully:', task);
                $("#taskId").val(taskId);
                $("#taskModalTitle").html(task.title);
                $("#taskDesc").html(task.description);
                $("#taskAuthor").html(task.author);
                $("#taskWorker").html(task.worker);
                $("#taskDeadlineTime").html(task.deadlineTime);
                $("#taskCreatedTime").html(task.createdTime);
                $("#taskPriority").html(task.priority);
                if ($("#username").val() === $("#taskAuthor").html || $("#curUserBoardRole").val() === 'BOARD_ADMIN')  {
                    $("#taskDetails").css("display", "block");
                }
            },
            error: function(error) {
                console.error('Error sending data:', error.responseJSON);
            }
        });
    });
});