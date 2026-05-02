<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Inclusão do componente de flash message -->
<jsp:include page="/WEB-INF/layout/partials/flash-message.jsp" />

<div class="custom-container">
  <div class="dashboard-shell">
    <section class="dashboard-hero card bg-gradient-primary text-white border-0">
      <div class="card-body p-4 p-lg-5">
        <div class="row align-items-center g-4">
          <div class="col-lg-7">
            <span class="hero-chip">Central de operacoes</span>
            <h1 class="fs-2 mt-3 mb-3">Seu painel academico esta pronto para acompanhar a rotina da escola.</h1>
            <p class="hero-copy mb-4">
              Visualize matriculas, acompanhe alertas do dia e acesse rapidamente os modulos mais usados da plataforma.
            </p>
            <div class="hero-actions">
              <a href="#" class="btn btn-light hero-primary-action">Ver agenda de hoje</a>
              <a href="#" class="btn btn-outline-light hero-secondary-action">Abrir relatorios</a>
            </div>
          </div>

          <div class="col-lg-5">
            <div class="hero-panel">
              <div class="hero-panel-label">Resumo do turno</div>
              <div class="hero-panel-value">94%</div>
              <p class="hero-panel-copy mb-0">Presenca media registrada nas turmas desta semana.</p>
              <div class="hero-progress mt-4">
                <span style="width: 94%;"></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="row g-4 mt-1">
      <div class="col-xl-3 col-md-6">
        <article class="card stats-card h-100">
          <div class="card-body">
            <div class="stat-card-top">
              <div class="stat-icon bg-primary-subtle text-primary">
                <i class="ti ti-book"></i>
              </div>
              <span class="stat-trend positive">+2 este mes</span>
            </div>
            <h2 class="stat-number">12</h2>
            <p class="stat-label">Disciplinas ativas</p>
          </div>
        </article>
      </div>

      <div class="col-xl-3 col-md-6">
        <article class="card stats-card h-100">
          <div class="card-body">
            <div class="stat-card-top">
              <div class="stat-icon bg-success-subtle text-success">
                <i class="ti ti-users"></i>
              </div>
              <span class="stat-trend positive">+18 novas matriculas</span>
            </div>
            <h2 class="stat-number">156</h2>
            <p class="stat-label">Alunos matriculados</p>
          </div>
        </article>
      </div>

      <div class="col-xl-3 col-md-6">
        <article class="card stats-card h-100">
          <div class="card-body">
            <div class="stat-card-top">
              <div class="stat-icon bg-warning-subtle text-warning">
                <i class="ti ti-calendar-time"></i>
              </div>
              <span class="stat-trend neutral">8 aulas hoje</span>
            </div>
            <h2 class="stat-number">24</h2>
            <p class="stat-label">Eventos na agenda</p>
          </div>
        </article>
      </div>

      <div class="col-xl-3 col-md-6">
        <article class="card stats-card h-100">
          <div class="card-body">
            <div class="stat-card-top">
              <div class="stat-icon bg-info-subtle text-info">
                <i class="ti ti-clipboard-check"></i>
              </div>
              <span class="stat-trend positive">87% concluidas</span>
            </div>
            <h2 class="stat-number">43</h2>
            <p class="stat-label">Tarefas pedagogicas</p>
          </div>
        </article>
      </div>
    </section>

    <section class="row g-4 mt-1">
      <div class="col-xl-8">
        <article class="card h-100">
          <div class="card-header section-header">
            <div>
              <span class="section-kicker">Monitoramento</span>
              <h5 class="card-title mb-0">Atividades recentes</h5>
            </div>
            <a href="#" class="section-link">Ver historico</a>
          </div>
          <div class="card-body">
            <div class="activity-list">
              <div class="activity-item">
                <div class="activity-icon bg-info-subtle text-info">
                  <i class="ti ti-user-plus"></i>
                </div>
                <div class="activity-content">
                  <p class="mb-1">Novo aluno matriculado: Joao Silva</p>
                  <small class="text-muted">Hoje, 08:30</small>
                </div>
              </div>

              <div class="activity-item">
                <div class="activity-icon bg-success-subtle text-success">
                  <i class="ti ti-check"></i>
                </div>
                <div class="activity-content">
                  <p class="mb-1">Avaliacao de Matematica finalizada pela turma 2B</p>
                  <small class="text-muted">Hoje, 10:15</small>
                </div>
              </div>

              <div class="activity-item">
                <div class="activity-icon bg-warning-subtle text-warning">
                  <i class="ti ti-calendar"></i>
                </div>
                <div class="activity-content">
                  <p class="mb-1">Reuniao de pais confirmada para sexta-feira as 18h</p>
                  <small class="text-muted">Ontem, 17:40</small>
                </div>
              </div>

              <div class="activity-item">
                <div class="activity-icon bg-primary-subtle text-primary">
                  <i class="ti ti-message-dots"></i>
                </div>
                <div class="activity-content">
                  <p class="mb-1">Coordenacao enviou um aviso para os professores do ensino medio</p>
                  <small class="text-muted">Ontem, 15:05</small>
                </div>
              </div>
            </div>
          </div>
        </article>
      </div>

      <div class="col-xl-4">
        <article class="card h-100">
          <div class="card-header section-header">
            <div>
              <span class="section-kicker">Execucao</span>
              <h5 class="card-title mb-0">Acoes rapidas</h5>
            </div>
          </div>
          <div class="card-body">
            <div class="quick-actions-grid">
              <a href="#" class="quick-action-card quick-action-btn">
                <i class="ti ti-plus"></i>
                <span>Novo aluno</span>
              </a>
              <a href="#" class="quick-action-card quick-action-btn">
                <i class="ti ti-book"></i>
                <span>Nova disciplina</span>
              </a>
              <a href="#" class="quick-action-card quick-action-btn">
                <i class="ti ti-calendar"></i>
                <span>Agendar aula</span>
              </a>
              <a href="#" class="quick-action-card quick-action-btn">
                <i class="ti ti-chart-bar"></i>
                <span>Ver relatorios</span>
              </a>
            </div>

            <div class="agenda-panel mt-4">
              <div class="agenda-panel-header">
                <strong>Proximo compromisso</strong>
                <span>14:00</span>
              </div>
              <p class="mb-1">Conselho pedagogico com coordenacao.</p>
              <small class="text-muted">Sala de reunioes 2</small>
            </div>
          </div>
        </article>
      </div>
    </section>
  </div>
</div>
