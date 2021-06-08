"use strict";
$(document).ready(function () {
    //summernote JS
    $('.summernote').summernote({
        height: 200,
        width: 500
    });

    $.ajax({
        url: '/api/course_schedule',
        contentType: 'application/json',
        dataType: 'json',
        type: 'GET',
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                var curData = data[i];
                let description = curData["description"] == null ? "No description" : curData["description"];
                let trSelector = "#time" + parseInt(curData["time_from"].split(":")[0]);
                let tdSelector = "." + curData["day"].substring(0, 3);
                $(trSelector + " " + tdSelector).html(
                    '<a href="#" title=' + description + '>' + curData["time_from"] + ' - ' + curData["time_to"] + '<br><b>' + curData["course"] + '</b></a>'
                ).addClass("inline").on('click', function (e) {
                    e.preventDefault();
                    swal({
                        title: "Course Calendar",
                        text: description,
                        allowOutsideClick: false,
                        confirmButtonColor: "#fc7070",
                        confirmButtonText: "Cancel"
                    })
                })
                console.log(data[i])
            }
        },
    })


    //package reset functionality
    $('input[type=reset]').on('click', function () {
        $(".note-editable").empty();
        $('#timings_form').bootstrapValidator("resetForm");
    });

    $('#datetimepicker4').datetimepicker({

        keepOpen: false,
        useCurrent: false,
        minDate: new Date().setHours(0, 0, 0, 0)
    });
    $('#datetimepicker5').datetimepicker({

        keepOpen: false,
        useCurrent: false,
        minDate: new Date()
    });

    $('#datetimepicker4').on("dp.change", function (e) {
        $('#datetimepicker5').data("DateTimePicker").minDate(e.date);
        $('#timings_form').bootstrapValidator('revalidateField', 'time_from');
    });

    $('#datetimepicker5').on("dp.change", function (e) {
        $('#datetimepicker4').data("DateTimePicker").maxDate(e.date);
        $('#timings_form').bootstrapValidator('revalidateField', 'time_to');

    });
    $('#timings_form').bootstrapValidator({

        fields: {
            title: {
                validators: {
                    notEmpty: {
                        message: 'The title is required'
                    }
                }
            },
            category: {
                validators: {
                    notEmpty: {
                        message: 'The category is required'
                    }

                }
            },
            day: {
                validators: {
                    notEmpty: {
                        message: 'The day is required'
                    }
                }
            },
            time_from: {
                validators: {

                    notEmpty: {
                        message: 'The start time is required'
                    }

                }

            },
            time_to: {
                validators: {
                    notEmpty: {
                        message: 'The end time is required'
                    }
                }
            }

        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        swal({
            title: "Success.",
            text: "Successfully Submitted",
            type: "success",
            allowOutsideClick: false

        }).then(function () {

            location.reload();

        });
    })


});
