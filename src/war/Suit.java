package war;

public enum Suit {

    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
