package servlets.controllers;

import db.DBConnection;
import model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Cabs", urlPatterns = "/cabs")
public class Cabs extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminName = request.getParameter("adminName");
        List<Admin> adminsList = new DBConnection().getUsersAppsByAdminsName(adminName);
        if (adminsList != null) {
            for (Admin a : adminsList) {
                System.out.println(a.getClientName() + " --> " + a.getAppName());
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("admins", adminsList);
            response.sendRedirect("cabs.jsp");
        } else {
            request.setAttribute("error", "Список клиентов данного агента пуст");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
