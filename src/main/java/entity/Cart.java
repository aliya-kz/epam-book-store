package entity;

public class Cart extends Entity {
    private int id;
    private int userId;
    private int [] bookIds;

    public Cart() {
    }

    public Cart(int userId, int[] bookIds) {
        this.userId = userId;
        this.bookIds = bookIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int[] getBookIds() {
        return bookIds;
    }

    public void setBookIds(int[] bookIds) {
        this.bookIds = bookIds;
    }

    @Override
    public int compareTo(Entity o) {
        return this.id - ((Cart) o).getId();
    }
}
