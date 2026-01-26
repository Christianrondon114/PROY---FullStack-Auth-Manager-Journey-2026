async function addToCart(productId, quantity) {
    const requestBody = {
        productId: productId,
        quantity: quantity
    };

    try {
        const response = await fetch('/api/shopping-cart', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        });

        if (response.ok) {
            const cartData = await response.json();
            const summaryTotal = document.getElementById('summary-total');
            const summarySubtotal = document.getElementById('summary-subtotal');

            if (summaryTotal) summaryTotal.innerText = `$${cartData.totalPrice.toFixed(2)}`;
            if (summarySubtotal) summarySubtotal.innerText = `$${cartData.totalPrice.toFixed(2)}`;

            const cartBadge = document.getElementById('cart-count-badge');
            if (cartBadge) {
                cartBadge.innerText = cartData.totalItems;
                cartBadge.classList.remove('d-none'); // Mostrar si estaba oculto
            }

            if (typeof loadCart === 'function' && document.getElementById('cart-items-container')) {
                loadCart();
            }
        }
    } catch (err) {
        console.error("Error en la petición:", err);
    }
}

async function clearItemCart(cartItemId){
    const response = await fetch(`/api/shopping-cart/clear-item?cartItemId=${cartItemId}`,{
        method: 'DELETE'
    });

    if(response.ok){
        await loadCart();
    }
}

async function clearAllCart(){
    const response = await fetch(`/api/shopping-cart/clear-all`, {
        method: 'DELETE'
    });

    if(response.ok){
        await loadCart();
    }
}
