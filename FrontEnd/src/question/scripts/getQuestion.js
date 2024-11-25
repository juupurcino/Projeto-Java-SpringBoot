window.onload = () => {
    // Pega o ID da pergunta na URL (exemplo: ?id=1)
    const urlParams = new URLSearchParams(window.location.search);
    const questionId = urlParams.get('id');  // 'id' é o parâmetro passado na URL

    // Pega o token do localStorage (ou de outro lugar onde você o armazenou)
    const token = localStorage.getItem('token'); // Exemplo: você armazenou o token no localStorage

    // Verifique se o ID e o token foram encontrados
    if (questionId && token) {
        console.log(`ID da pergunta: ${questionId}`);
        console.log(`Token: ${token}`);
        
        // Chama a função para buscar a pergunta
        fetchQuestion(questionId, token);
    } else {
        console.log("ID da pergunta ou token não encontrado.");
    }
}

// Função para buscar a pergunta no backend com base no ID e o token
function fetchQuestion(questionId, token) {
    // Exemplo de requisição ao backend (substitua pela URL da sua API)
    fetch(`http://localhost:8080/spaces/${questionId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,  // Envia o token no header
            'Content-Type': 'application/json'   // Define o tipo de conteúdo como JSON
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log("Dados da pergunta:", data);
            displayQuestion(data);
        })
        .catch(error => console.error("Erro ao carregar a pergunta:", error));
}

// Função para renderizar os dados da pergunta no HTML
function displayQuestion(question) {
    const userElement = document.getElementById('user-name');
    const questionElement = document.getElementById('question-body');
    
    userElement.textContent = question.user.username;
    questionElement.textContent = question.question;

}
