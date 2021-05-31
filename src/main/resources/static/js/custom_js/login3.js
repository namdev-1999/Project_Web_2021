"use strict";
$(document).ready(function() {

    $("#reset_password").bootstrapValidator({
        excluded: [':disabled'],

        fields: {
            re_password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
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
                    },
                    identical: {
                        field: 're_password',
                        message: 'The password and its confirm are not the same'
                    }
                }
            }
        }
    });
});