const cardsContainer = document.getElementById("cardsContainer");
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");

let currentPage = 1;
let totalPages = 1;

async function getSpaces(page) {

    let token = localStorage.getItem('token')

    if (!token) {
        console.log("Token nâo encontrado, permissâo negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    console.log(token)

    fetch(`http://localhost:8080/spaces?page=${currentPage}&size=8`, {
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
        })
        .catch(error => console.log('Erro:', error));
}

async function createSpace() {
    const name = document.getElementById("spaceTitle").value;

    if (!name) {
        alert("O título do espaço é obrigatório.");
        return;
    }

    let token = localStorage.getItem('token');

    if (!token) {
        console.log("Token não encontrado, permissão negada!");
        return;
    }

    token = token.replace(/\\/g, '');
    token = token.replace(/^"(.*)"$/, '$1');
    token = token.replace(/^"(.*)"$/, '$1');

    const spaceData = { name, qtdUsers: 1 };

    try {
        const response = await fetch(`http://localhost:8080/spaces`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(spaceData)
        });

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.statusText}`);
        }

        const contentType = response.headers.get("content-type");

        if (contentType && contentType.includes("application/json")) {
            const data = await response.json();
            console.log('Espaço criado com sucesso:', data);
            window.location.href = "http://127.0.0.1:5500/FrontEnd/src/home/index.html"
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
        console.log('Erro ao criar o espaço:', error);
    }
}

document.getElementById('createSpaceBtn').addEventListener('click', createSpace);

getSpaces();