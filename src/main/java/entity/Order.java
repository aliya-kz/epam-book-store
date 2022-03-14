package entity;

import java.sql.Date;
import java.util.Map;
import java.util.Objects;

public class Order extends Entity {
    private long id;
    private long userId;
    private Map<Book, Integer> orderItems;
    private long statusId;
    private Map <Status, Date> statusHistory;
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

    public long getId() {
        return id;
    }

    public void setId(long orderID) {
        this.id = orderID;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Map<Book, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<Book, Integer> orderItems) {
        this.orderItems = orderItems;
    }

    public long getStatusId() {
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

    public Map<Status, Date> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(Map<Status, Date> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
