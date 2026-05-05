const questionsList = document.getElementById("questions-list");
const addQuestionButton = document.getElementById("add-question");

function updateQuestionTitles() {
    const questions = questionsList.querySelectorAll(".question-item");

    questions.forEach((question, index) => {
        question.querySelector("strong").textContent = `Questão ${index + 1}`;
        question.querySelector(".remove-question").disabled = questions.length === 1;
    });
}

function createQuestionItem() {
    const question = document.createElement("div");
    question.className = "border rounded p-3 question-item";
    question.innerHTML = `
        <div class="d-flex justify-content-between align-items-center mb-3">
            <strong>Questão</strong>
            <button type="button" class="btn btn-outline-danger btn-sm remove-question">
                <i class="ti ti-trash"></i>
                Remover
            </button>
        </div>
        <div class="row g-3">
            <div class="col-md-9">
                <label class="form-label">Enunciado</label>
                <textarea class="form-control" name="questionText" rows="3" placeholder="Digite o enunciado da questão" required></textarea>
            </div>
            <div class="col-md-3">
                <label class="form-label">Peso</label>
                <input type="number" class="form-control" name="questionWeight" min="0" step="0.1" placeholder="Ex: 1.0">
            </div>
        </div>
    `;

    return question;
}

if (addQuestionButton && questionsList) {
    addQuestionButton.addEventListener("click", () => {
        questionsList.appendChild(createQuestionItem());
        updateQuestionTitles();
    });

    questionsList.addEventListener("click", (event) => {
        const removeButton = event.target.closest(".remove-question");

        if (!removeButton) {
            return;
        }

        const questions = questionsList.querySelectorAll(".question-item");

        if (questions.length > 1) {
            removeButton.closest(".question-item").remove();
            updateQuestionTitles();
        }
    });

    updateQuestionTitles();
}
