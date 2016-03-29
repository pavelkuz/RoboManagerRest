package kz.kuzovatov.pavel.models;

public class Robot extends NamedEntity {
    private String type;
    private int year;

    public Robot() {
    }

    public Robot(int id, String name, String type, int year) {
        super(id, name);
        this.type = type;
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
