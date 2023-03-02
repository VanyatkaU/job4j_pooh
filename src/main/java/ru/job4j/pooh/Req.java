package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;


    public Req(String httpRequestType, String poohMode,
               String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] arrayLine = content.split("\\n");
        String[] parsRequest = arrayLine[0].split(" ");
        String httpRequestType = parsRequest[0];
        String[] modeSourceParam = parsRequest[1].split("/");
        String poohMode = modeSourceParam[1];
        String sourceName = modeSourceParam[2];
        String param;
        if (modeSourceParam.length == 4) {
            param = modeSourceParam[3];
        } else {
            param = arrayLine[arrayLine.length - 1].trim();
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}