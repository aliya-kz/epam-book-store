package entity;

import java.util.Objects;

public class Address extends Entity {

    private String address;
    private int id;
    private int userId;

    public Address(int id, int userId, String address) {
        this.address = address;
        this.id = id;
        this.userId = userId;
    }

    public Address() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Entity o) {
        return 0;
    }
}
