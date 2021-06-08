"use strict";
//icheck js

$(document).ready(function () {

    $('#receipt_form').bootstrapValidator({

        fields: {
            title: {
                validators: {
                    notEmpty: {
                        message: 'Invoice title is required'
                    }
                }
            },
            contain: {
                validators: {
                    notEmpty: {
                        message: 'Invoice contain is required'
                    }
                }
            },
            total: {
                validators: {
                    notEmpty: {
                        message: 'Invoice total is required'
                    }
                }
            },

            date: {
                validators: {
                    notEmpty: {
                        message: 'Invoice date is required'
                    },
                    date: {
                        format: 'YYYY/MM/DD',
                        message: 'The value is not a valid date'
                    },
                    stringLength: {
                        min: 10,
                        max: 10,
                        message: 'Invoice date is correctly 10 characters'
                    }
                }
            },
            id_customer: {
                validators: {
                    notEmpty: {
                        message: 'ID Customer is required'
                    },
                    remote: {
                        url: '/api/id_customer/',
                        message: 'The id invalid. Please enter other id',
                    }
                }
            },
        }
    })
});


