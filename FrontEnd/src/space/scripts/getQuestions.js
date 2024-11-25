window.onload = () => {
    // Pega o ID passado na URL (exemplo: ?1)
    const urlParams = new URLSearchParams(window.location.search);
    const spaceId = urlParams.get('id');  // 'id' é o nome do parâmetro

    // Verifique se o ID foi encontrado
    if (spaceId) {
        console.log(`ID do espaço: ${spaceId}`);
        // Aqui você pode fazer uma requisição para buscar as perguntas do espaço
        fetchQuestions(spaceId);
    } else {
        console.log("ID não encontrado na URL.");
    }
}

// Função para buscar as perguntas com base no ID do espaço
function fetchQuestions(spaceId) {
    // Exemplo de chamada para uma API (substitua com a URL da sua API ou lógica de banco de dados)
    fetch(`/api/perguntas?spaceId=${spaceId}`)
        .then(response => response.json())
        .then(data => {
            console.log("Perguntas do espaço:", data);
            displayQuestions(data);
        })
        .catch(error => console.error("Erro ao carregar perguntas:", error));
}

// Função para renderizar as perguntas no HTML
function displayQuestions(questions) {
    const cardContainer = document.getElementById('cardContainer');
    cardContainer.innerHTML = ''; // Limpa qualquer conteúdo existente
    
    questions.forEach(question => {
        const card = document.createElement('div');
        card.classList.add('card', 'w-75', 'h-25');
        card.innerHTML = `
            <div class="card-body">
                <button class="btn btn-danger btnExcluir" style="position: absolute; right: 5%;">🗑️</button>
                <h5 class="card-title">${question.title}</h5>
                <p class="card-text">${question.body}</p>
                <p class="card-text"><small class="text-muted">Atualizado por último há ${question.timeAgo}</small></p>
            </div>
        `;
        cardContainer.appendChild(card);
    });
}
