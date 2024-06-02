$(document).ready(function () {
    $("#addCardForm").on("submit", function (event) {
        event.preventDefault();
        let boardId = $("#boardId").val();
        let workspaceId = $("#workspaceId").val();
        console.log(workspaceId);
        let dto = {
            boardId: boardId,
            title: $("#cardName").val()
        };

        $("#addCardFormMsg").hide();

        $.ajax({
            url: '/api/v1/cards',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dto),
            success: function (response) {
                console.log('Data sent successfully:', response.body);
                window.location.href = '/workspaces/' + workspaceId + '/boards/' + boardId;
            },
            error: function (error) {
                $("#addCardFormMsg").html(error.responseJSON.errorMsg);
                $("#addCardFormMsg").show();
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});