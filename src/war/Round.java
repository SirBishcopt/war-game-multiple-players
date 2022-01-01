package war;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private List<Player> players;
    private List<Card> prize;
    private int indexOfPlayerWithHighestRank;
    private boolean isThereWar = false;
    private boolean isUserLooser = false;

    public Round(List<Player> players, List<Card> prize) {
        this.players = players;
        this.prize = prize;
    }

    public void turnUpCards() {
        for (Player player : players) {
            if (player.getIsActive()) {
                System.out.println(player.getName() + ": " + player.getFirstCard());
            }
        }
    }

    public void checkWhoHasHighestRankInRound() {
        int highestRank = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getIsActive() && players.get(i).getRankOfFirstCard() > highestRank) {
                indexOfPlayerWithHighestRank = i;
                highestRank = players.get(i).getRankOfFirstCard();
            }
        }
    }

    public boolean checkForWar() {
        int amountOfPlayersWithHighestRank = 0;
        for (Player player : players) {
            if (player.getIsActive() && player.getRankOfFirstCard() == players.get(indexOfPlayerWithHighestRank).getRankOfFirstCard()) {
                amountOfPlayersWithHighestRank++;
            }
        }
        isThereWar = true;
        return amountOfPlayersWithHighestRank > 1;
    }

    public void markPlayersThatParticipateInWar() {
        for (Player player : players) {
            if (player.getIsActive() && player.getRankOfFirstCard() == players.get(indexOfPlayerWithHighestRank).getRankOfFirstCard()) {
                player.setParticipatesInWar(true);
            }
        }
    }

    public void proceedWithWar() {
        int warsInRow = 0;
        while (isThereWar) {
            System.out.println("\nFinally! War is coming! Let's check whether every warrior has at least 4 cards...");
            checkWhoHasHighestRankInRoundInCaseOfWar();
            markPlayersThatParticipateInWarInCaseOfWar();
            deactivatePlayersThatLostInCaseOfWar();
            if (isUserLooser) break;
            checkForWarInCaseOfWar();
            if (isThereWar) {
                warsInRow++;
                System.out.println("\nAnd now every warrior should put 3 cards on the table, face down. Then, take another card " +
                        "and turn it up.");
                moveFirstCardsToPrizeInCaseOfWar(warsInRow);
                moveThreeCards();
                turnUpCardsInCaseOfWar();
                checkWhoHasHighestRankInRoundInCaseOfWar();
                checkForWarInCaseOfWar();
                if (!isThereWar) {
                    nameRoundWinner();
                    rewardRoundWinnerInCaseOfWar(warsInRow);
                    prize.clear();
                }

            } else {
                System.out.println("\nWhat a surprise! There are not enough warriors for war! Winner will get remaining cards " +
                        "of those who surrendered.");
                if (warsInRow == 0) {
                    for (Player player : players) {
                        player.setParticipatesInWar(true);
                    }
                }

                checkWhoHasHighestRankInRoundInCaseOfWar();
                checkForWarInCaseOfWar();
                if (isThereWar) {
                    markPlayersThatParticipateInWar();
                } else {
                    nameRoundWinner();
                    rewardRoundWinnerInCaseOfWar(warsInRow);
                    unmarkPlayersThatParticipatedInWar();
                    prize.clear();
                }
            }
        }

    }

    private void checkWhoHasHighestRankInRoundInCaseOfWar() {
        int highestRank = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getIsActive() && players.get(i).getParticipatesInWar() && players.get(i).getRankOfFirstCard() > highestRank) {
                indexOfPlayerWithHighestRank = i;
                highestRank = players.get(i).getRankOfFirstCard();
            }
        }
    }

    private void markPlayersThatParticipateInWarInCaseOfWar() {
        for (Player player : players) {
            if (player.getIsActive() && player.getParticipatesInWar() && player.getRankOfFirstCard() == players.get(indexOfPlayerWithHighestRank).getRankOfFirstCard()) {
                player.setParticipatesInWar(true);
            } else {
                player.setParticipatesInWar(false);
            }
        }
    }

    private void deactivatePlayersThatLostInCaseOfWar() {
        List<Card> temp = new ArrayList<>();
        int requiredCards = 5;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getIsActive() && players.get(i).getParticipatesInWar() && players.get(i).countCards() < requiredCards) {
                if (i == 0) {
                    isThereWar = false;
                    isUserLooser = true;
                } else if (i == 1) {
                    System.out.println("What a shame! I lost! You are quite an opponent, I admit. My cards will be added to this round's prize.");
                } else {
                    System.out.println("Awww... " + players.get(i).getName() + " lost. Their cards will be added to this round's prize.");
                }
                temp.addAll(players.get(i).getCards());
                players.get(i).setIsActive(false);
            }
        }
        prize.addAll(temp);
    }

    private void checkForWarInCaseOfWar() {
        int amountOfPlayersWithHighestRank = 0;
        for (Player player : players) {
            if (player.getIsActive() && player.getParticipatesInWar() && player.getRankOfFirstCard() == players.get(indexOfPlayerWithHighestRank).getRankOfFirstCard()) {
                amountOfPlayersWithHighestRank++;
            }
        }
        isThereWar = amountOfPlayersWithHighestRank > 1;
    }

    private void moveFirstCardsToPrizeInCaseOfWar(int warsInARow) {
        if (warsInARow == 1) {
            moveFirstCardsToPrize();
        } else {
            for (Player player : players) {
                if (player.getIsActive() && player.getParticipatesInWar()) {
                    prize.add(player.moveFirstCard());
                }
            }
        }
    }

    private void moveThreeCards() {
        for (Player player : players) {
            if (player.getIsActive() && player.getParticipatesInWar()) {
                prize.addAll(player.moveThreeCards());
            }
        }
    }

    private void turnUpCardsInCaseOfWar() {
        for (Player player : players) {
            if (player.getIsActive() && player.getParticipatesInWar()) {
                System.out.println(player.getName() + ": " + player.getFirstCard());
            }
        }
    }

    private void unmarkPlayersThatParticipatedInWar() {
        for (Player player : players) {
            player.setParticipatesInWar(false);
        }
    }

    private void rewardRoundWinnerInCaseOfWar(int warsInRow) {
        moveFirstCardsToPrizeInCaseOfWar(warsInRow);
        moveWonCards();
    }

    public boolean checkIfUserLost() {
        return isUserLooser;
    }

    public void nameRoundWinner() {
        System.out.println("\nLooks like we know, who won this round. " + players.get(indexOfPlayerWithHighestRank).getName() + ", good job!");
    }

    public void rewardRoundWinner() {
        moveFirstCardsToPrize();
        moveWonCards();
    }

    private void moveFirstCardsToPrize() {
        for (Player player : players) {
            if (player.getIsActive()) {
                prize.add(player.moveFirstCard());
            }
        }
    }

    private void moveWonCards() {
        players.get(indexOfPlayerWithHighestRank).addCardsToPlayersDeck(prize);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
