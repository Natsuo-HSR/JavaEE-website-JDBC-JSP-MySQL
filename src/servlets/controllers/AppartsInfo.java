package servlets.controllers;

import db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AppartsInfo", urlPatterns = "/appartsinfo")
public class AppartsInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_s = request.getParameter("id");
        int id = Integer.parseInt(id_s);
        DBConnection db = new DBConnection();
        // Получаем название квартиры по id
        String appart_name = db.getAppartNameByID(id);
        System.out.println("IN APPSINFO NAME= " + appart_name);
        request.setAttribute("appname", appart_name);

        request.getRequestDispatcher("info.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
