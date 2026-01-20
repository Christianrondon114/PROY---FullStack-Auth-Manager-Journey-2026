document.addEventListener("DOMContentLoaded", function() {
    const container = document.getElementById('navbar-container');
    if (!container) return;

    let navbarPath = '/html/public/navbar_user.html';

    if (window.location.pathname.includes('/administrador/')) {
        navbarPath = '/html/administrador/navbar_admin.html';
    }

    fetch(navbarPath)
        .then(response => {
            if (!response.ok) throw new Error('Navbar no encontrado en: ' + navbarPath);
            return response.text();
        })
        .then(data => {
            container.innerHTML = data;

            initializeNavbarFeatures();
        })
        .catch(error => console.error('Error cargando el navbar:', error));
});

async function initializeNavbarFeatures() {
    const currentPath = window.location.pathname;

    // Marcar link activo
    const links = document.querySelectorAll('.nav-link');
    links.forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });

    try {
        const username = localStorage.getItem('username') || 'Account';
        const userRole = localStorage.getItem('userRole'); // Ej: 'ADMIN', 'USER'

        const nameElement = document.getElementById('nav-username');
        if (nameElement) nameElement.textContent = username;

        if (userRole === 'ADMIN' || userRole === 'MOD') {
            const adminPanelLink = document.getElementById('admin-panel-link');
            if (adminPanelLink) {
                adminPanelLink.classList.remove('d-none');
            }
        }
    } catch (e) {
        console.warn("No se pudo personalizar el navbar (usuario no identificado)");
    }
}