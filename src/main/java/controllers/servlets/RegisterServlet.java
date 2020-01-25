package controllers.servlets;

import controllers.Utils.ServletsUtils;
import controllers.errors.ValidationError;
import model.User;
import services.UserManagementService;
import services.impl.UserManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private UserManagementService service;
    private List<ValidationError> errors;

    @Override
    public void init() throws ServletException {
        service = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        errors = new ArrayList<>();
        String login = req.getParameter(ServletsUtils.USER_LOGIN);
        String name = req.getParameter(ServletsUtils.USER_NAME);
        String surName = req.getParameter(ServletsUtils.USER_SURNAME);
        String email = req.getParameter(ServletsUtils.USER_EMAIL);
        String password = req.getParameter(ServletsUtils.USER_PASSWORD);
        String repeatedPassword = req.getParameter(ServletsUtils.USER_REPEATED_PASSWORD);


        if (service.isUserLoginExists(login)) {
            ValidationError error = new ValidationError(ServletsUtils.LOGIN_ERROR_HEADER, ServletsUtils.LOGIN_IN_USE_ERROR_MESSAGE);
            errors.add(error);

        }
        if (service.isUserEmailExist(email)) {
            ValidationError error = new ValidationError(ServletsUtils.EMAIL_ERROR_HEADER, ServletsUtils.EMAIL_ERROR_MESSAGE);
            errors.add(error);
        }
        if (errors.size() > 0) {
            req.setAttribute(ServletsUtils.ERRORS_ATTRIBUTE_NAME, errors);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }
        User user = User.UserBuilder.getBuilder()
                .login(login)
                .email(email)
                .surname(surName)
                .password(password)
                .name(name)
                .build();

        service.saveUser(user);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);


    }


}
