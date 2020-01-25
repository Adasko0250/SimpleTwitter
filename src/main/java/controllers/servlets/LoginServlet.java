package controllers.servlets;

import controllers.Utils.ServletsUtils;
import controllers.errors.ValidationError;
import services.UserManagementService;
import services.impl.UserManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final int SECONDS_IN_DAY = 60 * 60 * 24;
    private UserManagementService service;


    @Override
    public void init() throws ServletException {
        service = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;
        if (null != req && null != req.getCookies()) {
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals(ServletsUtils.USER_LOGIN)) {
                    login = c.getValue();
                }
                if (c.getName().equals(ServletsUtils.USER_PASSWORD)) {
                    password = c.getValue();
                }
            }
        }
        if (login != null && password != null) {
            req.setAttribute(ServletsUtils.USER_LOGIN, login);
            req.setAttribute(ServletsUtils.USER_PASSWORD, password);
            doPost(req, resp);
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ValidationError> errors = new ArrayList<>();
        String login = req.getParameter(ServletsUtils.USER_LOGIN);
        String password = req.getParameter(ServletsUtils.USER_PASSWORD);
        Boolean isRememberChecked = (ServletsUtils.CHECKBOX_CHECKED).equals(req.getParameter(ServletsUtils.REMEMBER));

        if (login == null && password == null) {
            login = (String) req.getAttribute(ServletsUtils.USER_LOGIN);
            password = (String) req.getAttribute(ServletsUtils.USER_PASSWORD);
        }

        if (!service.isUserLoginExists(login)) {
            errors.add(new ValidationError(ServletsUtils.LOGIN_ERROR_HEADER, ServletsUtils.LOGIN_NOT_EXIST_ERROR_MESSAGE));
            req.setAttribute(ServletsUtils.ERRORS_ATTRIBUTE_NAME, errors);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        if (!service.isUserValid(login, password)) {
            errors.add(new ValidationError(ServletsUtils.PASSWORD_ERROR_HEADER, ServletsUtils.WRONG_PASSWORD_ERROR_MESSAGE));
            req.setAttribute(ServletsUtils.ERRORS_ATTRIBUTE_NAME, errors);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }
        //save login in session (in server)
        req.getSession().setAttribute(ServletsUtils.USER_LOGIN, login);

        if (isRememberChecked) {
            /// cookie saving login on web
            Cookie loginCookie = new Cookie(ServletsUtils.USER_LOGIN, login);
            Cookie passwordCookie = new Cookie(ServletsUtils.USER_PASSWORD, password);
            loginCookie.setMaxAge(SECONDS_IN_DAY);
            passwordCookie.setMaxAge(SECONDS_IN_DAY);
            resp.addCookie(loginCookie);
            resp.addCookie(passwordCookie);
        }

        req.getRequestDispatcher("users").forward(req, resp);
    }

}
