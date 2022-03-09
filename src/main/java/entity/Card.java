package entity;

import java.util.Objects;

public class Card extends Entity {
    private long id;
    private long userId;
    String cardNumber;

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Card(long id, long userId, String cardNumber) {
        this.id = id;
        this.userId = userId;
        this.cardNumber = cardNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
