package com.tobias.controller.user;

import java.io.IOException;

import com.tobias.config.PasswordHash;
import com.tobias.dao.UserDAO;
import com.tobias.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
    "/user/register-form",
    "/user/register-save",
    "/user/update-form",
    "/user/update-save",
    "/user/read",
    "/user/delete"
})
public class UserController extends HttpServlet {

    // Trata as requisições GET, normalmente usadas para abrir páginas ou consultar dados.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Identifica qual URL chamou este servlet para decidir a ação correta.
        String action = request.getServletPath();

        switch (action) {
            case "/user/register-form":
                ShowRegidterFormes(request, response);
                return;
            case "/user/update-form":
                // TODO: carregar os dados do usuário e abrir o formulário de edição.
                
                return;
            case "/user/read":
                // TODO: listar ou exibir os dados do usuário.
                    
                return;
            default:
                // Qualquer rota GET não reconhecida é enviada para a página 404.
                response.sendRedirect(request.getContextPath() + "/404");
                return;
        }
    }

    // Trata as requisições POST, usadas para enviar dados de formulários.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Identifica qual operação foi enviada pelo formulário.
        String action = request.getServletPath();
        
        switch (action) {
            case "/user/register-save":
                registerUser(request, response);
                return;
            case "/user/read":
                // TODO: implementar leitura via POST, se essa rota for necessária.
                
                return;
            case "/user/update":
                // TODO: validar os dados enviados e atualizar o usuário.
                
                return;
            case "/user/delete":
                // TODO: remover o usuário informado na requisição.
                
                return;
            default:
                // Qualquer rota POST não reconhecida é enviada para a página 404.
                response.sendRedirect(request.getContextPath() + "/404");
                return;
        }
    }
    
    // Abre a página JSP com o formulário de cadastro de usuário.
    protected void ShowRegidterFormes(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/templates/user/register.jsp").forward(request, response);

    }

    // Recebe os dados do formulário, cria o usuário e salva no banco.
    protected void registerUser(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        // Garante que caracteres acentuados sejam lidos corretamente.
        request.setCharacterEncoding("UTF-8");

        // Captura os campos enviados pelo formulário de cadastro.
        String nome = request.getParameter("nome");
        String cpfText = request.getParameter("cpf");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            // Remove pontos, traços e outros caracteres do CPF antes de converter para número.
            long cpf = Long.parseLong(cpfText.replaceAll("\\D", ""));

            // Gera o hash da senha para evitar salvar a senha pura no banco.
            String senhaHash = PasswordHash.hashPassword(senha);

            // Cria o objeto User usando o hash da senha.
            User user = new User(nome, cpf, email, senhaHash, 0);

            // DAO responsável por executar a inserção do usuário no banco.
            UserDAO userDAO = new UserDAO();

            if (userDAO.inserirUsuario(user)) {
                // Se o cadastro der certo, o usuário é enviado para o dashboard.
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }

        } catch (Exception e) {
            // Em caso de erro, registra a falha no console para ajudar no debug.
            System.out.println("Erro ao cadastrar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        // Se algo falhar no cadastro, volta para o formulário.
        response.sendRedirect(request.getContextPath() + "/user/register-form");
    }
}   
