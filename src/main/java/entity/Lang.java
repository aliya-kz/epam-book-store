package entity;

public class Lang extends Entity{
    private int id;
    private String title;

    public Lang () {
    }

    public Lang(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(Entity o) {
        return this.id - ((Lang) o).getId();
    }
}
