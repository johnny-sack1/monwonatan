var PRICE_INDEX = 2;
var BUTTON_INDEX = 3;

var coins = document.getElementsByTagName("coins")[0].innerHTML;
var table = document.getElementsByTagName("table")[0];

for (var i = 1; i < table.rows.length; i++) {
  if (table.rows.item(i).cells.item(PRICE_INDEX).innerHTML <= coins) {
    table.rows.item(i).cells.item(BUTTON_INDEX).childNodes[0].disabled = false;
  }
}