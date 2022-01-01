package war;

public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank.name().toLowerCase() + " of " + suit.name().toLowerCase();
    }

    public Rank getRank() {
        return rank;
    }

}