function startLoader() {
    $(".loader").each(function () {
        $(this).css("display", "block");
    });
}

function stopLoader() {
    $(".loader").each(function () {
        $(this).css("display", "none");
    });
}

function applyUnit(base) {
    if (confirm("Are you sure?")) {
        var ids = [];
        $("#tbl-available").find(">tbody>tr>td>input:checked").each(
                function () {
                    var elemId = $(this).parent().parent().attr("id");
                    var arr = elemId.split("-");
                    var id = arr[arr.length - 1];
                    ids.push(id);
                });
        if (ids.length > 0) {
            console.log(ids);

            var url = base + "applicationStatus/apply";
            startLoader();
            $.ajax({
                url: url,
                method: "POST",
                data: JSON.stringify(ids),
                dataType: "json",
                contentType: 'application/json; charset=utf-8',
                statusCode: {
                    200: function (response) {
                        //TODO: Show success tooltip
                        location.reload();
                        stopLoader();
                    }
                },
                success: function (result, status, xhr) {
                    console.log("result: " + result);
                    console.log("status: " + status);
                    console.log("code: " + xhr.status);
                },
                error: function (xhr) {
                    //TODO: Show error tooltip
                },
                complete: function () {
                    stopLoader();
                }
            });
        }
    }
}

function deleteUnit(base, unitId, historyId) {
    if (confirm("Are you sure?")) {
        var url = base + "applicationStatus/delete/" + historyId;
        startLoader();
        $.ajax({
            url: url,
            method: "DELETE",
            statusCode: {
                200: function (response) {
                    //TODO: Show success tooltip
                    addRowToTable(unitId, historyId);
                }
            },
            success: function (result, status, xhr) {
                console.log("result: " + result);
                console.log("status: " + status);
                console.log("code: " + xhr.status);
            },
            error: function (xhr) {
                //TODO: Show error tooltip
            },
            complete: function () {
                stopLoader();
            }
        });
    }
}

function addRowToTable(unitId, historyId) {
    var row = $("#tbl-apl-row-" + historyId);
    var unit = row.children('td:nth-child(1)').html();
    var name = row.children('td:nth-child(2)').html();
    var fees = row.children('td:nth-child(3)').html()
    row.remove();

    $("#tbl-available").find("tbody")
    .append($("<tr>")
            .attr("id", "tbl-avl-row-" + unitId)
            .append($("<td>")
            .append($("<input>").attr("type", "checkbox"))
            .append($("<span>").text(" " + unit)))
            .append($("<td>").text(name))
            .append($("<td>").text(fees))
    );
}