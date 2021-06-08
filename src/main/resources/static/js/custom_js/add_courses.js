"use strict";
//icheck js

$(document).ready(function () {

    $('[type="reset"]').click(function () {
        setTimeout(function () {
            $("input").iCheck("update");
        }, 10);
    });
    $('#course_form').bootstrapValidator({

        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: 'Course name is required'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: 'Course description is required'
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        message: 'Course price is required'
                    },
                    regexp: {
                        regexp: /^\d{0,8}(\,\d{1,4})?$/, //TODO!!
                        message: 'Enter valid price number'
                    }
                }
            },
            start_date: {
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
                        message: 'Start date is correctly 10 characters'
                    }
                }
            },
            end_date: {
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
                        message: 'End date is correctly 10 characters'
                    }
                }
            },
            id_trainer: {
                validators: {
                    notEmpty: {
                        message: 'ID Trainer is required'
                    },
                    remote: {
                        url: '/api/id_trainer/',
                        message: 'The id invalid. Please enter other id',
                    }
                }
            },
        }
    })
});


