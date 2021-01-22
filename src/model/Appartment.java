package model;

public class Appartment {
    private int id;
    private String name;
    private String type;
    private int square;
    private int cost;
    private String url;
    public Appartment(int i, String n, String t, int s, int c, String u) {
        id = i;
        name = n;
        type = t;
        square = s;
        cost  = c;
        url = u;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getSquare() {
        return square;
    }
    public int getCost() {
        return cost;
    }
    public String getURL() {
        return url;
    }
}
