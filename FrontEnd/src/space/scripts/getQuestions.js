const spaceName = document.getElementById('spaceName');
const cardContainer = document.getElementById('cardContainer');

window.onload = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const spaceId = urlParams.get('idSpace');

    if (spaceId) {
        console.log(`ID do espaço: ${spaceId}`);
        getSpaceInfo(spaceId);
        getQuestionBySpace(spaceId);
        // fetchQuestions(spaceId);
    } else {
        console.log("ID não encontrado na URL.");
    }
}

function getSpaceInfo(spaceId) {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    var idNumber = parseInt(spaceId, 10);

    fetch(`http://localhost:8080/spaces/${idNumber}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição');
        }
        // Converta o corpo da resposta para JSON
        return response.json();
    })
    .then(data => {
        console.log(data);
        spaceName.innerHTML = data.name;
    })
}

function getQuestionBySpace(spaceId) {
    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    var idNumber = parseInt(spaceId, 10);

    fetch(`http://localhost:8080/question/${idNumber}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição');
        }
        // Converta o corpo da resposta para JSON
        return response.json();
    })
    .then(data => {
        console.log(data);
        if (data.length === 0) {
            cardContainer.innerHTML = '';
            cardContainer.innerHTML = 'Não há perguntas nesse espaço!';
        }

        else {
            data.forEach(question => {
                const card = document.createElement('div');
                card.classList.add('card', 'w-75', 'h-25');
                card.innerHTML = `
                    <div class="card-body">
                        <button class="btn btn-danger btnExcluir" style="position: absolute; right: 5%;" onclick="deleteQuestion(${question.id})">🗑️</button>
                        <a href="/FrontEnd/src/question/index.html?idQuestion=${question.id}">
                            <h4 class="card-title">${question.user.username}: ${question.title}</h5>
                            <p class="card-text">${question.question}</p>
                        </a>
                    </div>
                `;
                cardContainer.appendChild(card);
            });
        }
    })
}

async function deleteQuestion(idQuestion) {
    
    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');


    fetch(`http://localhost:8080/question/${idQuestion}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição');
        }
        // Converta o corpo da resposta para JSON
        return response.json();
    })
}

// Função para buscar as perguntas com base no ID do espaço
// function fetchQuestions(spaceId) {
//     // Exemplo de chamada para uma API (substitua com a URL da sua API ou lógica de banco de dados)
//     fetch(`/api/perguntas?${spaceId}`)
//         .then(response => response.json())
//         .then(data => {
//             console.log("Perguntas do espaço:", data);
//             displayQuestions(data);
//         })
//         .catch(error => console.error("Erro ao carregar perguntas:", error));
// }

// Função para renderizar as perguntas no HTML
// function displayQuestions(questions) {
//     const cardContainer = document.getElementById('cardContainer');
//     cardContainer.innerHTML = ''; // Limpa qualquer conteúdo existente
    
//     questions.forEach(question => {
//         const card = document.createElement('div');
//         card.classList.add('card', 'w-75', 'h-25');
//         card.innerHTML = `
//             <div class="card-body">
//                 <button class="btn btn-danger btnExcluir" style="position: absolute; right: 5%;">🗑️</button>
//                 <h5 class="card-title">${question.title}</h5>
//                 <p class="card-text">${question.body}</p>
//                 <p class="card-text"><small class="text-muted">Atualizado por último há ${question.timeAgo}</small></p>
//             </div>
//         `;
//         cardContainer.appendChild(card);
//     });
// }
