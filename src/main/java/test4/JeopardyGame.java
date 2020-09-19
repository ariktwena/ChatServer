package test4;

import test4.classes.Question;
import test4.classes.Question_board;
import test4.tui.TUI;

import java.io.IOException;
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
    private volatile List<JeopardyClient> clients;
    private final ArrayList<ArrayList<Question_board>> easyHardArray;
    private int numberOfAnswers;
    private volatile boolean exitGame;
    private volatile boolean waitingforPlayer;
    private volatile String activePlayerName;
    private volatile int waitingGame;
    private volatile int index_start;
    private volatile int index_end;
    private volatile int question_index;
    private volatile String playerCategoryChoice;
    private final TUI tui;
    private boolean switchPlayer;

//    private JeopardyClient player1;
//    private JeopardyClient player2;

    public JeopardyGame(int capacity, List<JeopardyClient> clients, ArrayList<ArrayList<Question_board>> easyHardArray, TUI tui) {
        this.waiting = capacity;
        this.clients = clients;
        this.easyHardArray = easyHardArray;
        this.numberOfAnswers = 0;
        this.exitGame = false;
        this.waitingforPlayer = true;
        this.activePlayerName = "";
        this.waitingGame = 2;
        this.index_start = 0;
        this.index_end = 0;
        this.question_index = 0;
        this.playerCategoryChoice = "";
        this.tui = tui;
        this.switchPlayer = false;


        this.winner = null;
    }

//    private List<JeopardyClient> randomPlayer() {
//        List<JeopardyClient>  shuffledClients = new ArrayList<>(clients);
//        Collections.shuffle(shuffledClients);
//        return shuffledClients;
//    }

    public void play(JeopardyClient client, GameParticipant participant) throws InterruptedException, IOException {


        await(participant, client);
//        this.player1 = randomPlayer().get(0);
//        this.player2 = randomPlayer().get(1);

        participant.weHaveAllThePlayers();
//        tui.weHaveAllThePlayers();
//                        tui.loader();
//        tui.welcomeMessage(client.getClientName());
//                        tui.loaderLong();

//        participant.drawBoard(easyHardArray.get(0));

        choose1stCategory(client, participant);

        whatBoardToPlay(client, participant);





//        participant.notifyGameStart(secretWord);
//        while (true) {
//            if (done() || participant.getAnswer().equals(secretWord)) {
//                break;
//            }
//        }
//        JeopardyClient winner = getAndSetWinner(participant.getClient());
//        participant.notifyWinner(winner);
    }

    public synchronized void await(GameParticipant participant, JeopardyClient client) throws InterruptedException {
        waiting -= 1;
        if (waiting == 0) {
            this.notifyAll();
        } else {
            while (waiting > 0) {
//                tui.waitingForOnMorePlayer(client.getClientName());
                participant.WeAreWaitingForMorePlayers();
                this.wait();
            }
        }
    }

    public synchronized void awaitForPlayer() throws InterruptedException {
        waitingGame -= 1;
        if (waitingGame == 0) {
            this.notifyAll();
        } else {
            while (waitingGame > 0) {
                this.wait();
            }
        }
    }

