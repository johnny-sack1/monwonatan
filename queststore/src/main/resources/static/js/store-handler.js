var PRICE_INDEX = 2;
var BUTTON_INDEX = 3;

var coins = document.getElementsByTagName("coins")[0].innerHTML;
var table = document.getElementsByTagName("table")[0];

for (var i = 1; i < table.rows.length; i++) {
  var cells_ = table.rows.item(i).cells;
  if (cells_.item(PRICE_INDEX).innerHTML <= parseInt(coins)) {
    cells_.item(BUTTON_INDEX).childNodes[0].disabled = false;
  }
}