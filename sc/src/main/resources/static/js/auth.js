async function login() {
    const username = document.getElementById("user").value;
    const password = document.getElementById("pass").value;

    const response = await fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    const data = await response.json();

    if (data.status === "ok") {
        window.location.href = "/html/index.html";
    }
}
