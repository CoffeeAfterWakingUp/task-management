$(document).ready(function () {
    $("#addPersonForm").on("submit", function (event) {
        event.preventDefault();
        let dto = {
            username: $("#personUsername").val(),
            userRole: $("#personRole").val()
        };

        $("#addPersonFormMsg").hide();
        $("#addPersonBtn").html("Loading...");

        $.ajax({
            url: '/api/v1/workspaces/' + $("#workspaceId").val() + '/members',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function (response) {
                $("#addPersonBtn").html("Добавить пользователя");
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspaces/' + $("#workspaceId").val() + '/members';
            },
            error: function (error) {
                $("#addPersonFormMsg").html(error.responseJSON.errorMsg);
                $("#addPersonFormMsg").show();
                $("#addPersonBtn").html("Добавить пользователя");
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});