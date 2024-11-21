document.getElementById('userForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Evitar que o formulário seja enviado da forma tradicional

    // Coletar os dados do formulário
    const name = document.getElementById('name-input').value;
    const email = document.getElementById('email-input').value;
    const edv = document.getElementById('edv-input').value;
    const password = document.getElementById('password-input').value;
    const passwordConfirmation = document.getElementById('password-inputConfirmation').value;

    // Criar o objeto com os dados
    const userData = {
        name: name,
        email: email,
        edv: edv,
        password: password
    };

    try {
        // Fazer a requisição POST para o backend
        const response = await fetch('http://localhost:8080/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData) // Enviar os dados como JSON
        });

        // Verificar a resposta do backend
        if (response.ok) {
            const responseBody = await response.text();
            document.getElementById('responseMessage').innerText = responseBody;
        } else {
            const errorMessage = await response.text();
            document.getElementById('responseMessage').innerText = `Erro: ${errorMessage}`;
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        document.getElementById('responseMessage').innerText = 'Erro ao enviar os dados';
    }
});