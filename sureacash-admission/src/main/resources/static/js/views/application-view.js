
function deleteUnit(elNum) {
    if (confirm("Are you sure?")) {
        var row = $("#tbl-apl-row-" + elNum);
        var unit = row.children('td:nth-child(1)').html();
        var name = row.children('td:nth-child(2)').html();
        var fees = row.children('td:nth-child(3)').html()
        row.remove();

        $("#tbl-available").find("tbody")
        .append($("<tr>")
                .append($("<td>")
                    .append($("<input>").attr("type", "checkbox"))
                    .append($("<span>").text(" " + unit)))
                .append($("<td>").text(name))
                .append($("<td>").text(fees))
        );

        //TODO: Call service with ID = elNum
    }
}