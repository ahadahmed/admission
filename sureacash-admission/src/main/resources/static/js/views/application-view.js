
function btnApplyClicked() {
    var ids = [];
    $("#tbl-available").find(">tbody>tr>td>input:checked").each(function () {
        var elemId = $(this).parent().parent().attr("id");
        var arr = elemId.split("-");
        var id = arr[arr.length - 1];
        ids.push(id);
    });
    if (ids.length > 0) {
        console.log(ids);

        //TODO: call service
        location.reload();
    }
}

function deleteUnit(unitId, historyId) {
    if (confirm("Are you sure?")) {
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

        //TODO: Call service with ID = elNum
    }
}