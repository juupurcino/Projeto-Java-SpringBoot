// const cardsContainer = document.getElementById("cardsContainer");
const questionTitle = document.getElementById('titleQuestion');
const userName = document.getElementById("userName")
const question = document.getElementById("question")
const card = document.getElementById("cardContainer")

const urlParams = new URLSearchParams(window.location.search);
const questionId = urlParams.get('idQuestion');

if (!questionId || isNaN(questionId)) {
    console.error("ID inválido para a pergunta.");
}

window.onload = () => {

    if (questionId) {
        console.log(`ID da questão: ${questionId}`);
        getQuestionInfo(questionId);
        getAnswers(questionId)
  
    } else {
        console.log("ID não encontrado na URL.");
    }
}

async function getQuestionInfo(idQuestion) {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    var id = parseInt(idQuestion, 10);

    fetch(`http://localhost:8080/question/id/${id}`, {
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
        questionTitle.innerHTML = data.title;
        userName.innerHTML = data.user.username;
        question.innerHTML = data.question;
    })
}
async function getAnswers(questionId) {
    let token = localStorage.getItem('token');

    if (!token) {
        console.log("Token não encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    try {
        const response = await fetch(`http://localhost:8080/answer/${questionId}?page=1&size=10`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            // Se a resposta não for 200-299, trata como erro
            if (response.status === 400) {
                console.log("Não há respostas ainda");
                card.innerHTML = 'Não há respostas ainda!';
            } else {
                console.log(`Erro desconhecido: ${response.status}`);
            }
            return;
        }

        const data = await response.json();

        if (data.length === 0) {
            card.innerHTML = 'Não há respostas ainda!';
            return;
        }

        data.forEach((answer) => {
            // Criar um novo card para cada resposta
            const card = document.createElement('div');
            card.classList.add('d-flex', 'justify-content-between', 'flex-row', 'relative');
            card.style.position = 'relative';
        
            // Adicionar conteúdo do card
            card.innerHTML = `
                <div class="upvote">
                    <button class="upCircle">🔼</button>
                    <p class="mb-2">0</p>
                    <button class="downCircle">🔽</button>
                </div>
                <div class="row d-flex flex-wrap gap-3 align-items-center w-100">
                    <div class="card flex-wrap" style="width: 70rem; height: fit-content;">
                        <div class="card-body">
                            <h5 class="card-title">${answer.title}</h5>
                            <p class="card-text">${answer.answer}</p>
                        </div>
                    </div>
                </div>
            `;
            
            // Adicionar o card ao container
            const cardContainer = document.getElementById('cardContainer');
            cardContainer.appendChild(card);
        });
        
    } catch (error) {
        console.error("Erro ao buscar respostas:", error.message);
    }
}

async function createAnswer(questionId) {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    var id = parseInt(questionId, 10);

    const answer = document.getElementById("answerText")

    const aswerData =  {answer}

    fetch(`http://localhost:8080/answer`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(aswerData),
            credentials: 'include'
        
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
            cardContainer.innerHTML = 'Não há respostas ainda!';
        }})
    
}

document.getElementById('createAnsBtn').addEventListener('click', createAnswer);

