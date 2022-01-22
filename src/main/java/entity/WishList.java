package entity;

import java.util.List;

public class WishList extends Entity{
    private int id;
    private int userId;
    private List<Book> books;

    public WishList() {
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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

    @Override
    public int compareTo(Entity o) {
        return this.id - ((WishList) o).getId();
    }
}
