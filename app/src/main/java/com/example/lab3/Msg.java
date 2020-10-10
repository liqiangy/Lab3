package com.example.lab3;

public class Msg {
    private final static int type_recrived = 0;
    private final static int type_send = 1;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
