// const cardsContainer = document.getElementById("cardsContainer");
const questionTitle = document.getElementById('titleQuestion');
const userName = document.getElementById("userName")
const question = document.getElementById("question")

const urlParams = new URLSearchParams(window.location.search);
const questionId = urlParams.get('idQuestion');

window.onload = () => {

    if (questionId) {
        console.log(`ID da questão: ${questionId}`);
        getQuestionInfo(questionId);
  
    } else {
        console.log("ID não encontrado na URL.");
    }
}


async function getQuestionInfo(spaceId) {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    var id = parseInt(spaceId, 10);

    // const viewMembersButton = document.querySelector('.btn-secondary a');

    // viewMembersButton.href = `/FrontEnd/src/members/index.html?idSpace=${idNumber}`;

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

async function getAnswers() {

}

async function createAnswer() {
    
}

