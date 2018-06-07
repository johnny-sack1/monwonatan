var input = document.getElementsByTagName("input");
var events = ["keydown", "keyup", "keypressed"];
for (i = 0; i < input.length; i++) {
    for (j = 0; j < events.length; j++) {
        var index = i;
        input[i].addEventListener(events[j], function (event) {disableButtonOnEmpty();});
    }
}

function disableButtonOnEmpty() {

    var BUTTON_INDEX = 5;
    var table = document.getElementsByTagName("table")[0];

    for (var i = 1; i < table.rows.length; i++) {
        var cells_ = table.rows.item(i).cells;
        if (noEmptyFields(cells_)) {
            cells_.item(BUTTON_INDEX).getElementsByTagName("button")[0].disabled = false;
        } else {
            cells_.item(BUTTON_INDEX).getElementsByTagName("button")[0].disabled = true;
        }
    }
}

function noEmptyFields(cells_) {
    var FIELDS_TO_SKIP = 1;
    var REQUIRED_FIELDS = 4;
    for (i = 1; i < 5; i++) {
        if(cells_.item(i).getElementsByTagName("input")[0].value == "") {
            return false;
        }
    }
    return true;
}