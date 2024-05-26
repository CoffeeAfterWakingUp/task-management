$(document).ready(function () {
    $("#createWorkspaceForm").on("submit", function (event) {
        event.preventDefault();
        let dto = {
            title: $("#workspaceName").val(),
            description: $("#workspaceInfo").val()
        };

        $("#msg").hide();
        $("#createWorkspaceBtn").html("Loading...");

        $.ajax({
            url: '/api/v1/workspaces',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function(response) {
                $("#workspaceName").val("");
                $("#workspaceInfo").val("");
                $("#createWorkspaceBtn").html("Создать");
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspace';
            },
            error: function(error) {
                $("#msg").html(error.responseJSON.errorMsg);
                $("#msg").show();
                $("#createWorkspaceBtn").html("Создать");
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});