package servlets.controllers;

import db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AppartsLinker", urlPatterns = "/appartslink")
public class AppartsLinker extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_s = request.getParameter("id");
        String uid_s = request.getParameter("uid");
        int id = Integer.parseInt(id_s);
        int uid = Integer.parseInt(uid_s);
        System.out.println("Apparts id: " + id);
        System.out.println("User id: " + uid);
        DBConnection db = new DBConnection();
        // Получаем название квартиры по id
        String appart_name = db.getAppartNameByID(id);
        // Получаем id юзера, который купил квартиру
        //int uid = 4;
        // Вычитаем из id юзера 3, первые 3 индекса в общей таблице - админы, в таблице users админов нет
        uid -= 3;
        if (uid < 1) System.out.println("ERROR: USER ID LESS THEN 1");
        else {
            // Получаем имя юзера, который купил квартиру
            String user_name = db.getUserNameByID(uid);
            // Заполняем название квартиры юзера в таблице users
            db.setAppartNameByUserLogin(user_name, appart_name);

            // Получаем id вашего админа
            int your_admin = db.getAdminID();
            // Привязываем пользователя к админу
            db.setUserToAdmin(uid, your_admin);

            request.setAttribute("error", "Покупка совершена, наш агент скоро свяжется с вами");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            //response.sendRedirect("list.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(request.getParameter("name"));
    }
}
