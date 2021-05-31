$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/users/auth",
        contentType: "application/json",
        success: function (response) {
            $('.avt').attr('src', response.avatar)
            $('.name').text(response.name);
            $('.role').text(response.role);
        }
    });
});

