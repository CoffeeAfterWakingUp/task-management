$(document).ready(function () {
    $("#createBoardForm").on("submit", function (event) {
        event.preventDefault();
        let workspaceId = $("#workspaceId").val();
        console.log(workspaceId);
        let dto = {
            title: $("#boardName").val(),
            description: $("#boardDescription").val(),
            boardVisibility: $("#boardVisibility").val(),
            workspaceId: workspaceId
        };

        $("#msg").hide();
        $("#createBoardBtn").html("Loading...");

        $.ajax({
            url: '/api/v1/boards',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function(response) {
                $("#createWorkspaceBtn").html("Создать");
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspaces/' + workspaceId + '/boards';
            },
            error: function(error) {
                $("#msg").html(error.responseJSON.errorMsg);
                $("#msg").show();
                $("#createBoardBtn").html("Создать");
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});