document.getElementById('createSpaceBtn').addEventListener('click', async function () {
    const name = document.getElementById("spaceTitle").value;

    if (!name) {
        alert("O título do espaço é obrigatório.");
        return;
    }

    const spaceData = { name, qtdUsers: 1 };

    try {
        const token = localStorage.getItem("token");
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
