"use strict";
//icheck js

$(document).ready(function () {

    $('[type="reset"]').click(function () {
        setTimeout(function () {
            $("input").iCheck("update");
        }, 10);
    });
    $('#trainer').bootstrapValidator({

        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: 'Name is required'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'Phone number is required'
                    },
                    regexp: {
                        regexp: /^[0-9]{10,15}$/, //TODO!!
                        message: 'Enter valid phone number'
                    }
                }
            },
            dob: {
                validators: {
                    notEmpty: {
                        message: 'Date of birth is required'
                    },
                    date: {
                        format: 'YYYY/MM/DD',
                        message: 'The value is not a valid date'
                    },
                    stringLength: {
                        min: 10,
                        max: 10,
                        message: 'Date of birth is correctly 10 characters'
                    }
                }
            }
        }
    })
});


