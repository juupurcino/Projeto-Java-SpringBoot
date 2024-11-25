// const cardsContainer = document.getElementById("cardsContainer");
const questionDiv = document.getElementById('questionDiv');


window.onload = () => {

    const urlParams = new URLSearchParams(window.location.search);
    const questionId = urlParams.get('id');  

    const token = localStorage.getItem('token'); 
  
    if (questionId && token) {
        console.log(`ID da pergunta: ${questionId}`);
        console.log(`Token: ${token}`);
        
        // Chama a função para buscar a pergunta
        fetchQuestion(questionId, token);
    } else {
        console.log("ID da pergunta ou token não encontrado.");
    }
}


async function getAnswers() {
    const questionId = new URLSearchParams(window.location.search).get('id');
    console.log(questionId);

    let token = localStorage.getItem('token'); 

    if (!token) {
        console.log("Token não encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '').replace(/^"(.*)"$/, '$1');

    // Verifica se o token existe e se o questionId também
    if (!questionId) {
        console.log("ID da pergunta não encontrado.");
        return;
    }

    fetch(`http://localhost:8080/answer/${questionId}?page=1&size=10`, {
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
        console.log('Dados recebidos:', data);
        if (!data || data.length === 0) {
            questionDiv.innerHTML = 'Essa questão ainda não tem respostas.';
            return;
        }

        questionDiv.innerHTML = ''; // Limpa o container de respostas

        data.forEach(answer => {
            const card = document.createElement('div');
            card.classList.add('row', 'd-flex', 'flex-wrap', 'gap-3', 'align-items-center', 'cardFofo');
            card.innerHTML = `
                <div class="card-body">
                    <h5 class="card-title">${answer.userName}</h5>
                    <p class="card-text">${answer.text}</p>
                </div>
            `;
            questionDiv.appendChild(card);
        });
    })
    .catch(error => {
        console.log('Erro:', error);
        questionDiv.innerHTML = 'Ocorreu um erro ao carregar as respostas.';
    });
}

window.onload = getAnswers;

// innerHTML = '<div class="card-body">
//                         <h5 class="card-title">{Nome do Usuário}</h5>
//                             <p class="card-text">{Texto da Resposta}</p>
//                     </div>'