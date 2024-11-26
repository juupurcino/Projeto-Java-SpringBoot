const spaceName = document.getElementById('spaceName');
const cardContainer = document.getElementById('cardContainer');
const cardUsersContainer = document.getElementById('cardUsersContainer');
const btnQuestion = document.getElementById('createQuestionBtn');
const btnBtns = document.getElementById('btnBtns');
const btnPermission = document.getElementById('changePermission');
var modal = new bootstrap.Modal(document.getElementById('modalUser'));
var modalMember = new bootstrap.Modal(document.getElementById('newMember'));
const modalBody = document.getElementById('modalUsers');

const urlParams = new URLSearchParams(window.location.search);
const spaceId = urlParams.get('idSpace');

window.onload = () => {

    if (spaceId) {
        console.log(`ID do espaço: ${spaceId}`);
        getSpaceInfo(spaceId);
        getQuestionBySpace(spaceId);

    } else {
        console.log("ID não encontrado na URL.");
    }
}

btnQuestion.addEventListener('click', () => {
    createQuestion(spaceId);
});

btnPermission.addEventListener('click', () => {
    getMembers();
})

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
                        <a href="/FrontEnd/src/question/index.html?idQuestion=${question.id}&&idSpace=${spaceId}">
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


async function getMembers() {
    const name = document.getElementById("nomeDoCidadao").value;
    const permissao = document.getElementById("permissao").value;
    console.log("Permissão selecionada:", permissao);

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');


    fetch(`http://localhost:8080/findUser/${name}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Usuário não encontrado');
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        if (data.length > 1) {
            // alert("Há mais de um usuário com esse nome! Escolha qual usuário você quer mudar a permissão: ")
            document.getElementById('btnFecharUser').click();
            modalMember.hide();
            modal.show();

            modalBody.innerHTML = '' 
            data.forEach(users => {
                const cardUser = document.createElement('div');
                    cardUser.classList.add('card', 'w-75', 'h-25', 'p-3');
                    cardUser.innerHTML = `
                         <button class="btn" onclick="changePermissionUser(${spaceId}, ${users.id}, ${parseInt(permissao)})">
                            <h4 class="card-title">Nome: ${users.username}</h4>
                            <p class="card-text">EDV: ${users.edv}</p>
                            <p class="card-text">Email: ${users.email}</p>
                        </button>

                    `;
                    modalBody.appendChild(cardUser);
            })
            
        }

        else {
            changePermissionUser(spaceId, data[0].id, parseInt(permissao));
        }
    })
}


async function changePermissionUser(idSpace, idUser, level) {
    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    const btnFecharUser = document.getElementById("btnFecharUser");

    const permissionData = {idSpace, idUser, level};


    fetch(`http://localhost:8080/permission`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(permissionData),
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            alert("Erro na requisição!");
            throw new Error('Erro na requisição!');
        }
        console.log("Permissão alterada!")
        btnFecharUser.click();
        location.reload();
        return response.json();
    })

    .catch(error => {
        console.log("Error: ", error);
    })
}