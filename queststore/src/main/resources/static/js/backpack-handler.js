var STATUS_INDEX = 2;
var BUTTON_INDEX = 3;

var table = document.getElementsByTagName("table")[0];

for (var i = 0, row; row = table.rows[i]; i++) {
    if (row.cells[STATUS_INDEX].innerHTML === "New") {
        table.rows.item(i).cells.item(BUTTON_INDEX).childNodes[0].disabled = false;
    }
}