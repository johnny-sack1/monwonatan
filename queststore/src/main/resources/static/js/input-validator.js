var input = document.getElementsByTagName("input");
var events = ["keydown", "keyup", "keypressed"];
for (i = 0; i < input.length; i++) {
    for (j = 0; j < events.length; j++) {
        input[i].addEventListener(events[j], function (event) {inputValidation()});
    }
}


function disableButtonOnEmpty() {
    var inputs = document.getElementsByTagName("input");
    for (i = 0; i < inputs.length; i++) {
        if (!inputs[i].value) {
            document.getElementById("submit-btn").disabled = true;
            return;
        }
    }
    document.getElementById("submit-btn").disabled = false;
}

function buttonClickOnEnter() {
    if (event.keyCode === 13) {
        document.getElementById("submit-btn").click();
    }
}

function passwordsMustBeEqual() {
    var passwords = document.getElementsByClassName("password");
    if (passwords[0].value !== passwords[1].value) {
        document.getElementById("submit-btn").disabled = true;
    }
}

function inputValidation() {
    disableButtonOnEmpty();
    buttonClickOnEnter();
    passwordsMustBeEqual();
}