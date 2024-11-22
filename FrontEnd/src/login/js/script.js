// URL da API
const url = "https://localhost:8080/auth";
const emailInput = document.getElementById("email-input");
const passwordInput = document.getElementById("password-input");


const data = {
    login: "emailInput",
    passwordInput: "passwordInput"
}

fetch(url, {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
})

.then(response => {
    if(!response.ok) {
        throw new Error(`Erro ${response.status}`);
    }

    return response.json();
})

.then(data => {
    console.log(`Login bem sucedido! Dados recebidos: ${data}`);
})

.catch(error => {
    console.error(`Erro ao fazer a requisição: ${error}`);
})