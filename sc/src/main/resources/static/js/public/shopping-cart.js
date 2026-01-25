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

    } catch (err) {
        console.error("Error en la petición:", err);
    }
}

async function clearItemCart(id){

}

async function clearAllCart(){

}
