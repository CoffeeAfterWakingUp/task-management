$(document).ready(function () {
    $("#editWorkspaceNameForm").on("submit", function (event) {
        event.preventDefault();
        let workspaceId = $("#workspaceId").val();
        let newWorkspaceName = $("#newWorkspaceName").val();
        let dto = {
            newWorkspaceName: newWorkspaceName
        };

        $("#editWorkspaceNameMsg").hide();

        $.ajax({
            url: '/api/v1/workspaces/' + workspaceId + '/workspace-name',
            type: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function(response) {
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspaces/' + workspaceId + '/settings';
            },
            error: function(error) {
                $("#editWorkspaceNameMsg").html(error.responseJSON.errorMsg);
                $("#editWorkspaceNameMsg").show();
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});