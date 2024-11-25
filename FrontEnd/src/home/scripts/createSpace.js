const cardsContainer = document.getElementById("cardsContainer");
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");
const container = document.getElementById('cardsContainer');

let currentPage = 1;
let totalPages = 1;

nextButton.addEventListener('click', () => {
    if (currentPage < totalPages) {
        currentPage += 1;
        getSpaces();
    }

    if (currentPage === totalPages - 2) {
        nextButton.disabled = true;
    } else {
        nextButton.disabled = false;
    }

    if (currentPage === 1) {
        prevButton.disabled = true;
    } else {
        prevButton.disabled = false;
    }

})

prevButton.addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage -= 1;
        getSpaces();
    }

    if (currentPage === 1) {
        prevButton.disabled = true;
    } else {
        prevButton.disabled = false;
    }

    if (currentPage === totalPages - 2) {
        nextButton.disabled = true;
    } else {
        nextButton.disabled = false;
    }
});


async function getSpaces() {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    console.log(token)

    fetch(`http://localhost:8080/spaces?page=${currentPage}&size=2`, {
        method: 'GET',
        // mode: 'no-cors',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
      })
        .then(response => {
                // Verifique se a requisição foi bem-sucedida
            if (!response.ok) {
                throw new Error('Erro na requisição');
            }
        
            // Converta o corpo da resposta para JSON
            return response.json();
        })
        .then(data => {
            console.log('Dados recebidos: ', data);
            totalPages = data.total;
            console.log(totalPages);

            if (totalPages == 0) {
                container.innerHTML = 'Nenhum espaço encontrado.';
            }

            container.innerHTML = '';

            data.space.forEach(space => {
                const card = document.createElement('div');
                card.classList.add('row', 'd-flex', 'flex-wrap', 'gap-3', 'align-items-center', 'cardFofo');
    
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
    
                container.appendChild(card)
            })
        })
        .catch(error => {
            console.log('Erro:', error);
            container.innerHTML = 'Ocorreu um erro ao carregar os espaços.';
        }
    );
}



document.getElementById('createSpaceBtn').addEventListener('click', async function () {
    const name = document.getElementById("spaceTitle").value;

    if (!name) {
        alert("O título do espaço é obrigatório.");
        return;
    }

    const spaceData = { name, qtdUsers: 1 };

    try {
        const token = localStorage.getItem("token");
        
        token = token.replace(/\\/g, '')
        token = token.replace(/^(.*)"$/, '$1')
        token = token.replace(/^(.*)"$/, '$1')
        console.log("Token recebido:", token);

        if (!token) {
            alert("Token não encontrado. Faça login novamente.");
            return;
        }

        const response = await fetch("http://localhost:8080/spaces", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(spaceData),
            credentials: 'include'  // Se necessário, adicionar credenciais para cookies ou headers
        });

        if (!response.ok) {
            throw new Error('Erro ao criar espaço: ' + response.statusText);
        }

        const data = await response.json();
        console.log('Espaço criado com sucesso:', data);
    } catch (error) {
        console.error('Erro:', error);
    }
});


window.onload = getSpaces();