//    public synchronized void awaitForPlayer1() throws InterruptedException {
//        int count = 0;
//
//        waitingGame -= 1;
//        if (waitingGame == 0) {
//            this.notifyAll();
//            count++;
//        } else {
//            while (waitingGame > 0) {
//                this.wait();
//            }
//            count++;
//        }
//
//        if(count == 2){
//            waitingGame = 2;
//        }
//    }

    public void choose1stCategory(JeopardyClient client, GameParticipant participant) {
//        turnPlayer1 = true;
        clients.get(0).setPlayerTurn(true);
        if (client.isPlayerTurn()) {
//            tui.youWonThetoes();
            participant.youStartTheGameAndChooseCategory();
        } else {
//            tui.youDidntWinThetoes();
            participant.theOtherPlayerISChoosingACategory();
        }
    }

    public void whatBoardToPlay(JeopardyClient client, GameParticipant participant) throws InterruptedException, IOException {
        if (numberOfAnswers < 30) {

            chooseCategory(client, participant, easyHardArray.get(0));

            awaitForPlayer();

            if(!exitGame){
                //Get the category title to display
//            tui.getCategoryTitle(easyHardArray.get(0).get(index_start).getCategory().getCategoryName());
                participant.getCategoryTitle(easyHardArray.get(0).get(index_start).getCategory().getCategoryName());

                chooseQuestion(client, participant, easyHardArray.get(0));
            }

            if(!exitGame){
                awaitForPlayer();

                displayTheQuestion(easyHardArray.get(0),index_start, question_index, participant, client);


                awaitForPlayer();

                getAnswerFromPlayer(easyHardArray.get(0), participant, client);

                awaitForPlayer();

                if(switchPlayer){
                    switchPlayer(client, participant);
                }

                //We restart the categories by showing the "loader"
                participant.loaderLong();
//            tui.loaderLong();

                this.switchPlayer = false;
                this.waitingGame = 2;

                //Go back to category choice via "redirect
                redirectAfterSwitch(participant, client);
            }

            if(client.isPlayerTurn()){
                participant.exitSwitch(client.getClientName());
            } else {
                participant.youAreTheWinner(client.getClientName());
            }


        } else {

            if(numberOfAnswers == 30){
                participant.getHardBoardMessage();
//                tui.getHardBoardMessage();
            }

            chooseCategory(client, participant, easyHardArray.get(1));
        }
    }

    public void chooseCategory(JeopardyClient client, GameParticipant participant, ArrayList<Question_board> list) throws InterruptedException, IOException {


//        participant.drawBoard(list);
        participant.drawBoard(list);


        if (client.isPlayerTurn()) {

            //A list of the answers possibilities for the categories/questions
            List<String> answerIndex = List.of("a", "b", "c", "d", "e", "f");

            //Player category choice
//            playerCategoryChoice = tui.playerCategoryInput();
            playerCategoryChoice = participant.playerCategoryChoice();


            //We check if the players input is on the list and return the index number. Else it returns -1
            int input_index_categoty = answerIndex.lastIndexOf(playerCategoryChoice);

            //If the input_index is greater den -1, list contains the player answer/input
            if(input_index_categoty >= 0) {
                //We calculate the index_start: 0, 5, 10, 15, 20, 25. Example third element with index 2 => 10
                index_start = 5 * input_index_categoty;

                //We calculate the index_start: 5, 10, 15, 20, 25, 30. Example third element with index 2 => 15
                index_end = 5 + (5 * input_index_categoty);
                
            } else {

                //If the player answer doesn't match the answerIndex, we got to the switch statements
                getSwitch(playerCategoryChoice, participant, client);

                //Go back to category choice via "redirect
                redirectAfterSwitch(participant, client);

            }
        } else {

            this.waitingGame = 2;
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e){
                throw new UnsupportedOperationException("You got an InterruptedException: " + e.getMessage());
            }
        }
    }

    public void chooseQuestion(JeopardyClient client, GameParticipant participant, ArrayList<Question_board> list) throws InterruptedException, IOException {



        if (client.isPlayerTurn()) {

            //A list of the answers possibilities for the categories/questions
            List<String> answerIndex = List.of("a", "b", "c", "d", "e");

//            activePlayerName = client.getClientName();

            //Get the available questions and non if they are answered
            availableQuestionsInCategory(index_start, index_end, list, participant);

            //Player Question choice
            String playerQuestionChoice = participant.playerQuestionInputChoice();
//            String playerQuestionChoice = tui.playerQuestionInputChoice().toLowerCase();

            //We check if the players input is on the list and return the index number. Else it returns -1
            int input_index_question = answerIndex.lastIndexOf(playerQuestionChoice);

            //If the input_index is greater den -1, list contains the player answer/input
            if(input_index_question >= 0) {

                //input_index_question will be 0, 1, 2, 3 or 4
                question_index = input_index_question;
            } else {

                //If the player answer doesn't match the answerIndex, we got to the switch statements
                getSwitch(playerQuestionChoice, participant, client);

                //Go back to category choice via "redirect
                redirectAfterSwitch(participant, client);

            }
        } else {
            this.waitingGame = 2;
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e){
                throw new UnsupportedOperationException("You got an InterruptedException: " + e.getMessage());
            }
        }
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


    public void availableQuestionsInCategory(int index_start, int index_end, ArrayList<Question_board> list, GameParticipant participant){


        String[] choiseSpots = {"A", "B", "C", "D", "E"};
        int choiseCount = 0;

        for( int i = index_start ; i < index_end ; i++ ){
            if(list.get(i).getAnswered() == null){

                participant.availableQuestionsInCategoryAndPoint(choiseSpots[choiseCount], list.get(i).getScore());

//                tui.availableQuestionsInCategoryAndPoint(choiseSpots[choiseCount], list.get(i).getScore());

            } else {

                participant.nonAvailableQuestionsInCategoryAndPoint(choiseSpots[choiseCount]);

//                tui.nonAvailableQuestionsInCategoryAndPoint(choiseSpots[choiseCount]);
            }
            choiseCount ++;
        }
    }

    public void getAnswerFromPlayer(ArrayList<Question_board> list, GameParticipant participant, JeopardyClient client) throws InterruptedException {


        if (client.isPlayerTurn()) {
            if (getTheQuestion(list, index_start, question_index, participant)) {

                //Player answer
                String answer = participant.playerQuestionInputAnswer();
//                String answer = tui.playerQuestionInputAnswer();

                //We check if the players answer matches the right answer
                validateAnswer(list, index_start, question_index, answer, participant, client);

                //We update the number of questions that have been played
                numberOfAnswers += 1;
            }
        } else {

            this.waitingGame = 2;
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e){
                throw new UnsupportedOperationException("You got an InterruptedException: " + e.getMessage());
            }
        }

    }


    public void displayTheQuestion(ArrayList<Question_board> list, int index_start, int question_index, GameParticipant participant, JeopardyClient client) throws IOException, InterruptedException {
        if(list.get(index_start + question_index).getAnswered() != null){
            participant.questionHasAlreadyBeenPlayed();
//            tui.questionHasAlreadyBeenPlayed();
            chooseQuestion(client, participant, list);
            //waitingGame = 2;
        } else {
            participant.getQuestion(list.get(index_start + question_index).getQuestion());
//            tui.getQuestion(list.get(index_start + question_index).getQuestion());
        }
    }

    public boolean getTheQuestion(ArrayList<Question_board> list, int index_start, int question_index, GameParticipant participant){
        if(list.get(index_start + question_index).getAnswered() != null){
            participant.questionHasAlreadyBeenPlayed();
//            tui.questionHasAlreadyBeenPlayed();
            return false;
        } else {
            participant.getQuestion(list.get(index_start + question_index).getQuestion());
//            tui.getQuestion(list.get(index_start + question_index).getQuestion());
            return true;
        }
    }

    public void validateAnswer(ArrayList<Question_board> list, int index_start, int question_index, String answer, GameParticipant participant, JeopardyClient client){
        if(list.get(index_start + question_index).getAnswer().equalsIgnoreCase(answer)){

            //Message to the player
            participant.correctAnswer(client.getClientName(), list.get(index_start + question_index).getScore());
//            tui.correctAnswer(client.getClientName(), list.get(index_start + question_index).getScore());

            //We add the score to the player
            client.setScore(client.getScore() + list.get(index_start + question_index).getScore());

            //We deactivet the question
            list.get(index_start + question_index).setAnswered("---");

        } else {
            //Message to the player
            participant.incorrectAnswer(client.getClientName(), list.get(index_start + question_index).getAnswer());
//            tui.incorrectAnswer(client.getClientName(), list.get(index_start + question_index).getAnswer());

            //We subtract the score to the player
            client.setScore(client.getScore() - list.get(index_start + question_index).getScore());

            //We deactivet the question
            list.get(index_start + question_index).setAnswered("---");

            this.switchPlayer = true;

        }
    }

    public void getSwitch(String playerInput, GameParticipant participant, JeopardyClient client) throws InterruptedException, IOException {
        switch (playerInput) {
            case "help":
//                tui.getHelpGame();
                participant.helpSwitch();
                break;
            case "score":
                if(client.isPlayerTurn()){
//                    tui.getScore(client.getClientName(), client.getScore());
                    participant.getScoreSwitch(client.getClientName(), client.getScore());
                }
                break;
            case "board":
//                tui.getBoardStatus(numberOfAnswers);
                participant.getBoardSwitch(numberOfAnswers);

                break;
            case "exit":
                if(client.isPlayerTurn()){
                    client.setExitGame(true);
                }
                exitGame = true;
                break;
            default:
                participant.defaultSwitch();
//                tui.gameDefaultMessage();

        }
    }

    public void redirectAfterSwitch(GameParticipant participant, JeopardyClient client) throws InterruptedException, IOException {
        //Checks if the game has started though the gameStart boolean
        if(!exitGame){

            //Redirects to the category selector
            whatBoardToPlay(client, participant);

        }

    }


    public synchronized void switchPlayer(JeopardyClient client, GameParticipant participant){
        if(client.isPlayerTurn()){
            client.setPlayerTurn(false);
            participant.youLostYourTurn();
        } else {
            client.setPlayerTurn(true);
            participant.itsYourTurn();
        }
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


        void getCategoryTitle(String categoryName);


        void helpSwitch();
        void getScoreSwitch(String clientName, int score);
        void getBoardSwitch(int numberOfAnswers);
        void backSwitch();
        void exitSwitch(String clientName) throws IOException;
        void defaultSwitch();


        void youAreTheWinner(String clientName) throws IOException;

        void getHardBoardMessage();

        void availableQuestionsInCategoryAndPoint(String choiseSpot, int score);

        void nonAvailableQuestionsInCategoryAndPoint(String choiseSpot);

        String playerQuestionInputChoice();

        void loaderLong();

        String playerQuestionInputAnswer();

        void correctAnswer(String clientName, int score);

        void incorrectAnswer(String clientName, String answer);

        void questionHasAlreadyBeenPlayed();

        void getQuestion(String question);


        void youLostYourTurn();

        void itsYourTurn();
    }
}