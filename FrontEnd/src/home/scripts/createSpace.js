const cardsContainer = document.getElementById("cardsContainer");
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");

let currentPage = 1;
let totalPages = 1;

async function getSpaces(page) {
    try {
        const response = await fetch(`http://localhost:8080/spaces?page=${page}&size=8`);
        const data = await response.json();

        totalPages = Math.ceil(data)
    }
}



document.getElementById('createSpaceBtn').addEventListener('click', async function () {
    const name = document.getElementById("spaceTitle").value;

    // Verifica se o nome foi fornecido
    if (!name) {
        alert("O título do espaço é obrigatório.");
        return;
    }

    const spaceData = {
        name: name,
        qtdUsers: 1 
    };

    try {
        // Envia a requisição POST para o backend
        const response = await fetch("http://localhost:8080/spaces", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Se você estiver usando autenticação baseada em token ou sessão
                'Authorization': 'Bearer ' + token // Certifique-se de que o token está disponível
            },
            body: JSON.stringify(spaceData),  // Passa o spaceData para o corpo da requisição
            credentials: 'include'  // Adiciona cookies (se necessário)
        });

        if (!response.ok) {
            throw new Error('Erro ao criar espaço: ' + response.statusText);
        }

        const data = await response.json();
        console.log('Espaço criado com sucesso:', data);
        // Se desejar, você pode atualizar a interface aqui

    } catch (error) {
        console.error('Erro:', error);
    }
});
