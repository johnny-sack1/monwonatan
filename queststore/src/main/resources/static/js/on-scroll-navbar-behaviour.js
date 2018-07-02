var opacityRatio = 0.9;
var opacityRadius = 1.5;

function dynamicOpacity() {
  var header = document.getElementsByClassName("header-background")[0];
  var offset = header.getBoundingClientRect().bottom * -1; // invert offset
  var percent = offset / (header.getBoundingClientRect().height * opacityRatio) + opacityRadius;
  document.body.style.setProperty('--nav-bg-opacity', percent);
}


var prevScrollpos = window.pageYOffset;

function dynamicMenuHiding() {
    var currentScrollPos = window.pageYOffset;
    if (prevScrollpos > currentScrollPos) {
    document.getElementsByTagName("nav")[0].style.top = "0";
    } else {
    document.getElementsByTagName("nav")[0].style.top = "-100px";
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