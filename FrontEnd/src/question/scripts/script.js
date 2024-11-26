// const cardsContainer = document.getElementById("cardsContainer");
const questionTitle = document.getElementById('titleQuestion');
const userName = document.getElementById("userName")
const question = document.getElementById("question")
const card = document.getElementById("cardQuestion")
const btnNewAnswer = document.getElementById('newAnswer');

const urlParams = new URLSearchParams(window.location.search);
const questionId = urlParams.get('idQuestion');
const spaceId = urlParams.get('idSpace');

btnNewAnswer.addEventListener('click', () => {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    const btnFechar = document.getElementById("btnFechar");
    const answer = document.getElementById('answerText').value;
    console.log(answer);

    const idSpace = spaceId;
    const idQuestion = questionId;
    const answerData = {answer, idQuestion, idSpace};


    fetch(`http://localhost:8080/answer`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(answerData),
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            alert("Você não pode responder essa pergunta!");
            throw new Error('Erro na requisição!');
        }
        console.log("Resposta criada!")
        btnFechar.click();
        location.reload();
        return response.json();
    })

    .catch(error => {
        console.log("Error: ", error);
    })
})

window.onload = () => {

    if (questionId && spaceId) {
        console.log(`ID da questão: ${questionId}`);
        console.log(`ID do Espaço: ${spaceId}`);
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

    // Validar o questionId
    const id = parseInt(questionId, 10);
    
    if (isNaN(id) || id <= 0) {
        console.error("ID inválido para a pergunta.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/answer`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error("Erro na requisição:", response.status, response.statusText);
            throw new Error('Erro na requisição');
        }

        const data = await response.json();

        const cardContainer = document.getElementById('cardContainer');
        cardContainer.innerHTML = ''; // Limpar antes de renderizar

        if (!data || data.length === 0) {
            cardContainer.innerHTML = 'Não há respostas ainda!';
            return;
        }

        data.forEach((answer) => {
            const card = document.createElement('div');
            card.classList.add('card', 'w-75', 'h-25');
            card.innerHTML = `
                <div>
                    <p>Resposta: ${answer.text}</p>
                </div>`;
            cardContainer.appendChild(card);
        });
    } catch (error) {
        console.error(error.message);
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

