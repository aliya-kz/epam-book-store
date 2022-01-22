package entity;

public class Category extends Entity {
    private int id;
    private String categoryName;
    private String lang;

    public Category () {}

    public Category(int id, String categoryName, String lang) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public int compareTo(Entity o) {
        return ((Category) o).getId() - this.id;
    }
}
