// URL da API
const url = "https://localhost:8080/auth";

fetch(url).then(response => {
    if(!response.ok) {
        throw new Error(`Erro ${response.status}`);
    }

    return response.json();
})
.then(data => {
    console.log(`Dados recebidos: ${data}`);
})

.catch(error => {
    console.error(`Erro ao fazer a requisição: ${error}`);
})