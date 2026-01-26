document.addEventListener('DOMContentLoaded', () => {
    loadCart();
});

async function loadCart() {
    try {
        const response = await fetch('/api/shopping-cart/my-cart');
        const data = await response.json();
        const container = document.getElementById('cart-items-container');

        container.innerHTML = '<br><h1>Welcome to your Shopping Cart</h1><br>';

        if (!data.listItems || data.listItems.length === 0) {
            container.innerHTML += `
    <div class="text-center mt-5">
        <p class="empty-msg text-muted">Your Shopping Cart is empty!</p>
        <a href="/html/public/home_user.html" class="fw-bold text-primary text-decoration-none">
            <i class="bi bi-arrow-left"></i> Comeback to the Store and add Products
        </a>
    </div>
`;
            return;
        }
        data.listItems.forEach(item => {
            const price = item.productPrice || 0;
            const subtotal = item.subtotal || 0;

            const cartItemHtml = `
                <article class="cart-item">
                    <img src="${item.imageUrl || 'https://via.placeholder.com/100'}" alt="${item.productName}" style="width: 100px; height: 100px; object-fit: cover;"/>
                    <div class="item-details">
                        <h3>${item.productName || 'Producto sin nombre'}</h3>
                        <p class="unit-price">Precio: $${price}</p>
                        <div class="item-controls d-flex align-items-center gap-3">
                        <div class="qty-selector d-flex align-items-center bg-light border rounded-3 p-1">
        <button class="btn btn-sm btn-qty-control border-0" onclick="cambiarCantidad(${item.productId}, -1)">
            <i class="bi bi-dash-lg"></i>
        </button>
        
        <span class="qty-display px-3 fw-bold">${item.quantity}</span>
        
        <button class="btn btn-sm btn-qty-control border-0" onclick="cambiarCantidad(${item.productId}, 1)">
            <i class="bi bi-plus-lg"></i>
        </button>
    </div>

    <button class="btn btn-link text-danger text-decoration-none btn-sm" onclick="clearItemCart(${item.cartItemId})">
        <i class="bi bi-trash3 me-1"></i> Eliminar
    </button>
</div>
                    </div>
                    <div class="item-subtotal" style="margin-left: auto;">
                            <p style="font-weight: bold;">Subtotal: $${subtotal}</p>
                    </div>
                </article>
            `;
            container.innerHTML += cartItemHtml;
        });
    } catch (error) {
        console.error("Error al cargar el carrito:", error);
    }
}