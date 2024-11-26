const cardsContainer = document.getElementById("cardsContainer");
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");
const container = document.getElementById('cardsContainer');

let currentPage = 1;
let totalSpaces = 1;
let totalPages = 1;
let size = 2;

nextButton.addEventListener('click', () => {
    if (currentPage < totalPages) {
        currentPage += 1;
        getSpaces();
    }

    updateButtonState();

})

prevButton.addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage -= 1;
        getSpaces();
    }

    updateButtonState();
});

function updateButtonState() {
    if (currentPage === totalPages) {
        nextButton.disabled = true;
    } else {
        nextButton.disabled = false;
    }

    if (currentPage === 1) {
        prevButton.disabled = true;
    } else {
        prevButton.disabled = false;
    }
}


async function getSpaces() {

    // console.log(currentPage)

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    // console.log(token)

    fetch(`http://localhost:8080/spaces?page=${currentPage}&size=${size}`, {
        method: 'GET',
        // mode: 'no-cors',
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
            // console.log('Dados recebidos: ', data);
            totalSpaces = data.total;
            totalPages = Math.ceil(totalSpaces / size);
            // console.log(totalSpaces);

            if (totalSpaces == 0) {
                container.innerHTML = 'Nenhum espaço encontrado.';
            }

            container.innerHTML = '';

            data.space.forEach(space => {
                const card = document.createElement('div');
                card.classList.add('row', 'd-flex', 'flex-wrap', 'gap-3', 'align-items-center', 'cardFofo');
    
                card.innerHTML = `
                    <div class="card" style="width: 18rem;">
                        <div class="card-body d-flex flex-column align-items-center justify-content-center p-2">
                            <a href='/FrontEnd/src/space/index.html?idSpace=${space.idSpace}'>
                                <p class="card-text">${space.name}</p>  <!-- O nome do espaço é inserido aqui -->
                            </a>
                        </div>
                        <div class="square"></div>
                    </div>
                `;
    
                container.appendChild(card)
            })
            updateButtonState();
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

        const contentType = response.headers.get("content-type");

        if (contentType && contentType.includes("application/json")) {
            const data = await response.json();
            console.log('Espaço criado com sucesso:', data);
            window.onload = getSpaces();
            
        } else {
            const message = await response.text();
            console.log('Resposta recebida:', message); 
            // alert(message);  // Exibe a mensagem de sucesso no usuário
        }
           // Simula o clique no botão de fechar do modal
           const closeButton = document.querySelector('#newSpace .btn-close');
           if (closeButton) {
               closeButton.click();  // Fechar o modal
           }
    } catch (error) {
        console.error('Erro:', error);
    }
});


window.onload = getSpaces();