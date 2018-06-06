var opacityRatio = 0.8;
var opacityRadius = 0.75;

function dynamicOpacity() {
  var header = document.getElementsByClassName("header-background")[0];
  var offset = header.getBoundingClientRect().bottom * -1; // invert offset
  var percent = offset / (header.getBoundingClientRect().height * opacityRatio) + opacityRadius;
  document.getElementById("test-text").innerHTML = offset;
  document.body.style.setProperty('--nav-bg-opacity', percent);
}


var prevScrollpos = window.pageYOffset;

function dynamicMenuHiding() {
    var currentScrollPos = window.pageYOffset;
    if (prevScrollpos > currentScrollPos) {
    document.getElementById("navbar").style.top = "0";
    } else {
    document.getElementById("navbar").style.top = "-100px";
    }
    prevScrollpos = currentScrollPos;
}


function addDynamicOpacity() {
    document.getElementsByTagName("body")[0].onscroll = function() {dynamicOpacity()};
}

function addMenuHiding() {
    document.getElementsByTagName("body")[0].onscroll = function() {dynamicMenuHiding()};
}

function allFeatures() {
    dynamicOpacity();
    dynamicMenuHiding();
}

function allFeaturesMenu() {
    document.getElementsByTagName("body")[0].onscroll = function() {allFeatures()};
}