document.getElementById('userForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Impede o envio tradicional do formulário

    const name = document.getElementById('name-input').value;
    const email = document.getElementById('email-input').value;
    const edv = document.getElementById('edv-input').value;
    const password = document.getElementById('password-input').value;
    const passwordConfirmation = document.getElementById('password-inputConfirmation').value;

    const userData = {
        name: name,
        email: email,
        edv: edv,
        password: password
    };

    // Verifica se as senhas são iguais
    if (password !== passwordConfirmation) {
        alert("A senha e a confirmação de senha devem ser iguais!");
        return;  // Sai da função caso as senhas não coincidam
    }

    try {
        // Envia a requisição POST para o backend
        const response = await fetch('http://localhost:8080/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)  // Envia os dados como JSON
        });
        
        // Verifica a resposta do backend
        if (response.ok) {
            const responseBody = await response.text();
            document.getElementById('responseMessage').innerText = responseBody;
            alert("Conta criada com sucesso!")
            window.location.href = "http://127.0.0.1:5500/FrontEnd/src/login/index.html";
        } else {
            const errorMessage = await response.text();
            document.getElementById('responseMessage').innerText = `Erro: ${errorMessage}`;
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        document.getElementById('responseMessage').innerText = 'Erro ao enviar os dados';
    }
});