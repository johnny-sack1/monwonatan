function validateQuestSelect() {
    var CHECKBOX_INDEX = 0;

    var table = document.getElementsByTagName("table")[0];
    for (var i = 1; i < table.rows.length; i++) {
        if (table.rows.item(i).cells.item(CHECKBOX_INDEX).childNodes[0].checked) {
            document.getElementById("quest_submit").disabled = false;
            return;
        }
    }
    document.getElementById("quest_submit").disabled = true;
}


var inputs = document.getElementsByTagName("input");
for (i = 0; i < inputs.length; i++) {
    if (inputs[i].type == "checkbox") {
        inputs[i].addEventListener('change', function () { validateQuestSelect() });
    }
}