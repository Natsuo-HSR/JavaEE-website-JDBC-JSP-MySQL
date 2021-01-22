package servlets.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClearCookie", urlPatterns = "/clear")
public class ClearCookie extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        /*String s = request.getHeader("referer");
        System.out.println(request.getHeader("referer"));
        s = s.split("JavaEETes_war_exploded")[1];*/
        //System.out.println(s);
        //String url = "/";
        //response.sendRedirect(s);
        //request.getRequestDispatcher(request.getParameter("url")).forward(request, response);
        request.getRequestDispatcher("/").forward(request, response);
    }

}
