document.addEventListener('DOMContentLoaded', function() {
    loadUsers();
});

async function loadUsers() {
    try {
        const response = await fetch('/api/users');

        if (!response.ok) {
            if (response.status === 403) {
                alert("No tienes permisos de administrador.");
            }
            throw new Error("Error al obtener usuarios");
        }

        const users = await response.json();
        const tableBody = document.getElementById('users-table-body');

        tableBody.innerHTML = '';

        users.forEach(user => {
            const row = `
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="fw-bold">${user.id}</div>
                        </div>
                    </td>
                    <td>${user.username}</td>
                    <td><span class="badge bg-primary">${user.roleName}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteUser(${user.id})"> Delete </button>
                        <button class="btn btn-sm btn-outline-dark" onclick="prepareUpdate(${user.id}, '${user.username}', ${user.idRole})"> Edit </button>
                        <button class="btn btn-sm btn-outline-info me-2" onclick="showDetail(${user.id})"> <i class="bi bi-eye"></i> View </button>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });

    } catch (error) {
        console.error("Error:", error);
    }
}

async function createUser() {
    const username = document.getElementById('usernameInput').value;
    const password = document.getElementById('passwordInput').value;
    const idRole = document.getElementById('roleSelect').value;

    const newUser = {
        username: username,
        password: password,
        idRole: parseInt(idRole)
    };

    const response = await fetch('/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newUser)
    });

    if(response.ok) {
        const modalElement = document.getElementById('createUserModal');
        const modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();
        document.getElementById('createUserForm').reset();
        loadUsers();
    }
}

async function deleteUser(id){
    const response = await fetch(`/api/users/${id}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        loadUsers();
    }
}

async function updateUser(id){
    const username = document.getElementById("usernameUpdate").value;
    const password = document.getElementById('passwordUpdate').value;
    const idRole = document.getElementById('roleUpdate').value;

    const UpdateUserRequest = {
        username: username,
        password: password,
        idRole: parseInt(idRole)
    };

    const response = await fetch(`/api/users/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(UpdateUserRequest)
    });

    if (response.ok) {
        loadUsers();
        const modalElement = document.getElementById('updateUserModal');
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        if (modalInstance) {
            modalInstance.hide();
        }

    }
}

function prepareUpdate(id, username, roleId) {
    document.getElementById('usernameUpdate').value = username;
    document.getElementById('roleUpdate').value = roleId;

    const saveButton = document.querySelector('#updateUserModal .btn-warning');

    saveButton.setAttribute('onclick', `updateUser(${id})`);

    const modal = new bootstrap.Modal(document.getElementById('updateUserModal'));
    modal.show();
}

async function showDetail(id) {
    try {
        const response = await fetch(`/api/users/${id}`);

        if (!response.ok) {
            throw new Error("No se pudo obtener la información del usuario");
        }

        /** @type {{id: number, username: string, roleName: string}} */
        const user = await response.json();

        // 2. Referencias a los elementos del modal
        const viewId = document.getElementById('viewId');
        const username = document.getElementById('viewName');
        const roleName = document.getElementById('viewRole');

        if (viewId && username && roleName) {
            viewId.textContent = user.id;
            username.textContent = user.username;
            roleName.textContent = user.roleName;

            const modalElement = document.getElementById('userDetailModal');
            const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
            modal.show();
        } else {
            console.error("Error: Algunos elementos del modal no se encontraron en el DOM.");
        }

    } catch (error) {
        console.error("Error en showDetail:", error);
        alert("Could not load user details.");
    }
}

