document.addEventListener('DOMContentLoaded', function() {
    loadProducts();
});

async function loadProducts() {
    try {
        const response = await fetch('/api/products');

        if (!response.ok) {
            if (response.status === 403) {
                alert("No tienes permisos de administrador.");
            }
            throw new Error("Error al obtener usuarios");
        }

        const products = await response.json();
        const tableBody = document.getElementById('products-table-body');

        tableBody.innerHTML = '';

        products.forEach(product => {
            const row = `
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="fw-bold">${product.productId}</div>
                        </div>
                    </td>
                    <td>${product.name}</td>
                    <td><span class="badge bg-primary">${product.price}</span></td>
                    <td><span class="badge bg-primary">${product.stock}</span></td>
                    <td><span class="badge bg-primary">${product.available}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteProduct(${product.productId})"> Delete </button>
                        <button class="btn btn-sm btn-outline-dark" onclick="prepareUpdate(${product.productId}, '${product.name}', 
                        '${product.description}', ${product.price}, ${product.stock}, '${product.category}', ${product.discount}, 
                        '${product.brand}', '${product.imageUrl}', ${product.available})"> Edit </button>
                        <button class="btn btn-sm btn-outline-info me-2" onclick="showDetail(${product.productId})"> <i class="bi bi-eye"></i> View </button>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });

    } catch (error) {
        console.error("Error:", error);
    }
}

async function createProduct() {
    const name = document.getElementById('nameInput').value;
    const description = document.getElementById('descriptionInput').value;
    const price = document.getElementById('priceInput').value;
    const stock = document.getElementById('stockInput').value;
    const category = document.getElementById('categoryInput').value;
    const discount = document.getElementById('discountInput').value;
    const brand = document.getElementById('brandInput').value;
    const imageUrl = document.getElementById('imageUrlInput').value;
    const available = document.getElementById('availableInput').checked;


    const CreateProductRequest = {
        name: name,
        description: description,
        price: parseInt(price),
        stock: parseInt(stock),
        category: category,
        discount: parseInt(discount),
        brand: brand,
        imageUrl: imageUrl,
        available: available
    };

    const response = await fetch('/api/products', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(CreateProductRequest)
    });

    if(response.ok) {
        const modalElement = document.getElementById('createProductModal');
        const modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();
        document.getElementById('createProductForm').reset();
        loadProducts();
    }
}

async function deleteProduct(id){
    const response = await fetch(`/api/products/${id}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        loadProducts();
    }
}

async function updateProduct(id) {

    const updateRequest = {
        name: document.getElementById('updateNameInput').value,
        description: document.getElementById('updateDescriptionInput').value,
        price: parseFloat(document.getElementById('updatePriceInput').value),
        stock: parseInt(document.getElementById('updateStockInput').value),
        category: document.getElementById('updateCategoryInput').value,
        discount: parseFloat(document.getElementById('updateDiscountInput').value),
        brand: document.getElementById('updateBrandInput').value,
        imageUrl: document.getElementById('updateImageInput').value, // Debe ser imageUrl como en tu DTO
        available: document.getElementById('updateAvailableInput').checked
    };

    try {
        const response = await fetch(`/api/products/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updateRequest)
        });

        if (response.ok) {
            alert("¡Producto actualizado!");
            loadProducts(); // Recarga la tabla

            // Cerrar modal
            const modalElement = document.getElementById('updateProductModal');
            const modalInstance = bootstrap.Modal.getInstance(modalElement);
            if (modalInstance) modalInstance.hide();
        } else {
            const error = await response.json();
            alert("Error al actualizar: " + (error.message || "Verifica los datos"));
        }
    } catch (error) {
        console.error("Error en el PUT:", error);
    }
}

async function prepareUpdate(id) {
    try {
        const response = await fetch(`/api/products/${id}`);
        if (!response.ok) throw new Error("No se pudo obtener el detalle del producto");

        const product = await response.json();

        document.getElementById('updateNameInput').value = product.name;
        document.getElementById('updateDescriptionInput').value = product.description;
        document.getElementById('updatePriceInput').value = product.price;
        document.getElementById('updateStockInput').value = product.stock;
        document.getElementById('updateCategoryInput').value = product.category;
        document.getElementById('updateDiscountInput').value = product.discount;
        document.getElementById('updateBrandInput').value = product.brand;

        const imgInput = document.getElementById('updateImageInput');
        if (imgInput) imgInput.value = product.imageUrl;

        document.getElementById('updateAvailableInput').checked = product.available;

        const saveButton = document.querySelector('#updateProductModal .btn-success');
        saveButton.setAttribute('onclick', `updateProduct(${id})`);

        // Abrimos el modal
        const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('updateProductModal'));
        modal.show();

    } catch (error) {
        console.error("Error al preparar update:", error);
        alert("Error al cargar datos del producto");
    }
}

async function showDetail(id) {
    try {
        const response = await fetch(`/api/products/${id}`);

        if (!response.ok) {
            if (response.status === 404) {
                throw new Error("Product not founded.");
            }
            throw new Error("Information not founded");
        }

        const product = await response.json();

        document.getElementById('viewId').textContent = product.productId;
        document.getElementById('viewName').textContent = product.name;
        document.getElementById('viewDescription').textContent = product.description;
        document.getElementById('viewPrice').textContent = `$${product.price.toFixed(2)}`;
        document.getElementById('viewStock').textContent = product.stock;
        document.getElementById('viewCategory').textContent = product.category;
        document.getElementById('viewDiscount').textContent = `${product.discount}%`;
        document.getElementById('viewBrand').textContent = product.brand;
        document.getElementById('viewImage').textContent = product.imageUrl || "No image available";

        const availableElement = document.getElementById('viewAvailable');
        availableElement.textContent = product.available ? "Yes" : "No";
        availableElement.className = product.available ? "badge bg-success" : "badge bg-danger";

        document.getElementById('viewReleaseDate').textContent = product.releaseDate;

        const modalElement = document.getElementById('ProductDetailModal');
        const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
        modal.show();

    } catch (error) {
        console.error("Error en showDetail:", error);
        alert(error.message);
    }
}