package db;

import model.Appartment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppartsConnection {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static String url = "jdbc:mysql://localhost/appartsdb" +
            "?user=root&password=Bonia2012&serverTimezone=Europe/Moscow&useSSL=false";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url);

            System.out.println("Connection to Store DB succesfull!");
            stmt = con.createStatement();

        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    public void printDB() {
        try {
            //stmt = con.createStatement();

            String query = "select * from apparts";

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                int square = rs.getInt(4);
                int cost = rs.getInt(5);
                System.out.println("| " + id + " | " + name + " | " + type +
                        " | " + square + " | " + cost + " |");
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while printing DB");
            System.out.println(sql);
            sql.printStackTrace();
        }
    }

    public List<Appartment> getApparts() {
        ArrayList<Appartment> arr = new ArrayList<>();
        try {

            String query = "select * from apparts";

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                int square = rs.getInt(4);
                int cost = rs.getInt(5);
                String url = rs.getString(6);
                arr.add(new Appartment(id, name, type, square, cost, url));
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while executing getApparts()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return arr;
    }

    public List<Appartment> getAppartsByName(String appName) {
        ArrayList<Appartment> arr = new ArrayList<>();
        try {

            String query = "select * from apparts where type='" + appName + "'";

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                int square = rs.getInt(4);
                int cost = rs.getInt(5);
                String url = rs.getString(6);
                arr.add(new Appartment(id, name, type, square, cost, url));
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while executing getApparts()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return arr;
    }

    public List<Appartment> getAppartsByFilter(String appName, int costFrom, int costTo, int squareFrom, int squareTo) {
        ArrayList<Appartment> arr = new ArrayList<>();
        // Проверка
        String q1 ="", q2="", q3="";
        if (appName != null && !appName.equals("Тип")) {
            q1 = "type='" + appName + "'";
        }
        if (costFrom != -1 && costTo != -1 && costFrom<=costTo) {
            /*costFrom = 0;
            costTo = 999999999;*/
            q2 = "cost>=" +costFrom + " and cost<=" + costTo;
        }
        if (squareFrom != -1 && squareTo != -1 && squareFrom<=squareTo) {
            /*squareFrom = 0;
            squareTo = 99999;*/
            q3 = "square>=" +squareFrom + " and square<=" + squareTo;
        }
        if (!q1.equals("")) {
            q1 = "where " + q1;
        } else if (!q2.equals("")) {
            q2 = "where " + q2;
        } else if (!q3.equals("")) {
            q3 = "where " + q3;
        }
        if (!q1.equals("") && !q2.equals("") || !q3.equals("")) {
            q1 += " and ";
        } else if (!q2.equals("") && !q3.equals("")) {
            q2 += " and ";
        }
        try {

            String query = "select * from apparts " + q1 + q2 + q3;
            System.out.println(query);

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                int square = rs.getInt(4);
                int cost = rs.getInt(5);
                String url = rs.getString(6);
                arr.add(new Appartment(id, name, type, square, cost, url));
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while executing getApparts()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return arr;
    }

    public static void main(String[] args) {
        AppartsConnection ac = new AppartsConnection();
        List<Appartment> a = new ArrayList<>();
        String type = "Тип";
        int costFrom = 1000000;
        int costTo = 9999999;
        int squareFrom = -1;
        int squareTo = -1;
        a = ac.getAppartsByFilter(type, costFrom, costTo, squareFrom, squareTo);
        System.out.println("Results:");
        for (Appartment ap : a) {
            System.out.println(ap.getId() + " " + ap.getName() + " " + ap.getType() + " " + ap.getCost() + " " + ap.getSquare());
        }
    }
}