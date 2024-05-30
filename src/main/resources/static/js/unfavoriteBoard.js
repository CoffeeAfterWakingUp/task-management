$(document).ready(function () {
    $("#unfavoriteBoardForm").on("submit", function (event) {
        event.preventDefault();
        let boardId = $("#boardId").val();
        let username = $("#username").val();
        let dto = {
            boardId: boardId
        };

        $.ajax({
            url: '/api/v1/users/' + username + '/boards/favorites',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function(response) {
                $("#favoriteBoardForm").css("display", "block");
                $("#unfavoriteBoardForm").css("display", "none");
                console.log('Data sent successfully:', response.body);
            },
            error: function(error) {
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});