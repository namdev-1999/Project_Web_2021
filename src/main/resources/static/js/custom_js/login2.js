"use strict";
$(document).ready(function() {
    $("#forgot_password").bootstrapValidator({
        excluded: [':disabled'],

        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email address is required and cannot be empty'
                    },
                    emailAddress: {
                        message: 'The email address is invalid'
                    }
                }
            }
        }
    });
});