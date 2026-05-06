const questionsList = document.getElementById("questions-list");
const addQuestionButton = document.getElementById("add-question");

function updateQuestionTitles() {
    const questions = questionsList.querySelectorAll(".question-item");

    questions.forEach((question, index) => {
        const title = question.querySelector(".question-number");
        title.textContent = `Questão ${index + 1}`;
        title.dataset.number = index + 1;
        question.querySelector(".remove-question").disabled = questions.length === 1;
    });
}

function createQuestionItem() {
    const question = document.createElement("div");
    question.className = "question-item";
    question.innerHTML = `
        <div class="question-item-header">
            <strong class="question-number" data-number="">Questão</strong>
            <button type="button" class="btn btn-sm btn-action btn-action-delete remove-question">
                <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                    <path d="M9 3h6l1 2h4v2H4V5h4l1-2Zm-2 6h10l-.7 12H7.7L7 9Zm2.1 2 .5 8h1.8l-.4-8H9.1Zm3.9 0v8h2v-8h-2Z"/>
                </svg>
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
