package war;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> cards;
    private boolean isActive = true;
    private boolean participatesInWar = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public int countCards() {
        return cards.size();
    }

    public Card getFirstCard() {
        return cards.get(0);
    }

    public Card moveFirstCard() {
        return cards.remove(0);
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean getParticipatesInWar() {
        return participatesInWar;
    }

    public void setParticipatesInWar(boolean participatesInWar) {
        this.participatesInWar = participatesInWar;
    }

    public int getRankOfFirstCard() {
        return cards.get(0).getRank().ordinal();
    }

    public void addCardsToPlayersDeck(List<Card> prize) {
        cards.addAll(prize);
    }

    public List<Card> moveThreeCards() {
        List<Card> tempList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tempList.add(cards.remove(0));
        }
        return tempList;
    }

}
