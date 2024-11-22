// URL da API
const botao = document.getElementById("btn-login")
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
        if(!response.ok) {
            throw new Error(`Erro ${response.status}`);
        }

        return response.json();
    })
    
    .then(data => {
        console.log(`Login bem sucedido! Dados recebidos: ${JSON.stringify(data)}`);
        window.location.href = "http://127.0.0.1:5500/FrontEnd/src/home/index.html"

        // Verifica se o token está no header e armazena no localStorage
        // const token = response.headers.get('Authorization'); // Caso o JWT venha no header
        // if (token) {
            localStorage.setItem('token', JSON.stringify(JSON.stringify(data.jwt))); // Armazenando o JWT no localStorage
        // }

        // window.location.href = "http://127.0.0.1:5500/FrontEnd/src/home/index.html"
    })
    
    .catch(error => {
        console.error(`Erro ao fazer a requisição: ${error}`);
    })
})