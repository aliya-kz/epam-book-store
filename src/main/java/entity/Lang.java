package entity;

import java.io.Serializable;

public class Lang implements Serializable {
    private long id;
    private String title;

    public Lang () {
    }

    public Lang(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
