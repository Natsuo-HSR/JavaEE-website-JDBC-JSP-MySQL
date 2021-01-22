package servlets.controllers;

import db.DBConnection;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegController", urlPatterns = "/regcustomer")
public class RegController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println("Login: " + login);
        System.out.println("Password: " + password);
        if (User.isLoginFree(login)) {
            new DBConnection().regCustomer(login, password);
            response.sendRedirect("index.jsp");
        }
        else {
            request.setAttribute("error", "Логин уже занят");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }
}
