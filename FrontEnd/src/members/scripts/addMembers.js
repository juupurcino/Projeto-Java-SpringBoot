const spaceName = document.getElementById('spaceName');
const cardContainer = document.getElementById('cardContainer');

window.onload = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const spaceId = urlParams.get('idSpace');

    if (spaceId) {
        console.log(`ID do espaço: ${spaceId}`);
        getSpaceInfo(spaceId);
        // getQuestionBySpace(spaceId);
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

function getMenbersBySpace(spaceId) {
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
