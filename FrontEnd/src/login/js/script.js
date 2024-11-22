// URL da API
const botao = document.getElementById("btn-login");
const url = "http://localhost:8080/auth";

botao.addEventListener('click', async function(event) {
    event.preventDefault();
    const emailInput = document.getElementById("email-input");
    const passwordInput = document.getElementById("password-input");
    
    const data = {
        email: emailInput.value,
        password: passwordInput.value
    }

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erro ${response.status}`);
        }

        return response.json(); // Aqui é onde o corpo da resposta é transformado em um objeto JSON
    })
    
    .then(data => {
        console.log(`Login bem sucedido! Dados recebidos: ${JSON.stringify(data)}`);

        // Verifique onde o token está no corpo da resposta. Exemplo:
        const token = data.token; // Supondo que o JWT esteja em 'data.token'

        if (token) {
            localStorage.setItem('token', token); // Armazenando o token no localStorage
        }

        // Redirecionando após o login
        // window.location.href = "http://127.0.0.1:5500/FrontEnd/src/home/index.html";
    })
    
    .catch(error => {
        console.error(`Erro ao fazer a requisição: ${error}`);
    })
});
