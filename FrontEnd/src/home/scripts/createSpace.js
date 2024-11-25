const cardsContainer = document.getElementById("cardsContainer");
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");

let currentPage = 1;
let totalPages = 1;

// async function getSpaces(page) {
//     try {
//         const response = await fetch(`http://localhost:8080/spaces?page=${page}&size=8`);
//         const data = await response.json();

//         totalPages = Math.ceil(data)
//     }
// }

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
