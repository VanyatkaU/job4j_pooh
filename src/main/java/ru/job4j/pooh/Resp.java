package ru.job4j.pooh;

public class Resp {

    public static final String STATUS404 = "404";

    public static final String STATUS200 = "200";

    public static final String STATUSTEXT = "";

    private  final String text;
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {

        return text;
    }

    public String status() {
        return status;
    }
}
