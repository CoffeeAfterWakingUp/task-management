$(document).ready(function () {
    $("#addPersonToBoardForm").on("submit", function (event) {
        event.preventDefault();
        let workspaceId = $("#workspaceId").val();
        let boardId = $("#boardId").val();

        let dto = {
            member: $("#addPersonToBoardMember").val(),
            memberRole: $("#addPersonToBoardMemberRole").val()
        };

        $("#addPersonToBoardFormMsg").hide();


        $.ajax({
            url: '/api/v1/boards/' + boardId + '/members',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function(response) {
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspaces/' + workspaceId + '/boards/' + boardId;
            },
            error: function(error) {
                $("#addPersonToBoardFormMsg").html(error.responseJSON.errorMsg);
                $("#addPersonToBoardFormMsg").show();
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});