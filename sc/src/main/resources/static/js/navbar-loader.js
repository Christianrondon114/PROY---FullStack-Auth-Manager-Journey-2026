// src/main/resources/static/js/navbar-loader.js
document.addEventListener("DOMContentLoaded", function() {
    fetch('/html/navbar.html') // Ajusta la ruta a donde guardaste el navbar.html
        .then(response => response.text())
        .then(data => {
            document.getElementById('navbar-container').innerHTML = data;

            const script = document.createElement('script');
            script.src = '/js/navbar.js';
            document.body.appendChild(script);
        })
        .catch(error => console.error('Error cargando el navbar:', error));
});