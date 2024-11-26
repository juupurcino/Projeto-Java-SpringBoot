const spaceName = document.getElementById('spaceName');
const cardContainer = document.getElementById('cardContainer');
const btnQuestion = document.getElementById('createQuestionBtn');
const btnBtns = document.getElementById('btnBtns');

const urlParams = new URLSearchParams(window.location.search);
const spaceId = urlParams.get('idSpace');

window.onload = () => {

    if (spaceId) {
        console.log(`ID do espaço: ${spaceId}`);
        getSpaceInfo(spaceId);
        getQuestionBySpace(spaceId);
        const btnAddMembers = document.createElement('button');
        btnAddMembers.classList.add('btn', 'btn-secondary', 'h-50');
        const linkForAddMembers = document.createElement('a');
        linkForAddMembers.href = `../members?spaceId=${spaceId}`;
        linkForAddMembers.innerHTML = "Adicionar membros";
        btnAddMembers.appendChild(linkForAddMembers);
        btnBtns.appendChild(btnAddMembers);
    } else {
        console.log("ID não encontrado na URL.");
    }
}

btnQuestion.addEventListener('click', () => {
    createQuestion(spaceId);
});

async function getSpaceInfo(spaceId) {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    var idNumber = parseInt(spaceId, 10);

    const viewMembersButton = document.querySelector('.btn-secondary a');

    viewMembersButton.href = `/FrontEnd/src/members/index.html?idSpace=${idNumber}`;

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
        return response.json();
    })
    .then(data => {
        console.log(data);
        spaceName.innerHTML = data.name;
    })
}

async function getQuestionBySpace(spaceId) {
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
        location.reload();
        return response.json();
    })
    getQuestionBySpace();
}

async function createQuestion(idSpace) {
    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    const btnFechar = document.getElementById("btnFechar");
    const title = document.getElementById('questionTitle').value;
    const question = document.getElementById('questionBody').value;

    const questionData = {title, question, idSpace};


    fetch(`http://localhost:8080/question`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(questionData),
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            alert("Erro na requisição!");
            throw new Error('Erro na requisição!');
        }
        console.log("Question criada!")
        btnFechar.click();
        location.reload();
        return response.json();
    })

    .catch(error => {
        console.log("Error: ", error);
    })
}