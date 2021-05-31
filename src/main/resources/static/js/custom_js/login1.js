"use strict";
$(document).ready(function() {
    $("input[type='checkbox']").iCheck({
        checkboxClass: 'icheckbox_minimal-yellow',
        radioClass: 'iradio_minimal-yellow'

    });
    $("#log_in").bootstrapValidator({
        excluded: [':disabled'],

        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email address is required and cannot be empty'
                    },
                    emailAddress: {
                        message: 'The email address is not a valid'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },
                    regexp: {
                        regexp: /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z0-9@$!%*#?&]{8,}$/,
                        message: 'The password must have at least 8 characters, at least one letter, one number and one special character'
                    },
                    stringLength: {
                        min: 8
                    }
                }
            }
        }
    });
});