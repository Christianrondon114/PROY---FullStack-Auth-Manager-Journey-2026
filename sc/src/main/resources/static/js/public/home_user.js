document.addEventListener('DOMContentLoaded', function() {
    loadProducts();
});

async function loadProducts() {
    try {
        const response = await fetch('/api/public/products');
        const products = await response.json();
        const container = document.getElementById('cards-container');

        container.innerHTML = '';

        products.forEach(product => {
            const cardHtml = `
                <div class="col-md-4 mb-4"> <div class="card">
                        <img src="${product.imageUrl}" class="card-img-top" alt="periferico">
                        <div class="card-body">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.description}</p>
                            <p class="card-text">$${product.price}</p> 
                            <p class="card-text">${product.discount}% Discount!</p> 
                            
                            <button class="btn btn-primary" onclick="addToCart(${product.idProduct}, 1)">
                    Añadir al carrito
                </button>
                        </div>
                    </div>
                </div>
            `;
            container.innerHTML += cardHtml;
        });
    } catch (error) {
        console.error("Error loading products...", error);

    }
}