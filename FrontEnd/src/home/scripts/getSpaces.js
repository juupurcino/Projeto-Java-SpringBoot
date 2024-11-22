async function loadSpaces() {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            alert("Token não encontrado. Faça login novamente.");
            return;
        }

        const response = await fetch("http://localhost:8080/spaces", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            credentials: 'include'  // Se necessário, adicionar credenciais para cookies ou headers
        });

        if (!response.ok) {
            throw new Error('Erro ao buscar os espaços: ' + response.statusText);
        }

        const spaces = await response.json();

        if (spaces.length === 0) {
            document.getElementById('cardsContainer').innerHTML = 'Nenhum espaço encontrado.';
            return;
        }

        const cardsContainer = document.getElementById('cardsContainer');
        cardsContainer.innerHTML = '';  // Limpa o container antes de adicionar os novos cards

        spaces.forEach(space => {
            const card = document.createElement('div');
            card.classList.add('row', 'd-flex', 'flex-wrap', 'gap-3', 'align-items-center');
            card.innerHTML = `
                <div class="card" style="width: 18rem;">
                    <div class="card-body d-flex flex-column align-items-center justify-content-center p-2">
                        <a href="#">
                            <p class="card-text">${space.name}</p>
                        </a>
                    </div>
                    <div class="square"></div>
                </div>
            `;
            cardsContainer.appendChild(card);
        });

    } catch (error) {
        console.error('Erro:', error);
        document.getElementById('cardsContainer').innerHTML = 'Ocorreu um erro ao carregar os espaços.';
    }
}

// Chama a função para carregar os espaços assim que a página carregar
window.onload = loadSpaces;
