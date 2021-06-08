"use strict";
$(document).ready(function () {
    $('[type="reset"]').click(function () {
        setTimeout(function () {
            $("input").iCheck("update");
        }, 10);
    });
    //summernote JS
    $('.summernote').summernote({
        height: 200,
        width: 500
    });

    //icheck js

    $('input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_minimal-green',
        radioClass: 'iradio_minimal-green'
    });
    //table js
    $('input[type=reset]').on('click', function () {
        $(".note-editable").empty();
        $('#courseschedule_form').bootstrapValidator("resetForm");
    });

    $('#courseschedule_form').bootstrapValidator({

        fields: {
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
                        message: 'The time is required'
                    }

                }
            },
            time_to: {
                validators: {
                    notEmpty: {
                        message: 'The time is required'
                    }
                }
            },
            course: {
                validators: {
                    notEmpty: {
                        message: 'The course is required'
                    }
                }
            },
            room: {
                validators: {
                    notEmpty: {
                        message: 'The room is required'
                    }
                }
            },
            trainer: {
                validators: {
                    notEmpty: {
                        message: 'The trainer is required'
                    }
                }
            }
        }
    });

    function row(fTable, frow) {
        var fData = fTable.fnGetData(frow);
        var ftable = $('>td', frow);

        for (var i = 0, iLen = ftable.length; i < iLen; i++) {
            fTable.fnUpdate(fData[i], frow, i, false);
        }

        fTable.fnDraw();
    }

    function editRow(fTable, frow) {
        var fData = fTable.fnGetData(frow);
        var ftable = $('>td', frow);
        ftable[0].innerHTML = '<input type="text" class="form-control input-small" value="' + fData['course'] + '">';
        ftable[1].innerHTML = '<input type="text" class="form-control input-small" value="' + fData['trainer'] + '">';
        ftable[2].innerHTML = '<input type="text" class="form-control input-small" value="' + fData['room'] + '">';
        ftable[3].innerHTML = '<input type="text" class="form-control input-small" value="' + fData['time_from'] + '">';
        ftable[4].innerHTML = '<input type="text" class="form-control input-small" value="' + fData['time_to'] + '">';
        ftable[5].innerHTML = '<a class="edit btn btn-success mar-bm" href="">Save</a>';
        ftable[6].innerHTML = '<a class="cancel btn btn-danger mar-bm" href="">Cancel</a>';
    }

    function saveRow(fTable, frow) {
        var jqInputs = $('input', frow);
        fTable.fnUpdate(jqInputs[0].value, frow, 0, false);
        fTable.fnUpdate(jqInputs[1].value, frow, 1, false);
        fTable.fnUpdate(jqInputs[2].value, frow, 2, false);
        fTable.fnUpdate(jqInputs[3].value, frow, 3, false);
        fTable.fnUpdate(jqInputs[4].value, frow, 4, false);
        fTable.fnUpdate('<a class="edit btn btn-primary mar-bm" href=""><i class="fa fa-fw fa-edit"></i> Edit</a>', frow, 5, false);
        fTable.fnUpdate('<a class="delete btn btn-danger mar-bm" href=""><i class="fa fa-trash-o"></i> Delete</a>', frow, 6, false);
        fTable.fnDraw();
    }

    function cancelfitnessEditRow(fTable, frow) {
        var jqInputs = $('input', frow);
        fTable.fnUpdate(jqInputs[0].value, frow, 0, false);
        fTable.fnUpdate(jqInputs[1].value, frow, 1, false);
        fTable.fnUpdate(jqInputs[2].value, frow, 2, false);
        fTable.fnUpdate(jqInputs[3].value, frow, 3, false);
        fTable.fnUpdate(jqInputs[4].value, frow, 4, false);
        fTable.fnUpdate('<a class="edit btn btn-primary mar-bm" href=""><i class="fa fa-fw fa-edit"></i>    Edit</a>', frow, 5, false);
        fTable.fnDraw();
    }

    var table = $('.table1');

    var fTable = table.dataTable({
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"]
        ],
        // set the initial value
        "pageLength": 5,
        ajax: {
            url: '/api/course_schedule/th?th=Monday',
            contentType: 'application/json',
            dataType: 'json',
            dataSrc: ''
        },
        rowId: 'id',
        columns: [
            {data: 'course'},
            {data: 'trainer'},
            {data: 'room'},
            {data: 'time_from'},
            {data: 'time_to'},
            {
                "data": null,
                "defaultContent": "<a class=\"edit btn btn-primary\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-fw fa-edit\"></i> Edit\n" + "</a>"
            },
            {
                "data": null,
                "defaultContent": "<a class=\"delete btn btn-danger\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-trash-o\"></i> Delete\n" + "</a>"
            }
        ],
        select: true
    });


    var FitnessEditing = null;
    var FitnesNew = false;

    $('#table_new').click(function (e) {
        e.preventDefault();

        if (FitnesNew && FitnessEditing) {

            fTable.fnDeleteRow(FitnessEditing);
            FitnessEditing = null;
            FitnesNew = false;
            return;
        }

        var aiNew = fTable.fnAddData(['', '', '', '', '', '']);
        var frow = fTable.fnGetNodes(aiNew[0]);
        editRow(fTable, frow);
        FitnessEditing = frow;
        FitnesNew = true;
    });
    table.on('click', '.delete', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        swal({
            title: "Delete?",
            text: "Are you sure want to delete this row",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes",
            confirmButtonColor: "#33a4d8",
            cancelButtonColor: "#fc7070",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: false

        }).then(function () {
            $.ajax({
                url: '/api/course_schedule/delete/' + frow.id,
                contentType: 'application/json',
                dataType: 'json text',
                type: 'DELETE',
                success: function (data) {
                    $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                    $('#user_data').DataTable().destroy();
                    fTable.fnDeleteRow(frow);
                    fetch_data();
                    location.reload();
                },
                error: function (error) {
                    location.reload();
                }
            });
        });

    });
    table.on('click', '.cancel', function (e) {
        e.preventDefault();

        if (FitnesNew) {
            fTable.fnDeleteRow(FitnessEditing);
            FitnesNew = false;
        } else {
            row(fTable, FitnessEditing);
            FitnessEditing = null;
        }
        location.reload();
    });

    table.on('click', '.edit', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        if (FitnessEditing !== null && FitnessEditing != frow) {
            row(fTable, FitnessEditing);
            editRow(fTable, frow);
            FitnessEditing = frow;

        } else if (FitnessEditing == frow && this.innerHTML == "Save") {
            var jqInputs = $('input', FitnessEditing);
            var id = FitnessEditing.id;
            var obj = {
                id: id,
                course: jqInputs[0].value,
                trainer: jqInputs[1].value,
                day: "Monday",
                room: jqInputs[2].value,
                time_from: jqInputs[3].value,
                time_to: jqInputs[4].value
            };
            var jsonData = JSON.stringify(obj);
            $.ajax({
                type: "PUT",
                url: "/api/course_schedule/edit/" + id,
                contentType: "application/json",
                dataType: "json",
                data: jsonData.toString()
            });
            saveRow(fTable, FitnessEditing);
            FitnessEditing = null;
            swal({
                title: "Updated.",
                text: "Successfully Saved",
                type: "success",
                allowOutsideClick: false
            })
        } else {
            editRow(fTable, frow);
            FitnessEditing = frow;
        }
    });

    //table 2
    var table = $('.table2');

    var fTable1 = table.dataTable({
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"]
        ],
        // set the initial value
        "pageLength": 5,
        ajax: {
            url: '/api/course_schedule/th?th=Tuesday',
            contentType: 'application/json',
            dataType: 'json',
            dataSrc: ''
        },
        rowId: 'id',
        columns: [
            {data: 'course'},
            {data: 'trainer'},
            {data: 'room'},
            {data: 'time_from'},
            {data: 'time_to'},
            {
                "data": null,
                "defaultContent": "<a class=\"edit btn btn-primary\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-fw fa-edit\"></i> Edit\n" + "</a>"
            },
            {
                "data": null,
                "defaultContent": "<a class=\"delete btn btn-danger\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-trash-o\"></i> Delete\n" + "</a>"
            }
        ],
        select: true
    });


    var FitnessEditing1 = null;
    var FitnesNew1 = false;

    $('#table_new1').on('click', function (f) {
        f.preventDefault();

        if (FitnesNew1 && FitnessEditing1) {

            fTable1.fnDeleteRow(FitnessEditing1);
            FitnessEditing1 = null;
            FitnesNew1 = false;
            return;
        }

        var aiNew = fTable1.fnAddData(['', '', '', '', '', '']);
        var frow = fTable1.fnGetNodes(aiNew[0]);
        editRow(fTable1, frow);
        FitnessEditing1 = frow;
        FitnesNew1 = true;
    });
    table.on('click', '.delete', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        swal({
            title: "Delete?",
            text: "Are you sure want to delete this row",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes",
            confirmButtonColor: "#33a4d8",
            cancelButtonColor: "#fc7070",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: false

        }).then(function () {
            $.ajax({
                url: '/api/course_schedule/delete/' + frow.id,
                contentType: 'application/json',
                dataType: 'json text',
                type: 'DELETE',
                success: function (data) {
                    $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                    $('#user_data').DataTable().destroy();
                    fTable.fnDeleteRow(frow);
                    fetch_data();
                    location.reload();
                },
                error: function (error) {
                    location.reload();
                }
            });
        });

    });


    table.on('click', '.cancel', function (f) {
        f.preventDefault();

        if (FitnesNew1) {
            fTable1.fnDeleteRow(FitnessEditing1);
            FitnesNew1 = false;
        } else {
            row(fTable1, FitnessEditing1);
            FitnessEditing1 = null;
        }
        location.reload();
    });

    table.on('click', '.edit', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        if (FitnessEditing !== null && FitnessEditing != frow) {
            row(fTable, FitnessEditing);
            editRow(fTable, frow);
            FitnessEditing = frow;
        } else if (FitnessEditing == frow && this.innerHTML == "Save") {
            var jqInputs = $('input', FitnessEditing);
            var id = FitnessEditing.id;
            var obj = {
                id: id,day: "Tuesday",
                course: jqInputs[0].value,
                trainer: jqInputs[1].value,
                room: jqInputs[2].value,
                time_from: jqInputs[3].value,
                time_to: jqInputs[4].value
            };
            var jsonData = JSON.stringify(obj);
            $.ajax({
                type: "PUT",
                url: "/api/course_schedule/edit/" + id,
                contentType: "application/json",
                dataType: "json",
                data: jsonData.toString()
            });
            saveRow(fTable, FitnessEditing);
            FitnessEditing = null;
            swal({
                title: "Updated.",
                text: "Successfully Saved",
                type: "success",
                allowOutsideClick: false
            })
        } else {
            editRow(fTable, frow);
            FitnessEditing = frow;
        }
    });

    //table 3
    var table = $('.table3');

    var fTable2 = table.dataTable({
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"]
        ],
        // set the initial value
        "pageLength": 5,
        ajax: {
            url: '/api/course_schedule/th?th=Wednesday',
            contentType: 'application/json',
            dataType: 'json',
            dataSrc: ''
        },
        rowId: 'id',
        columns: [
            {data: 'course'},
            {data: 'trainer'},
            {data: 'room'},
            {data: 'time_from'},
            {data: 'time_to'},
            {
                "data": null,
                "defaultContent": "<a class=\"edit btn btn-primary\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-fw fa-edit\"></i> Edit\n" + "</a>"
            },
            {
                "data": null,
                "defaultContent": "<a class=\"delete btn btn-danger\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-trash-o\"></i> Delete\n" + "</a>"
            }
        ],
        select: true


    });


    var FitnessEditing2 = null;
    var FitnesNew2 = false;

    $('#table_new2').click(function (h) {
        h.preventDefault();

        if (FitnesNew2 && FitnessEditing2) {

            fTable2.fnDeleteRow(FitnessEditing2);
            FitnessEditing2 = null;
            FitnesNew2 = false;
            return;
        }

        var aiNew = fTable2.fnAddData(['', '', '', '', '', '']);
        var frow = fTable2.fnGetNodes(aiNew[0]);
        editRow(fTable2, frow);
        FitnessEditing2 = frow;
        FitnesNew2 = true;
    });
    table.on('click', '.delete', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        swal({
            title: "Delete?",
            text: "Are you sure want to delete this row",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes",
            confirmButtonColor: "#33a4d8",
            cancelButtonColor: "#fc7070",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: false

        }).then(function () {
            $.ajax({
                url: '/api/course_schedule/delete/' + frow.id,
                contentType: 'application/json',
                dataType: 'json text',
                type: 'DELETE',
                success: function (data) {
                    $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                    $('#user_data').DataTable().destroy();
                    fTable.fnDeleteRow(frow);
                    fetch_data();
                    location.reload();
                },
                error: function (error) {
                    location.reload();
                }
            });
        });

    });


    table.on('click', '.cancel', function (h) {
        e.preventDefault();

        if (FitnesNew2) {
            fTable2.fnDeleteRow(FitnessEditing2);
            FitnesNew2 = false;
        } else {
            row(fTable2, FitnessEditing2);
            FitnessEditing2 = null;
        }
        location.reload();
    });

    table.on('click', '.edit', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        if (FitnessEditing !== null && FitnessEditing != frow) {
            row(fTable, FitnessEditing);
            editRow(fTable, frow);
            FitnessEditing = frow;
            // console.log(row)

        } else if (FitnessEditing == frow && this.innerHTML == "Save") {
            var jqInputs = $('input', FitnessEditing);
            var id = FitnessEditing.id;
            var obj = {
                id: id,day: "Wednesday",
                course: jqInputs[0].value,
                trainer: jqInputs[1].value,
                room: jqInputs[2].value,
                time_from: jqInputs[3].value,
                time_to: jqInputs[4].value
            };
            var jsonData = JSON.stringify(obj);
            $.ajax({
                type: "PUT",
                url: "/api/course_schedule/edit/" + id,
                contentType: "application/json",
                dataType: "json",
                data: jsonData.toString()
            });
            saveRow(fTable, FitnessEditing);
            FitnessEditing = null;
            swal({
                title: "Updated.",
                text: "Successfully Saved",
                type: "success",
                allowOutsideClick: false
            })
        } else {
            editRow(fTable, frow);
            FitnessEditing = frow;
        }
    });


    //table 4
    var table = $('.table4');

    var fTable3 = table.dataTable({
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"]
        ],
        // set the initial value
        "pageLength": 5,
        ajax: {
            url: '/api/course_schedule/th?th=Thursday',
            contentType: 'application/json',
            dataType: 'json',
            dataSrc: ''
        },
        rowId: 'id',
        columns: [
            {data: 'course'},
            {data: 'trainer'},
            {data: 'room'},
            {data: 'time_from'},
            {data: 'time_to'},
            {
                "data": null,
                "defaultContent": "<a class=\"edit btn btn-primary\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-fw fa-edit\"></i> Edit\n" + "</a>"
            },
            {
                "data": null,
                "defaultContent": "<a class=\"delete btn btn-danger\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-trash-o\"></i> Delete\n" + "</a>"
            }
        ],
        select: true


    });


    var FitnessEditing3 = null;
    var FitnesNew3 = false;

    $('#table_new3').on('click', function (h) {
        h.preventDefault();

        if (FitnesNew3 && FitnessEditing3) {

            fTable3.fnDeleteRow(FitnessEditing3);
            FitnessEditing3 = null;
            FitnesNew3 = false;
            return;
        }

        var aiNew = fTable3.fnAddData(['', '', '', '', '', '']);
        var frow = fTable3.fnGetNodes(aiNew[0]);
        editRow(fTable3, frow);
        FitnessEditing3 = frow;
        FitnesNew3 = true;
    });
    table.on('click', '.delete', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        swal({
            title: "Delete?",
            text: "Are you sure want to delete this row",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes",
            confirmButtonColor: "#33a4d8",
            cancelButtonColor: "#fc7070",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: false

        }).then(function () {
            $.ajax({
                url: '/api/course_schedule/delete/' + frow.id,
                contentType: 'application/json',
                dataType: 'json text',
                type: 'DELETE',
                success: function (data) {
                    $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                    $('#user_data').DataTable().destroy();
                    fTable.fnDeleteRow(frow);
                    fetch_data();
                    location.reload();
                },
                error: function (error) {
                    location.reload();
                }
            });
        });

    });


    table.on('click', '.cancel', function (h) {
        e.preventDefault();

        if (FitnesNew3) {
            fTable3.fnDeleteRow(FitnessEditing3);
            FitnesNew3 = false;
        } else {
            row(fTable3, FitnessEditing3);
            FitnessEditing3 = null;
        }
        location.reload();
    });

    table.on('click', '.edit', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        if (FitnessEditing !== null && FitnessEditing != frow) {
            row(fTable, FitnessEditing);
            editRow(fTable, frow);
            FitnessEditing = frow;
            // console.log(row)

        } else if (FitnessEditing == frow && this.innerHTML == "Save") {
            var jqInputs = $('input', FitnessEditing);
            var id = FitnessEditing.id;
            var obj = {
                id: id,day: "Thursday",
                course: jqInputs[0].value,
                trainer: jqInputs[1].value,
                room: jqInputs[2].value,
                time_from: jqInputs[3].value,
                time_to: jqInputs[4].value
            };
            var jsonData = JSON.stringify(obj);
            $.ajax({
                type: "PUT",
                url: "/api/course_schedule/edit/" + id,
                contentType: "application/json",
                dataType: "json",
                data: jsonData.toString()
            });
            saveRow(fTable, FitnessEditing);
            FitnessEditing = null;
            swal({
                title: "Updated.",
                text: "Successfully Saved",
                type: "success",
                allowOutsideClick: false
            })
        } else {
            editRow(fTable, frow);
            FitnessEditing = frow;
        }
    });

    //table 5

    var table = $('.table5');

    var fTable4 = table.dataTable({
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"]
        ],
        // set the initial value
        "pageLength": 5,
        ajax: {
            url: '/api/course_schedule/th?th=Friday',
            contentType: 'application/json',
            dataType: 'json',
            dataSrc: ''
        },
        rowId: 'id',
        columns: [
            {data: 'course'},
            {data: 'trainer'},
            {data: 'room'},
            {data: 'time_from'},
            {data: 'time_to'},
            {
                "data": null,
                "defaultContent": "<a class=\"edit btn btn-primary\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-fw fa-edit\"></i> Edit\n" + "</a>"
            },
            {
                "data": null,
                "defaultContent": "<a class=\"delete btn btn-danger\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-trash-o\"></i> Delete\n" + "</a>"
            }
        ],
        select: true


    });


    var FitnessEditing4 = null;
    var FitnesNew4 = false;

    $('#table_new4').click(function (i) {
        i.preventDefault();

        if (FitnesNew4 && FitnessEditing4) {

            fTable4.fnDeleteRow(FitnessEditing4);
            FitnessEditing4 = null;
            FitnesNew4 = false;
            return;
        }

        var aiNew = fTable4.fnAddData(['', '', '', '', '', '']);
        var frow = fTable4.fnGetNodes(aiNew[0]);
        editRow(fTable4, frow);
        FitnessEditing4 = frow;
        FitnesNew4 = true;
    });
    table.on('click', '.delete', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        swal({
            title: "Delete?",
            text: "Are you sure want to delete this row",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes",
            confirmButtonColor: "#33a4d8",
            cancelButtonColor: "#fc7070",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: false

        }).then(function () {
            $.ajax({
                url: '/api/course_schedule/delete/' + frow.id,
                contentType: 'application/json',
                dataType: 'json text',
                type: 'DELETE',
                success: function (data) {
                    $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                    $('#user_data').DataTable().destroy();
                    fTable.fnDeleteRow(frow);
                    fetch_data();
                    location.reload();
                },
                error: function (error) {
                    location.reload();
                }
            });
        });

    });


    table.on('click', '.cancel', function (i) {
        i.preventDefault();

        if (FitnesNew4) {
            fTable4.fnDeleteRow(FitnessEditing4);
            FitnesNew4 = false;
        } else {
            row(fTable4, FitnessEditing4);
            FitnessEditing4 = null;
        }
        location.reload();
    });

    table.on('click', '.edit', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        if (FitnessEditing !== null && FitnessEditing != frow) {
            row(fTable, FitnessEditing);
            editRow(fTable, frow);
            FitnessEditing = frow;
            // console.log(row)

        } else if (FitnessEditing == frow && this.innerHTML == "Save") {
            var jqInputs = $('input', FitnessEditing);
            var id = FitnessEditing.id;
            var obj = {
                id: id,day: "Friday",
                course: jqInputs[0].value,
                trainer: jqInputs[1].value,
                room: jqInputs[2].value,
                time_from: jqInputs[3].value,
                time_to: jqInputs[4].value
            };
            var jsonData = JSON.stringify(obj);
            $.ajax({
                type: "PUT",
                url: "/api/course_schedule/edit/" + id,
                contentType: "application/json",
                dataType: "json",
                data: jsonData.toString()
            });
            saveRow(fTable, FitnessEditing);
            FitnessEditing = null;
            swal({
                title: "Updated.",
                text: "Successfully Saved",
                type: "success",
                allowOutsideClick: false
            })
        } else {
            editRow(fTable, frow);
            FitnessEditing = frow;
        }
    });

    //table 6

    var table = $('.table6');

    var fTable5 = table.dataTable({
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"]
        ],
        // set the initial value
        "pageLength": 5,
        ajax: {
            url: '/api/course_schedule/th?th=Saturday',
            contentType: 'application/json',
            dataType: 'json',
            dataSrc: ''
        },
        rowId: 'id',
        columns: [
            {data: 'course'},
            {data: 'trainer'},
            {data: 'room'},
            {data: 'time_from'},
            {data: 'time_to'},
            {
                "data": null,
                "defaultContent": "<a class=\"edit btn btn-primary\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-fw fa-edit\"></i> Edit\n" + "</a>"
            },
            {
                "data": null,
                "defaultContent": "<a class=\"delete btn btn-danger\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-trash-o\"></i> Delete\n" + "</a>"
            }
        ],
        select: true


    });


    var FitnessEditing5 = null;
    var FitnesNew5 = false;

    $('#table_new5').on('click', function (j) {
        j.preventDefault();

        if (FitnesNew5 && FitnessEditing5) {

            fTable5.fnDeleteRow(FitnessEditing5);
            FitnessEditing5 = null;
            FitnesNew5 = false;
            return;
        }

        var aiNew = fTable5.fnAddData(['', '', '', '', '', '']);
        var frow = fTable5.fnGetNodes(aiNew[0]);
        editRow(fTable5, frow);
        FitnessEditing5 = frow;
        FitnesNew5 = true;
    });
    table.on('click', '.delete', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        swal({
            title: "Delete?",
            text: "Are you sure want to delete this row",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes",
            confirmButtonColor: "#33a4d8",
            cancelButtonColor: "#fc7070",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: false

        }).then(function () {
            $.ajax({
                url: '/api/course_schedule/delete/' + frow.id,
                contentType: 'application/json',
                dataType: 'json text',
                type: 'DELETE',
                success: function (data) {
                    $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                    $('#user_data').DataTable().destroy();
                    fTable.fnDeleteRow(frow);
                    fetch_data();
                    location.reload();
                },
                error: function (error) {
                    location.reload();
                }
            });
        });

    });


    table.on('click', '.cancel', function (j) {
        j.preventDefault();

        if (FitnesNew5) {
            fTable5.fnDeleteRow(FitnessEditing5);
            FitnesNew5 = false;
        } else {
            row(fTable5, FitnessEditing5);
            FitnessEditing5 = null;
        }
        location.reload();
    });

    table.on('click', '.edit', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        if (FitnessEditing !== null && FitnessEditing != frow) {
            row(fTable, FitnessEditing);
            editRow(fTable, frow);
            FitnessEditing = frow;
            // console.log(row)

        } else if (FitnessEditing == frow && this.innerHTML == "Save") {
            var jqInputs = $('input', FitnessEditing);
            var id = FitnessEditing.id;
            var obj = {
                id: id,day: "Saturday",
                course: jqInputs[0].value,
                trainer: jqInputs[1].value,
                room: jqInputs[2].value,
                time_from: jqInputs[3].value,
                time_to: jqInputs[4].value
            };
            var jsonData = JSON.stringify(obj);
            $.ajax({
                type: "PUT",
                url: "/api/course_schedule/edit/" + id,
                contentType: "application/json",
                dataType: "json",
                data: jsonData.toString()
            });
            saveRow(fTable, FitnessEditing);
            FitnessEditing = null;
            swal({
                title: "Updated.",
                text: "Successfully Saved",
                type: "success",
                allowOutsideClick: false
            })
        } else {
            editRow(fTable, frow);
            FitnessEditing = frow;
        }
    });

    //table 7
    var table = $('.table7');

    var fTable6 = table.dataTable({
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"]
        ],
        // set the initial value
        "pageLength": 5,
        ajax: {
            url: '/api/course_schedule/th?th=Sunday',
            contentType: 'application/json',
            dataType: 'json',
            dataSrc: ''
        },
        rowId: 'id',
        columns: [
            {data: 'course'},
            {data: 'trainer'},
            {data: 'room'},
            {data: 'time_from'},
            {data: 'time_to'},
            {
                "data": null,
                "defaultContent": "<a class=\"edit btn btn-primary\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-fw fa-edit\"></i> Edit\n" + "</a>"
            },
            {
                "data": null,
                "defaultContent": "<a class=\"delete btn btn-danger\" href=\"javascript:;\">\n" +
                    "<i class=\"fa fa-trash-o\"></i> Delete\n" + "</a>"
            }
        ],
        select: true


    });


    var FitnessEditing6 = null;
    var FitnesNew6 = false;

    $('#table_new6').on('click', function (i) {
        i.preventDefault();

        if (FitnesNew6 && FitnessEditing6) {

            fTable6.fnDeleteRow(FitnessEditing6);
            FitnessEditing6 = null;
            FitnesNew6 = false;
            return;
        }

        var aiNew = fTable6.fnAddData(['', '', '', '', '', '']);
        var frow = fTable6.fnGetNodes(aiNew[0]);
        editRow(fTable6, frow);
        FitnessEditing6 = frow;
        FitnesNew6 = true;
    });
    table.on('click', '.delete', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        swal({
            title: "Delete?",
            text: "Are you sure want to delete this row",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes",
            confirmButtonColor: "#33a4d8",
            cancelButtonColor: "#fc7070",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: false

        }).then(function () {
            $.ajax({
                url: '/api/course_schedule/delete/' + frow.id,
                contentType: 'application/json',
                dataType: 'json text',
                type: 'DELETE',
                success: function (data) {
                    $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                    $('#user_data').DataTable().destroy();
                    fTable.fnDeleteRow(frow);
                    fetch_data();
                    location.reload();
                },
                error: function (error) {
                    location.reload();
                }
            });
        });

    });


    table.on('click', '.cancel', function (i) {
        i.preventDefault();

        if (FitnesNew6) {
            fTable6.fnDeleteRow(FitnessEditing6);
            FitnesNew6 = false;
        } else {
            row(fTable6, FitnessEditing6);
            FitnessEditing6 = null;
        }
        location.reload();
    });

    table.on('click', '.edit', function (e) {
        e.preventDefault();
        var frow = $(this).parent().parent('tr')[0];
        if (FitnessEditing !== null && FitnessEditing != frow) {
            row(fTable, FitnessEditing);
            editRow(fTable, frow);
            FitnessEditing = frow;
            // console.log(row)

        } else if (FitnessEditing == frow && this.innerHTML == "Save") {
            var jqInputs = $('input', FitnessEditing);
            var id = FitnessEditing.id;
            var obj = {
                id: id,day: "Sunday",
                course: jqInputs[0].value,
                trainer: jqInputs[1].value,
                room: jqInputs[2].value,
                time_from: jqInputs[3].value,
                time_to: jqInputs[4].value
            };
            var jsonData = JSON.stringify(obj);
            $.ajax({
                type: "PUT",
                url: "/api/course_schedule/edit/" + id,
                contentType: "application/json",
                dataType: "json",
                data: jsonData.toString()
            });
            saveRow(fTable, FitnessEditing);
            FitnessEditing = null;
            swal({
                title: "Updated.",
                text: "Successfully Saved",
                type: "success",
                allowOutsideClick: false
            })
        } else {
            editRow(fTable, frow);
            FitnessEditing = frow;
        }
    });


});
