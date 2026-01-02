// src/main/resources/static/js/navbar-loader.js
document.addEventListener("DOMContentLoaded", function() {
    fetch('/html/navbar.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('navbar-placeholder').innerHTML = data;
        });
});