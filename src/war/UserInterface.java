package war;

import java.util.Scanner;

public class UserInterface {

    public String askUserForName() {
        System.out.println("\nWelcome! Lovely to see you!\nMy name is Karolina and I invited you over to play a game of cards.\n" +
                "I'm thinking... War.\nExcuse me, what was your name again?");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        System.out.println("\n" + userName + "? Enchanté!");
        return userName;
    }

    public int askIfUserWantsMorePlayers() {
        System.out.println("\nShall I invite some friends to join us? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        int basicNumberOfPlayers = 2;
        if (answer.equalsIgnoreCase("n")) {
            return basicNumberOfPlayers;
        } else if (answer.equalsIgnoreCase("y")) {
            return askUserForNumberOfAdditionalPlayers() + basicNumberOfPlayers;
        } else {
            return askIfUserWantsMorePlayers();
        }
    }

    private int askUserForNumberOfAdditionalPlayers() {
        System.out.println("How many exactly? (1 to 8)");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if (answer.matches("[1-8]")) {
            return Integer.parseInt(answer);
        } else {
            return askUserForNumberOfAdditionalPlayers();
        }
    }

    public String askUserForAction(String userName) {
        System.out.println("\nIt's your turn, " + userName + ". To turn up a card simply press Enter. If you would like to know " +
                "how many cards there are in your deck, type \"cards\" and press Enter.");
        Scanner scanner = new Scanner(System.in);
        // Do not shorten to return scanner.nextLine();
        String answer = scanner.nextLine();
        return answer;
    }

}