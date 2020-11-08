package com.example.lab3;

public class Msg {


    private boolean isSent=false;
    private boolean isRecieved=false;
    private long id;
    private String content;
    private int type;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Msg(String content, long id,int type) {
        this.content = content;
        this.id=id;
        this.type=type;
    }
    public boolean isRecieved() {
        return isRecieved;
    }

    public void setRecieved(boolean recieved) {
        isRecieved = recieved;
    }

    public String getContent() {
        return content;
    }
}
