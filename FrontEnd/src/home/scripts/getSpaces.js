// Função para buscar os espaços do backend e renderizar na página
async function loadSpaces() {
    try {
        // Fazendo a requisição GET para buscar os espaços
        const response = await fetch("http://localhost:8080/spaces", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token  // Caso você precise de autenticação com token
            }
        });

        if (!response.ok) {
            throw new Error('Erro ao buscar os espaços: ' + response.statusText);
        }

        const spaces = await response.json();  // Obtém a lista de espaços no formato JSON

        // Se não houver espaços, mostre uma mensagem
        if (spaces.length === 0) {
            document.getElementById('cardsContainer').innerHTML = 'Nenhum espaço encontrado.';
            return;
        }

        // Agora, vamos iterar sobre os espaços e criar os cards dinamicamente
        const cardsContainer = document.getElementById('cardsContainer');
        cardsContainer.innerHTML = '';  // Limpa o container antes de adicionar os novos cards

        spaces.forEach(space => {
            // Cria um card para cada espaço
            const card = document.createElement('div');
            card.classList.add('row', 'd-flex', 'flex-wrap', 'gap-3', 'align-items-center');

            // Criação do conteúdo do card
            card.innerHTML = `
                <div class="card" style="width: 18rem;">
                    <div class="card-body d-flex flex-column align-items-center justify-content-center p-2">
                        <a href="#">
                            <p class="card-text">${space.name}</p>  <!-- O nome do espaço é inserido aqui -->
                        </a>
                    </div>
                    <div class="square"></div>
                </div>
            `;

            // Adiciona o card ao container
            cardsContainer.appendChild(card);
        });

    } catch (error) {
        console.error('Erro:', error);
        document.getElementById('cardsContainer').innerHTML = 'Ocorreu um erro ao carregar os espaços.';
    }
}

// Chama a função para carregar os espaços assim que a página carregar
window.onload = loadSpaces;
