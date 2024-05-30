$(document).ready(function () {
    $("#addPersonForm").on("submit", function (event) {
        event.preventDefault();
        let dto = {
            username: $("#personUsername").val(),
            userRole: $("#personRole").val()
        };

        $("#msg").hide();
        $("#addPersonBtn").html("Loading...");

        $.ajax({
            url: '/api/v1/workspaces/' + $("#workspaceId").val() + '/members',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function (response) {
                $("#createWorkspaceBtn").html("Добавить пользователя");
                console.log('Data sent successfully:', response.body);
                // window.location.href = '/workspaces/' + response.body.id + '/boards';
            },
            error: function (error) {
                $("#msg").html(error.responseJSON.errorMsg);
                $("#msg").show();
                $("#addPersonBtn").html("Добавить пользователя");
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});