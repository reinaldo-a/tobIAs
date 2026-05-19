package com.tobias.controller.disciplines;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

import com.tobias.application.FlashMessage;
import com.tobias.dao.ActivityDAO;
import com.tobias.dao.DisciplineDAO;
import com.tobias.dao.MaterialDAO;
import com.tobias.dao.StudentDAO;
import com.tobias.dao.TeacherDAO;
import com.tobias.model.Activity;
import com.tobias.model.Aluno;
import com.tobias.model.Discipline;
import com.tobias.model.Material;
import com.tobias.model.Professor;
import com.tobias.model.User;

@WebServlet({
    "/Disciplines"
})

public class DisciplinesController extends HttpServlet {

    private DisciplineDAO dao = new DisciplineDAO();
    private StudentDAO studentDao = new StudentDAO();
    private TeacherDAO teacherDao = new TeacherDAO();
    private ActivityDAO activityDao = new ActivityDAO();
    private MaterialDAO materialDao = new MaterialDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        String action = request.getParameter("action");
        
        if (action == null){
            action = "";
        }
        switch (action){
            case "new":
                request.setAttribute("pageHeading","Nova Disciplina");
                request.setAttribute("contentPage","/WEB-INF/templates/disciplines/form.jsp");
                break;
            case "enter":
                request.setAttribute("pageHeading","Entrar na Disciplina");
                request.setAttribute("contentPage","/WEB-INF/templates/disciplines/form_enter.jsp");
                break;
            case "view":
                String idDiscipline = request.getParameter("id");
                int disciplineId = Integer.parseInt(idDiscipline);
                User loggedUser = (User) request.getSession().getAttribute("usuarioLogado");
                Discipline discipline = loggedUser != null ? dao.getById(disciplineId, loggedUser.getId()) : null;
                User participant = resolveParticipant(discipline, loggedUser);

                if(discipline == null || participant == null){
                    FlashMessage.set(request, "danger", "Você não participa dessa disciplina.");
                    response.sendRedirect(request.getContextPath() + "/Disciplines");
                    return;
                }

                discipline.setUserRole(participant.getRoleName());
                List<Activity> activities = activityDao.listActivitiesByDiscipline(disciplineId);
                List<Material> materials = materialDao.listByDiscipline(disciplineId);
                List<User> students = dao.listStudentsByDiscipline(disciplineId);

                FlashMessage.get(request);
                request.setAttribute("discipline", discipline);
                request.setAttribute("participant", participant);
                request.setAttribute("activities", activities);
                request.setAttribute("materials", materials);
                request.setAttribute("students", students);
                request.setAttribute("pageHeading","Sala de Aula");
                request.setAttribute("contentPage","/WEB-INF/templates/disciplines/discipline_details.jsp");
                break;
            default:
                
                FlashMessage.get(request);
                User user = (User) request.getSession().getAttribute("usuarioLogado");
                List<Discipline> lista = dao.listDisciplines(user.getId());
                request.setAttribute("listaDisciplines", lista);

                request.setAttribute("pageHeading", "Disciplinas");
                request.setAttribute("contentPage", "/WEB-INF/templates/disciplines/disciplines.jsp");
                break;
        }
        request.setAttribute("pageTitle", "Gestão Acadêmica");
        request.setAttribute("pageCss", "/assets/css/disciplines.css");
        request.getRequestDispatcher("/WEB-INF/templates/layout/base.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String action = request.getParameter("action");
        if (action == null){
            action = "";
        }

        switch(action){
            case "new":
                String name =  request.getParameter("name");
                String code =  request.getParameter("code");
                String description =  request.getParameter("description");

                Discipline d = new Discipline();
                d.setName(name);
                d.setCode(code);
                d.setDescription(description);

                User usuarioLogado = (User)request.getSession().getAttribute("usuarioLogado");
                if(usuarioLogado != null){
                    // Quem cria a disciplina e transformado/recuperado como Professor.
                    Professor professor = teacherDao.getOrCreateProfessor(usuarioLogado.getId());
                    d.setIdProfessor(professor.getProfessorId());
                }

                dao.save(d);

                FlashMessage.set(request, "success", "Disciplinas cadastrado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/Disciplines");
                break;
            case "enter":
                String codeTyped = request.getParameter("code");
                User user = (User) request.getSession().getAttribute("usuarioLogado");
                if(codeTyped != null && user != null){
                    int disciplineId = dao.getByCode(codeTyped);

                    if(disciplineId != -1){
                        // Quem entra pelo codigo e transformado/recuperado como Aluno.
                        Aluno aluno = studentDao.getOrCreateAluno(user.getId());

                        dao.enrollStudent(aluno.getStudentId(),disciplineId);
                        FlashMessage.set(request, "success", "Você entrou na disciplina com sucesso!");
                    }else{
                        FlashMessage.set(request, "danger", "Código de disciplina não encontrado.");
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Disciplines");
                break;
            default:
                User userLogado = (User) request.getSession().getAttribute("usuarioLogado");
                List<Discipline> lista = dao.listDisciplines(userLogado.getId());
                request.setAttribute("listaDisciplines", lista);

                request.setAttribute("pageHeading", "Disciplinas");
            request.setAttribute("contentPage", "/WEB-INF/templates/disciplines/disciplines.jsp");
                break;
        }
    }

    private User resolveParticipant(Discipline discipline, User loggedUser){
        // Decide qual subclasse de User representa o usuario dentro desta disciplina.
        if(discipline == null || loggedUser == null || discipline.getUserRole() == null){
            return null;
        }

        if("PROFESSOR".equals(discipline.getUserRole())){
            // Retorna um Professor, mas como tipo User para usar polimorfismo.
            return teacherDao.getProfessorByUserId(loggedUser.getId());
        }

        if("ALUNO".equals(discipline.getUserRole())){
            // Retorna um Aluno, mas como tipo User para usar polimorfismo.
            return studentDao.getAlunoByUserId(loggedUser.getId());
        }

        return null;
    }
}
