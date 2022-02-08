package entity;

import java.sql.Date;

public class Message extends Entity{
    private int id;
    private int senderId;
    private int reveiverId;
    private String content;
    private Date date;

    public Message () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReveiverId() {
        return reveiverId;
    }

    public void setReveiverId(int reveiverId) {
        this.reveiverId = reveiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
