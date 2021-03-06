package entity;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private long id;
    private String name;
    private String surname;
    private List <Address> addresses;
    private String email;
    private String phone;
    private List <Card> cards;
    private Date dateOfBirth;
    private String password;
    private List<Order> orders;
    private boolean isAdmin;
    private boolean isBlocked;

    public User() {
    }

    public User (long id) {
        this.id = id;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String surname, String email, String phone, Date dateOfBirth, String password, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long userID) {
        this.id = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
