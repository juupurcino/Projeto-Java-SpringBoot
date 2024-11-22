// // URL da API
// const botao = document.getElementById("btn-login")
// const url = "http://localhost:8080/question";

// botao.addEventListener('click', async function(event) {
//     event.preventDefault();
//     const tituloPergunta = document.getElementById("tituloPergunta");
//     const corpoPergunta = document.getElementById("corpoPergunta");
    
    
//     const data = {
//         title: tituloPergunta.value,
//         question: corpoPergunta.value,
//         idSpace: id
//     }

//     console.log("Dados enviados para a API:", JSON.stringify(data));
    
//     fetch(url, {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify(data)
//     })
    
//     .then(response => {
//         if(!response.ok) {
//             throw new Error(`Erro ${response.status}`);
//         }
    
//         return response.json();
//     })
    
//     .then(data => {
//         console.log(`Login bem sucedido! Dados recebidos: ${data}`);
//         window.location.href = "http://127.0.0.1:5500/FrontEnd/src/home/index.html"
//     })
    
//     .catch(error => {
//         console.error(`Erro ao fazer a requisição: ${error}`);
//     })
// });


