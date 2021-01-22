package model;

import db.DBConnection;
import others.StringValidator;
import servlets.controllers.CustomerController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        role = whatRole(login, password);
        id = new DBConnection().getUserID(login, password);
    }

    public String whatRole(String login, String password) {
        return new DBConnection().getRole(login, password);
    }

    public static boolean isLoginFree(String login) {
        return new DBConnection().isLoginFree(login);
    }
    public static boolean isPasswordValid(String login, String password) {
        return new DBConnection().validatePassword(login, password);
    }

    public int getID() { return id; }
    public String getRole() {
        return role;
    }
    public String getLogin() { return login; }
    public String getPassword() { return password; }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}