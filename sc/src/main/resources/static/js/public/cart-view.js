// 1. IMPORTANTE: Ejecutar la función al cargar
document.addEventListener('DOMContentLoaded', () => {
    loadCart();
});

async function loadCart() {
    try {
        const response = await fetch('/api/shopping-cart/my-cart');
        if (!response.ok) throw new Error("Error en la respuesta del servidor");

        const data = await response.json();

        // 2. CORRECCIÓN DEL SELECTOR: Usamos getElementById
        const container = document.getElementById('cart-items-container');

        if (!container) {
            console.error("No se encontró el contenedor 'cart-items-container' en el HTML");
            return;
        }

        // Limpiamos el contenedor
        container.innerHTML = '<h1>Welcome to your Shopping Cart</h1>';

        // 3. VALIDACIÓN DE DATOS: Verificamos si hay items
        if (!data.listItems || data.listItems.length === 0) {
            container.innerHTML += '<p class="empty-msg">Until now your Shopping Cart is empty!</p>';
            return;
        }

        data.listItems.forEach(item => {
            // Usamos nombres seguros. Si item.productPrice no existe, pondrá 0
            const price = item.productPrice || 0;
            const subtotal = item.subtotal || 0;

            const cartItemHtml = `
                <article class="cart-item" style="border: 1px solid #ccc; margin-bottom: 10px; padding: 10px; display: flex; align-items: center; gap: 20px;">
                    <img src="${item.imageUrl || 'https://via.placeholder.com/100'}" alt="${item.productName}" style="width: 100px; height: 100px; object-fit: cover;"/>
                    <div class="item-details">
                        <h3>${item.productName || 'Producto sin nombre'}</h3>
                        <p class="unit-price">Precio: $${price}</p>
                        <div class="item-controls">
                            <button class="btn-qty" onclick="cambiarCantidad(${item.productId}, -1)">-</button>
                            <span class="qty" style="margin: 0 10px; font-weight: bold;">${item.quantity}</span>
                            <button class="btn-qty" onclick="cambiarCantidad(${item.productId}, 1)">+</button>
                            <button class="btn-remove" style="margin-left: 15px; color: red;" onclick="clearItemCart(${item.cartItemId})">Eliminar</button>
                        </div>
                    </div>
                    <div class="item-subtotal" style="margin-left: auto;">
                        <p style="font-weight: bold;">Subtotal: $${subtotal}</p>
                    </div>
                </article>
            `;
            container.innerHTML += cartItemHtml;
        });

        // Añadir el total general
        container.innerHTML += `
            <div class="cart-summary" style="text-align: right; margin-top: 20px; border-top: 2px solid #000;">
                <h2 style="margin-top: 10px;">Total a pagar: $${data.totalPrice || 0}</h2>
                <button onclick="clearAllCart()" style="background: #ff4444; color: white; border: none; padding: 10px 20px; cursor: pointer;">Vaciar Carrito</button>
            </div>
        `;

    } catch (error) {
        console.error("Error al cargar el carrito:", error);
    }
}