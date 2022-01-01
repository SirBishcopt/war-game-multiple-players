package war;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Gameplay {

    private List<Player> players = new ArrayList<>();
    private List<Card> prize;
    private DeckOfCards deck = new DeckOfCards();
    private boolean isUserWinner = false;
    private boolean isUserLooser = false;

    public void setupGame(String userName, int numberOfPlayers) {
        createPlayers(userName, numberOfPlayers);
        introducePlayers(numberOfPlayers);
        dealCards();
        includeRestOfCardsInPrize();
    }

    private void createPlayers(String userName, int numberOfPlayers) {
        String[] names = {userName, "Karolina", "Alice", "Lewis", "Maryann", "Jack", "Edith", "Chester", "Pepper", "Drake"};
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(names[i]));
        }
    }

    private void introducePlayers(int numberOfPlayers) {
        if (numberOfPlayers > 2) {
            System.out.println("\nThus let me introduce you to our charming company.");
            for (int i = 2; i < numberOfPlayers; i++) {
                introducePlayer(i);
            }
        }
    }

    private void introducePlayer(int playerIndex) {
        Random random = new Random();
        String[] introduction = {"This is ", "Say \"Hi!\" to ", "Meet my friend "};
        System.out.println(introduction[random.nextInt(introduction.length)] + players.get(playerIndex).getName() + ".");
        String[] phrase = {"Nice to meet you.", "I'm ready to play.", "Ummm... Hi, I guess."};
        System.out.println(players.get(playerIndex).getName() + ": " + phrase[random.nextInt(phrase.length)] + "\n");
    }

    private void dealCards() {
        System.out.println("Now it's time to deal cards. I'm shuffling the deck... And here you go. We can start the game.");
        deck.shuffleCards();
        int cardsPerPlayer = 52 / players.size();
        for (int i = 0; i < players.size(); i++) {
            List<Card> temp = deck.getCards().subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer);
            players.get(i).setCards(temp);
        }
    }

    private void includeRestOfCardsInPrize() {
        int cardsAsPrize = 52 % players.size();
        List<Card> temp = deck.getCards().subList(deck.getCards().size() - cardsAsPrize, deck.getCards().size());
        prize = new ArrayList<>(temp);
    }

    public void fulfillUsersRequest(String answer) {
        if (answer.toLowerCase().contains("cards")) {
            checkUsersDeck();
            System.out.println("Now, let's proceed with our game.\n");
        }
    }

    private void checkUsersDeck() {
        System.out.println("\nLet's count cards in your deck... " + players.get(0).countCards() + "!");
    }

    public void proceedWithRound() {
        Round round = new Round(players, prize);
        round.turnUpCards();
        round.checkWhoHasHighestRankInRound();
        boolean isThereWar = round.checkForWar();
        if (isThereWar) {
            round.markPlayersThatParticipateInWar();
            round.proceedWithWar();
            isUserLooser = round.checkIfUserLost();
        } else {
            round.nameRoundWinner();
            round.rewardRoundWinner();
            prize.clear();
        }
        players = round.getPlayers();
    }

    public void deactivatePlayersThatLost() {
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getIsActive() && players.get(i).countCards() == 0) {
                if (i == 1) {
                    System.out.println("What a shame! I lost! You are quite an opponent, I admit.");
                } else {
                    System.out.println("Awww... " + players.get(i).getName() + " lost.");
                }
                players.get(i).setIsActive(false);
            }
        }
    }

    public boolean checkIfGameFinishes() {
        checkIfUserWins();
        checkIfUserLooses();
        // Do not shorten to return checkIfUserWins() || checkIfUserLooses();
        return isUserWinner || isUserLooser;
    }

    private void checkIfUserWins() {
        int amountOfActivePlayers = 0;
        for (Player player : players) {
            if (player.getIsActive()) {
                amountOfActivePlayers++;
            }
        }
        isUserWinner = amountOfActivePlayers == 1;
    }

    private void checkIfUserLooses() {
        // Do not shorten to isUserLooser = players.get(0).countCards() == 0;
        if (players.get(0).countCards() == 0) {
            isUserLooser = true;
        }
    }

    public void endGame() {
        if (isUserLooser) {
            System.out.println("\nOuch! Today is definitely not your luckiest day. You lost.");
        } else {
            System.out.println("\nMy my! What a delightful surprise. You won! Congratulations!\n");
        }
    }

}