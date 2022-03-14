package entity;

import java.io.Serializable;

public class Category implements Serializable {
    private long id;
    private String categoryName;
    private String lang;

    public Category () {}

    public Category(long id, String categoryName, String lang) {
        this.id = id;
        this.categoryName = categoryName;
        this.lang = lang;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
