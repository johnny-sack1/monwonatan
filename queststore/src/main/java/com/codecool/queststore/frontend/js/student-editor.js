var input = document.getElementsByTagName("form")[0].getElementsByTagName("input");
var events = ["keydown", "keyup", "keypressed"];
for (i = 0; i < input.length; i++) {
    for (j = 0; j < events.length; j++) {
        var index = i;
        input[i].addEventListener(events[j], function (event) {disableButtonOnEmpty();});
    }
}

function disableButtonOnEmpty() {
    if (validateCells()) {
        document.getElementById("submit-btn").disabled = false;
    } else {
        document.getElementById("submit-btn").disabled = true;
    }

}

function validateCells() {
    var inputs = document.getElementsByTagName("form")[0].getElementsByTagName("input");
    if (noEmptyFields(inputs)) {
        return areValidPasswords(inputs);
    }
    return false;
}

function noEmptyFields(inputs) {
    var input;
    for (i = 0; i < inputs.length; i++) {
        input = inputs[i];
        if (input.type == "password") {
            continue;
        }
        if(input.value == "") {
            return false;
        }
    }
    return true;
}

function areValidPasswords(inputs) {
    var PASS_INDEX = 2;
    var PASS_2_INDEX = 3;

    /*
        Password when changed must be repeated;
        pass1 is 'password' elemnet
        pass2 is 'repeat password' element
    */
    var pass1 = inputs[PASS_INDEX].value;
    var pass2 = inputs[PASS_2_INDEX].value;

    // Password is not changed when 'pass' fields are empty
    if (/^$/.test(pass1) && /^$/.test(pass2)){
        return true;
    }

    if(pass1 == pass2) {
        return pass1.length >= 8;
    }

    return false;
}


function editStudent(buttonClicked) {
    var BUTTON_INDEX = 4;
    var LOGIN_INDEX = 5;

    var table = document.getElementsByTagName("table")[0];

    for (var i = 1; i < table.rows.length; i++) {
        var cells = table.rows.item(i).cells;
        if(cells.item(BUTTON_INDEX).getElementsByTagName("button")[0] == buttonClicked){
            document.getElementById('id01').style.display='block';
        }
    }
}
