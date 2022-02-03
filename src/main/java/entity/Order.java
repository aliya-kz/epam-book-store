package entity;

import java.sql.Date;
import java.util.Map;

public class Order extends Entity {
    private int id;
    private int userId;
    private Map<Book, Integer> orderItems;
    private int statusId;
    private Date date;
    private Address address;
    private int cost;

    public Order() {
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int orderID) {
        this.id = orderID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user) {
        this.userId = user;
    }

    public Map<Book, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<Book, Integer> orderItems) {
        this.orderItems = orderItems;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int compareTo(Entity o) {
        return this.id - ((Order) o).getId();
    }
}
