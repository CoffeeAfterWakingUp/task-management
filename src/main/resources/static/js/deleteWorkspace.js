$(document).ready(function () {
    $("#deleteWorkspaceForm").on("submit", function (event) {
        event.preventDefault();
        let workspaceId = $("#workspaceId").val();


        $.ajax({
            url: '/api/v1/workspaces/' + workspaceId,
            type: 'DELETE',
            contentType: 'application/json',
            success: function(response) {
                console.log('Data sent successfully:', response.body);
                // window.location.href = '/workspaces/' + workspaceId + '/settings';
            },
            error: function(error) {
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});