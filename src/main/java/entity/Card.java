package entity;

import java.util.Objects;

public class Card extends Entity {
    private int id;
    private int userId;
    String cardNumber;

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Card(int id, int userId, String cardNumber) {
        this.id = id;
        this.userId = userId;
        this.cardNumber = cardNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
