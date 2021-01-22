package servlets.controllers;

import db.DBConnection;
import model.User;
import others.StringValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerController", urlPatterns = "/processcustomer")
public class CustomerController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*RequestCustomer customer = RequestCustomer.fromRequestParameters(request);
        customer.setAsRequestAttributes(request);
        List violations = customer.validate();

        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }*/
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (!User.isLoginFree(login)) {
            if (User.isPasswordValid(login, password)) {
                User user = new User(login, password);
                String role = user.getRole();

                request.setAttribute("login", login);
                request.setAttribute("password", password);
                request.setAttribute("role", role);

                System.out.println("YOUR ROLE IS: " + user.getRole());

                Cookie cookie = new Cookie("user", user.getLogin());
                cookie.setMaxAge(600);
                response.addCookie(cookie);
                Cookie cookie1 = new Cookie("id", Integer.toString(user.getID()));
                cookie1.setMaxAge(600);
                response.addCookie(cookie1);
                Cookie cookie2 = new Cookie("role", user.getRole());
                cookie2.setMaxAge(600);
                response.addCookie(cookie2);

                response.sendRedirect(request.getParameter("url"));
            } else {
                request.setAttribute("error", "Неправильный логин или пароль");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Пользователь не найден");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        //String url = determineUrl(violations);
        /*String s = request.getHeader("referer");
        s = s.split("JavaEETes_war_exploded")[1];*/
        //RequestDispatcher dispatcher=getServletContext().getRequestDispatcher( "/WEB-INF/list.jsp" );
        //dispatcher.forward( request, response );
        //getServletContext().getRequestDispatcher(s).forward(request, response);
        //request.getRequestDispatcher(request.getParameter("/")).forward(request, response);
        //System.out.println(request.getContextPath() + "/appartslist");
        /*response.sendRedirect(request.getParameter("url"));*/
        //request.getRequestDispatcher(request.getParameter("url")).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        doPost(request, response);
    }

    private String determineUrl(List violations) {
        if (!violations.isEmpty()) {
            return "/";

        } else {
            return "/WEB-INF/u/showProfileData.jsp";
            /*String s = request.getHeader("referer");
            s = s.split("JavaEETes_war_exploded")[1];
            return s;*/
        }
    }

    private static class RequestCustomer {

        private String login;
        private String password;
        private String role;

        private RequestCustomer(String login, String password, String role) {
            this.login = login;
            this.password = password;
            this.role = role;
        }

        public String getRole() {
            return role;
        }
        public String getLogin() { return login; }

        public static RequestCustomer fromRequestParameters(HttpServletRequest request) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String role = new DBConnection().getRole(login, password);
            return new RequestCustomer(login, password, role);
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("login", login);
            request.setAttribute("password", password);
            request.setAttribute("role", role);
        }

        public List validate() {
            List violations = new ArrayList<>();
            if (!StringValidator.validate(login)) {
                violations.add("Имя является обязательным полем");
            }
            if (!StringValidator.validate(password)) {
                violations.add("Фамилия является обязательным полем");
            }
            return violations;
        }

    }

}
