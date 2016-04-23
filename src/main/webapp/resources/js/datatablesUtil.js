function makeEditable() {
    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#filter').submit(function () {
        updateTable();
        return false;
    });

    $('.checkbox').click(function () {
       activateUser($(this).attr("id"), $(this).is(':checked'));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function activateUser(id, enabled) {
    $.ajax({
        url: ajaxUrl + id + '/' + enabled,
        type: "POST",
        success: function () {
            updateTable();
        }
    })
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    if ($('#startDate').val() != null && $('#endDate').val() != null &&
        $('#startTime').val() != null && $('#endTime').val() != null) {
        filter();
    } else {
        $.get(ajaxUrl, function (data) {
            datatableApi.fnClearTable();
            $.each(data, function (key, item) {
                datatableApi.fnAddData(item);
            });
            datatableApi.fnDraw();
        });
    }
}

function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

function filter() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: {
            startDate: $('#startDate').val(),
            endDate: $('#endDate').val(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val()
        },
        dataType: 'json',
        success: function (data) {
            datatableApi.fnClearTable();
            $.each(data, function (key, item) {
                datatableApi.fnAddData(item);
            });
            datatableApi.fnDraw();
        }
    })
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
