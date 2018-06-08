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
        if (validateCells(cells_)) {
            cells_.item(BUTTON_INDEX).getElementsByTagName("button")[0].disabled = false;
        } else {
            cells_.item(BUTTON_INDEX).getElementsByTagName("button")[0].disabled = true;
        }
    }
}

function noEmptyFields(cells_) {
    var FIELDS_TO_SKIP = 1;
    var REQUIRED_FIELDS = 4;
    var input;
    for (i = FIELDS_TO_SKIP; i < REQUIRED_FIELDS + FIELDS_TO_SKIP; i++) {
        input = cells_.item(i).getElementsByTagName("input")[0];
        if (input.type == "password") {
            continue;
        }
        if(input.value == "") {
            return false;
        }
    }
    return true;
}

function areValidPasswords(cells_) {
    var PASS_INDEX = 3;
    var PASS_2_INDEX = 4;

    var pass1 = cells_.item(PASS_INDEX).getElementsByTagName("input")[0].value;
    var pass2 = cells_.item(PASS_2_INDEX).getElementsByTagName("input")[0].value;

    console.log(pass1 + " " + pass2);

    if (/^$/.test(pass1) && /^$/.test(pass2)){
        return true;
    }

    if(pass1 == pass2) {
        return pass1.length >= 8;
    }

    return false;
}

function validateCells(cells_) {
    if (noEmptyFields(cells_)) {
        console.log("no empty");
        return areValidPasswords(cells_);
    }
    return false;
}