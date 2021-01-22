package servlets.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.AppartsConnection;
import model.Appartment;

@WebServlet(name = "AppartsController", urlPatterns = "/appartslist")
public class AppartsController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String optName = request.getParameter("type");
        System.out.println("TYPE=" + optName);
        String costfrom = request.getParameter("costfrom");
        System.out.println("COST=" + costfrom);
        String costto = request.getParameter("costto");
        String squarefrom = request.getParameter("squarefrom");
        String squareto = request.getParameter("squareto");

        List<Appartment> apparts = new ArrayList<>();
        int costFrom = -1, costTo = -1, squareFrom = -1, squareTo = -1;
        if (costfrom != null)   {
            if (!costfrom.equals(""))
                costFrom = Integer.parseInt(costfrom);
        }
        if (costto != null)   {
            if (!costto.equals(""))
                costTo = Integer.parseInt(costto);
        }
        if (squarefrom != null)   {
            if (!squarefrom.equals(""))
                squareFrom = Integer.parseInt(squarefrom);
        }
        if (squareto != null)   {
            if (!squareto.equals(""))
                squareTo = Integer.parseInt(squareto);
        }
        System.out.println("ALL ARGS: " + optName + " | " + costFrom + " | " + costTo + " | " + squareFrom + " | " + squareTo);
        apparts = new AppartsConnection().getAppartsByFilter(optName, costFrom, costTo, squareFrom, squareTo);
        HttpSession session = request.getSession(true);
        session.setAttribute("apparts", apparts);
        response.sendRedirect("list.jsp");


        /*if (optName != null && !optName.equals("Тип") || costfrom != null || costto != null || squarefrom != null || squareto != null) {
            if (costfrom != null)   {
                costFrom = Integer.parseInt(costfrom);
            }
            if (costto != null)   {
                costTo = Integer.parseInt(costto);
            }
            if (squarefrom != null)   {
                squareFrom = Integer.parseInt(squarefrom);
            }
            if (squareto != null)   {
                squareTo = Integer.parseInt(squareto);
            }
            apparts = new AppartsConnection().getAppartsByFilter(optName, costTo, costFrom, squareFrom, squareTo);
            System.out.println("Name field in true: " + optName);
            //apparts = new AppartsConnection().getAppartsByName(optName);
            //System.out.println("Apparts list in true " + apparts);
            HttpSession session = request.getSession(true);
            session.setAttribute("apparts", apparts);
            //request.setAttribute("apparts", apparts);
            response.sendRedirect("list.jsp");
            //getServletContext().getRequestDispatcher("/list.jsp").forward(request, response);
        }
        else {
            System.out.println("Name field in else: " + optName);
            apparts = new AppartsConnection().getApparts();
            HttpSession session = request.getSession(true);
            session.setAttribute("apparts", apparts);
            System.out.println("Apparts list in false " + apparts);
            //request.setAttribute("apparts", apparts);
            System.out.println(request.getParameter("name1"));
            //response.sendRedirect("http://localhost:8080/JavaEETes_war_exploded/list.jsp");
            getServletContext().getRequestDispatcher("/list.jsp").forward(request, response);
        }*/

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        //System.out.println("ASD");
        doPost(request, response);
    }
}

