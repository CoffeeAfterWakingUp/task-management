$(document).ready(function () {
    $("#favoriteBoardForm").on("submit", function (event) {
        event.preventDefault();
        let boardId = $("#boardId").val();
        let userId = $("#userId").val();
        console.log(boardId);
        let dto = {
            boardId: boardId
        };

        $.ajax({
            url: '/api/v1/users/' + userId + '/boards/favorites',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function(response) {
                $("#favoriteBoardBtnImg").attr("src", "/svg/");
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