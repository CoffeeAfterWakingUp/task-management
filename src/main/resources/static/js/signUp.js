$(document).ready(function () {
    $("#registerForm").on("submit", function (event) {
        event.preventDefault();
        let registerDto = {
            fullName: $("#fullName").val(),
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            rePassword: $("#password2").val()
        };

        $("#msg").hide();
        $("#signUp").html("Loading...");

        $.ajax({
            url: '/api/v1/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(registerDto),
            success: function(response) {
                $("#fullName").val("");
                $("#username").val("");
                $("#email").val("");
                $("#password").val("");
                $("#password2").val("");
                $("#msg").html(response.body).css("color", "green");
                $("#msg").show();
                $("#signUp").html("Sign up");
                console.log('Data sent successfully:', response.body);

                setTimeout(function() {
                    window.location.href = '/login';
                }, 2000);
            },
            error: function(error) {
                $("#msg").html(error.responseJSON.errorMsg + "");
                $("#msg").show();
                $("#signUp").html("Sign up");
                console.error('Error sending data:', error.responseJSON);
            }
        });

    });
});