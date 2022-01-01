package war;

public class Main {

    public static void main(String[] args) {

        UserInterface userInterface = new UserInterface();
        String userName = userInterface.askUserForName();
        int numberOfPlayers = userInterface.askIfUserWantsMorePlayers();

        Gameplay game = new Gameplay();
        game.setupGame(userName, numberOfPlayers);

        boolean isGameFinished = false;

        while (!isGameFinished) {
            String userRequest = userInterface.askUserForAction(userName);
            game.fulfillUsersRequest(userRequest);
            game.proceedWithRound();
            game.deactivatePlayersThatLost();
            isGameFinished = game.checkIfGameFinishes();
        }

        game.endGame();

    }
}
