package model;

public class Admin {
    private String clientName;
    private String appName;
    public Admin(String c, String a)  {
        clientName = c;
        appName = a;
    }
    public String getClientName() {
        return clientName;
    }

    public String getAppName() {
        return appName;
    }
}
