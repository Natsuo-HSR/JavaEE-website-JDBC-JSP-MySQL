package db;

import model.Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBConnection {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static String url = "jdbc:mysql://localhost/appartsdb" +
            "?user=root&password=Bonia2012&serverTimezone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true";
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

            String query = "select * from allusers";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt(1);
                String login = rs.getString(2);
                String password = rs.getString(3);
                String role = rs.getString(4);
                System.out.println(id + " | " + login + " | " + password + " | " + role);
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while printDB()");
            System.out.println(sql);
            sql.printStackTrace();
        }
    }

    public int getUserID(String login, String password) {
        int id = -1;
        try {
            String query = "select id from allusers where (login='" + login + "' and password='" + password +"')";
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while getUserID()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return id;
    }

    public String getRole(String l, String p) {
        String role = null;
        try {
            String query = "select role from allusers where (login='" + l + "' and password='" + p+"')";
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                role = rs.getString(1);
            }
            else {
                role = "unknown";
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while searching role");
            System.out.println(sql);
            sql.printStackTrace();
        }

        return role;
    }

    public void regCustomer(String login, String password) {
        try {
            String query = "insert into allusers (login, password, role) values('" + login + "', '" + password + "', 'user')";
            stmt.executeUpdate(query);
            String query1 = "insert into users (login) values('" + login + "')";
            stmt.executeUpdate(query1);
        } catch (SQLException sql) {
            System.out.println("Something went wrong while regCustomer()");
            System.out.println(sql);
            sql.printStackTrace();
        }
    }

    public int getAdminID() {
        int id = -1;
        try {
            String query = "select * from admins order by users_count";
            rs = stmt.executeQuery(query);
            /*while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " +
                        rs.getString(3) + " " + rs.getInt(4));
            }*/
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while searching role");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return id;
    }

    public String getAppartNameByID(int id) {
        String name = "no";
        try {
            String query = "select name from apparts where id = '" + id + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while getAppartNameByID()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return name;
    }

    public String getUserNameByID(int id) {
        String name = "no";
        try {
            String query = "select login from users where id = '" + id + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while getUserNameByID()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return name;
    }

    public void setAppartNameByUserLogin(String login, String appart_name) {
        try {
            String query = "update users set appart_name='" + appart_name + "' where login='" + login + "'";
            stmt.executeUpdate(query);
        } catch (SQLException sql) {
            System.out.println("Something went wrong while setAppartNameByUserLogin()");
            System.out.println(sql);
            sql.printStackTrace();
        }
    }

    public void setUserToAdmin(int user_id, int admin_id) {
        String users_id = null;
        String user_log = null;
        int users_count = -1;
        try {
            String query = "select login, users_id, users_count from admins where id='" + admin_id + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                user_log = rs.getString(1);
                users_id = rs.getString(2);
                users_count = rs.getInt(3);
            }
            if (users_id == null) {
                users_id = "" + user_id;
            } else {
                users_id += "," + user_id;
            }
            users_count++;
            String query1 = "update admins set users_id='"+users_id+"', users_count='" + users_count
                    + "' where login='" + user_log + "'";
            stmt.executeUpdate(query1);
        } catch (SQLException sql) {
            System.out.println("Something went wrong while setUserToAdmin()");
            System.out.println(sql);
            sql.printStackTrace();
        }
    }

    public void printUserListByAdmin(int admin_id) {
        String users_list[] = new String[0];
        //String apparts_list[] = new String[0];
        ArrayList<String> apparts_list = new ArrayList<>();
        try {
            String query = "select users_id from admins where id='" + admin_id + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                users_list = rs.getString(1).split(",");
            }
            for (String s : users_list) {
                String q = "select appart_name from users where id='" + s + "'";
                rs = stmt.executeQuery(q);
                if (rs.next())
                    apparts_list.add(rs.getString(1));
            }
            for (String s : apparts_list)
                System.out.println(s);
        } catch (SQLException sql) {
            System.out.println("Something went wrong while printUserListByAdmin()");
            System.out.println(sql);
            sql.printStackTrace();
        }
    }

    public List<Admin> getUsersAppsByAdminsName(String adminName) {
        List<Admin> appsList = new ArrayList<>();
        try {
            String query = "select users_id from admins where login= '" + adminName + "'";
            rs = stmt.executeQuery(query);
            String res = "";
            if (rs.next()) {
                res = rs.getString(1);
            }
            if (res != null) {
            //String[] clients_s = res.split(",");
                TreeSet<String> clients = new TreeSet<>(Arrays.asList(res.split(",")));
                Iterator<String> it = clients.iterator();
                while (it.hasNext()) {
                    try {
                        String query1 = "select login, appart_name from users where id= '" + it.next() + "'";
                        rs = stmt.executeQuery(query1);
                        String appsName = "", userName = "";
                        if (rs.next()) {
                            userName = rs.getString(1);
                            appsName = rs.getString(2);
                        }
                        appsList.add(new Admin(userName, appsName));
                    } catch (SQLException sql) {
                        System.out.println("Something went wrong while ggetUsersAppsByAdminsName() IN FOR");
                        System.out.println(sql);
                        sql.printStackTrace();
                    }
                }
            } else {
                appsList = null;
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while getUsersAppsByAdminsName()");
            System.out.println(sql);
            sql.printStackTrace();
        }

        /*for (String a : appsList) {
            System.out.println(a);
        }*/

        return appsList;
    }

    public boolean isLoginFree(String login) {
        boolean free = true;
        try {
            String query = "select * from allusers where login = '" + login + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                free = false;
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while isLoginFree()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return free;
    }

    public boolean validatePassword(String login, String password) {
        boolean valid = false;
        try {
            String query = "select * from allusers where login = '" + login + "' and password='" + password + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                valid = true;
            }
        } catch (SQLException sql) {
            System.out.println("Something went wrong while validatePassword()");
            System.out.println(sql);
            sql.printStackTrace();
        }
        return valid;
    }

    public static void main(String[] args) {
        System.out.println(new DBConnection().validatePassword("123", "123"));
        //System.out.println(new DBConnection().getUserID("user1", "user1"));
        //System.out.println(new DBConnection().getAdminID());
        //String w = new DBConnection().getAppartNameByID(2);
        //new DBConnection().setAppartNameByUserLogin("user1", w);
        //new DBConnection().setUserToAdmin(3, 2);
        /*String i = new DBConnection().getAppartNameByID(2);
        new DBConnection().setAppartNameByUserLogin("user1", i);
        String j = new DBConnection().getAppartNameByID(9);
        new DBConnection().setAppartNameByUserLogin("user2", j);
        String k = new DBConnection().getAppartNameByID(5);
        new DBConnection().setAppartNameByUserLogin("user3", k);*/
        /*new DBConnection().setUserToAdmin(1, 2);
        new DBConnection().setUserToAdmin(2, 1);
        new DBConnection().setUserToAdmin(3, 1);*/
        //new DBConnection().printUserListByAdmin(3);
    }
}








/*try{
            String url = "jdbc:mysql://localhost/usersdb" +
                    "?user=root&password=Bonia2012&serverTimezone=Europe/Moscow&useSSL=false";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection con = DriverManager.getConnection(
                    url);){

                System.out.println("Connection to Store DB succesfull!");


                stmt = con.createStatement();

                String query = "select * from users";

                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt(1);
                    String login = rs.getString(2);
                    String password = rs.getString(3);
                    String role = rs.getString(4);
                    System.out.println(id + " | " + login + " | " + password + " | " + role);
                }
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
            ex.printStackTrace();
        }*/