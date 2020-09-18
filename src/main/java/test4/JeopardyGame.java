package test4;

import test3.Client2;
import test3.Game;
import test4.classes.Question_board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JeopardyGame {
    private volatile int waiting;
    private volatile JeopardyClient winner;
    private static final List<String> words =
            List.of("verysecret",
                    "ordblind",
                    "Stewerdesse",
                    "banan",
                    "ekstraordinær",
                    "kagemand",
                    "kaospilot",
                    "ferskensmag",
                    "StrengeSpil",
                    "speciallægepraksisplanlægningsstabiliseringsperiode"
            );
    List<JeopardyClient> clients;
    ArrayList<ArrayList<Question_board>> easyHardArray;
//    private JeopardyClient player1;
//    private JeopardyClient player2;

    public JeopardyGame(int capacity, List<JeopardyClient> clients, ArrayList<ArrayList<Question_board>> easyHardArray) {
        this.waiting = capacity;
        this.clients = clients;
        this.easyHardArray = easyHardArray;


        this.winner = null;
    }

    private List<JeopardyClient> randomPlayer() {
        List<JeopardyClient>  shuffledClients = new ArrayList<>(clients);
        Collections.shuffle(shuffledClients);
        return shuffledClients;
    }

    public void play(JeopardyClient client, GameParticipant participant) throws InterruptedException {


        await(participant);
//        this.player1 = randomPlayer().get(0);
//        this.player2 = randomPlayer().get(1);

        participant.weHaveAllThePlayers();

        participant.drawBoard(easyHardArray.get(0));

        choose1stCategory(client, participant);

        chooseCategory(client, participant);

//        participant.notifyGameStart(secretWord);
//        while (true) {
//            if (done() || participant.getAnswer().equals(secretWord)) {
//                break;
//            }
//        }
//        JeopardyClient winner = getAndSetWinner(participant.getClient());
//        participant.notifyWinner(winner);
    }

    public synchronized void await(GameParticipant participant) throws InterruptedException {
        waiting -= 1;
        if (waiting == 0) {
            this.notifyAll();
        } else {
            while (waiting > 0) {
                participant.WeAreWaitingForMorePlayers();
                this.wait();
            }
        }
    }

    public synchronized void choose1stCategory(JeopardyClient client, GameParticipant participant) {
//        turnPlayer1 = true;
        randomPlayer().get(0).setPlayerTurn(true);
        if (client.isPlayerTurn()) {
            participant.youStartTheGameAndChooseCategory();
        } else {
            participant.theOtherPlayerISChoosingACategory();
        }
    }

    public synchronized void chooseCategory(JeopardyClient client, GameParticipant participant) throws InterruptedException {
       int index_start = 0;
       int index_end = 0;
       String name = null;

        if (client.isPlayerTurn()) {

            name = client.getClientName();

            //A list of the answers possibilities for the categories/questions
            List<String> answerIndex = List.of("a", "b", "c", "d", "e", "f");

            //Player category choice
            String playerCategoryChoice = participant.playerCategoryChoice();

            //We check if the players input is on the list and return the index number. Else it returns -1
            int input_index_categoty = answerIndex.lastIndexOf(playerCategoryChoice);

            //If the input_index is greater den -1, list contains the player answer/input
            if(input_index_categoty >= 0) {
                //We calculate the index_start: 0, 5, 10, 15, 20, 25. Example third element with index 2 => 10
                index_start = 5 * input_index_categoty;

                //We calculate the index_start: 5, 10, 15, 20, 25, 30. Example third element with index 2 => 15
                index_end = 5 + (5 * input_index_categoty);

            }


        } else {
            this.wait();
        }

        this.notifyAll();

        //Get the category title to display
        participant.getCategoryTitle(easyHardArray.get(0).get(index_start).getCategory().getCategoryName(), name);

        //Get the available questions and non if they are answered
//        availableQuestionsInCategory(index_start, index_end, easyHardArray.get(0));


    }



    public synchronized JeopardyClient getAndSetWinner(JeopardyClient client) {
        if (winner == null) {
            winner = client;
        }
        return winner;
    }

    public synchronized boolean done() {
        return winner != null;
    }

    public static interface GameParticipant {

        void notifyGameStart(String secretWord);
        void notifyWinner(JeopardyClient client);
        String getAnswer();
        JeopardyClient getClient();

        void weHaveAllThePlayers();
        void WeAreWaitingForMorePlayers();

        void youStartTheGameAndChooseCategory();

        void theOtherPlayerISChoosingACategory();


        void drawBoard(ArrayList<Question_board> question_boards);

        String playerCategoryChoice();


        void getCategoryTitle(String categoryName, String clientName);
    }
}