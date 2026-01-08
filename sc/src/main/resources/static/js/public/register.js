async function register(){
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const passwordRepeat = document.getElementById('passwordRepeat').value;

    if(password !== passwordRepeat){
        alert("Passwords dont match!")
        return;
    }

    const userRegistered = {
        username : username,
        password : password,
        passwordRepeat: passwordRepeat
    };

    const response = await fetch("/api/register", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userRegistered)
    });

    if (response.ok) {
        alert("¡Register Successfully. Welcome to Cajacho Houses.");
        window.location.href = "/html/myLogin.html";
    } else {
        const errorMsg = await response.text();
        alert("Error: " + errorMsg);
    }




}
