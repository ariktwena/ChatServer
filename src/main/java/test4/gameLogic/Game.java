//package test4.gameLogic;
//
//
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Game {
//
//    private final ArrayList<Question_board> easyQuestionsList;
//    private final ArrayList<Question_board> hardQuestionsList;
//    private final TUI tui;
//    private Player player;
//    private boolean gameStart;
//    private boolean exitGame;
//    private int numberOfAnswers;
//
//
//    public Game(ArrayList<Question_board> easyQuestionsList, ArrayList<Question_board> hardQuestionsList, Scanner scanner, PrintWriter writer) {
//        this.easyQuestionsList = easyQuestionsList;
//        this.hardQuestionsList = hardQuestionsList;
//        this.tui = new TUI(scanner, writer);
//        this.player = null;
//        this.gameStart = false;
//        this.exitGame = false;
//        numberOfAnswers = 0;
//    }
//
//    public void processor(){
//
//        createPlayer();
//
//        tui.weelcomeMessage(player.getName());
//
//        startGame();
//
//    }
//
//
//    //Create player
//    public Player createPlayer(){
//        //Create player name
//        player = new Player(tui.getPlayerName());
//        //Return player
//        return player;
//    }
//
//    public void startGame(){
//
//        //Player start game choice
//        String playerStartChoice = tui.playerInput().toLowerCase();
//
//        //If the player write "play" the game starts
//        if(playerStartChoice.equalsIgnoreCase("play")){
//
//            //Game starts and the right board is chosen
//            whatBoardToPlay();
//
//        } else {
//
//            //If the player answer doesn't match the answerIndex, we got to the switch statements
//            getSwitch(playerStartChoice);
//
//            if(!exitGame){
//                //Go back to category choice via "redirect
//                redirectAfterSwitch();
//            }
//
//        }
//    }
//
//    public void whatBoardToPlay(){
//        if (numberOfAnswers < 3) {
//            playTheGame(easyQuestionsList);
//        } else {
//
//            if(numberOfAnswers == 3){
//                tui.getHardBoardMessage();
//            }
//
//            playTheGame(hardQuestionsList);
//        }
//    }
//
//
//    //Show the board categories and selector to the player
//    public void playTheGame(ArrayList<Question_board> list){
//
//        int index_start, index_end;
//
//        //Set the gameStart to true so the player can be redirected
//        gameStart = true;
//
//        //Load the current status of the board
//        drawBoard(list);
//
//        //A list of the answers possibilities for the categories/questions
//        List<String> answerIndex = List.of("a", "b", "c", "d", "e", "f");
//
//        //Player category choice
//        String playerCategoryChoice = tui.playerCategoryInput().toLowerCase();
//
//        //We check if the players input is on the list and return the index number. Else it returns -1
//        int input_index_categoty = answerIndex.lastIndexOf(playerCategoryChoice);
//
//        //If the input_index is greater den -1, list contains the player answer/input
//        if(input_index_categoty >= 0){
//
//            //We calculate the index_start: 0, 5, 10, 15, 20, 25. Example third element with index 2 => 10
//            index_start = 5 * input_index_categoty;
//
//            //We calculate the index_start: 5, 10, 15, 20, 25, 30. Example third element with index 2 => 15
//            index_end = 5 + (5 * input_index_categoty);
//
//            //Get the category title to display
//            tui.getCategoryTitle(list.get(index_start).getCategory().getCategoryName());
//
//            //Get the available questions and non if they are answered
//            availableQuestionsInCategory(index_start, index_end, list);
//
//            //Player Question choice
//            String playerQuestionChoice = tui.playerQuestionInputChoice().toLowerCase();
//
//            //We check if the players input is on the list and return the index number. Else it returns -1
//            int input_index_question = answerIndex.lastIndexOf(playerQuestionChoice);
//
//            //If the input_index is greater den -1, list contains the player answer/input
//            if(input_index_question >= 0){
//
//                //input_index_question will be 0, 1, 2, 3 or 4
//                int question_index = input_index_question;
//
//                //We check if the question is NOT answered og else the methode will display a message to the player
//                if(getTheQuestion(list, index_start, question_index)){
//
//                    //Player answer
//                    String answer = tui.playerQuestionInputAnswer();
//
//                    //We check if the players answer matches the right answer
//                    validateAnswer(list, index_start, question_index, answer);
//
//                    //We update the number of questions that have been played
//                    numberOfAnswers += 1;
//                }
//
//                //We restart the categories by showing the "loader"
//                tui.loaderLong();
//
//                //Go back to category choice via "redirect
//                redirectAfterSwitch();
//
//            } else {
//
//                //If the player answer doesn't match the answerIndex, we got to the switch statements
//                getSwitch(playerQuestionChoice);
//
//                //Go back to category choice via "redirect
//                redirectAfterSwitch();
//
//            }
//        } else {
//
//            //If the player answer doesn't match the answerIndex, we got to the switch statements
//            getSwitch(playerCategoryChoice);
//
//            //Go back to category choice via "redirect
//            redirectAfterSwitch();
//
//        }
//
//
//    }
//
//    public void availableQuestionsInCategory(int index_start, int index_end, ArrayList<Question_board> list){
//        String[] choiseSpots = {"A", "B", "C", "D", "E"};
//        int choiseCount = 0;
//
//        for( int i = index_start ; i < index_end ; i++ ){
//            if(list.get(i).getAnswered() == null){
//
//                tui.availableQuestionsInCategoryAndPoint(choiseSpots[choiseCount], list.get(i).getScore());
//
//            } else {
//
//                tui.nonAvailableQuestionsInCategoryAndPoint(choiseSpots[choiseCount]);
//            }
//            choiseCount ++;
//        }
//    }
//
//    public void validateAnswer(ArrayList<Question_board> list, int index_start, int question_index, String answer){
//        if(list.get(index_start + question_index).getAnswer().equalsIgnoreCase(answer)){
//
//            //Message to the player
//            tui.correctAnswer(player.getName(), list.get(index_start + question_index).getScore());
//
//            //We add the score to the player
//            player.setScore(player.getScore() + list.get(index_start + question_index).getScore());
//
//            //We deactivet the question
//            list.get(index_start + question_index).setAnswered("---");
//
//        } else {
//            //Message to the player
//            tui.incorrectAnswer(player.getName(), list.get(index_start + question_index).getAnswer());
//
//            //We deactivet the question
//            list.get(index_start + question_index).setAnswered("---");
//
//        }
//    }
//
//    public boolean getTheQuestion(ArrayList<Question_board> list, int index_start, int question_index){
//        if(list.get(index_start + question_index).getAnswered() != null){
//            tui.questionHasAlreadyBeenPlayed();
//            return false;
//        } else {
//            tui.getQuestion(list.get(index_start + question_index).getQuestion());
//            return true;
//        }
//    }
//
//    public void getSwitch(String playerInput){
//        switch (playerInput) {
//            case "help":
//                tui.loader();
//                tui.getHelpGame();
//                break;
//            case "score":
//                tui.getScore(player.getName(), player.getScore());
//                break;
//            case "board":
//                tui.getBoardStatus(numberOfAnswers);
//                break;
//            case "back":
//                tui.loader();
//                whatBoardToPlay();
//                break;
//            case "exit":
//                tui.exitGame(player.getName());
//                exitGame = true;
//                break;
//            default:
//                tui.gameDefaultMessage();
//        }
//    }
//
//    public void redirectAfterSwitch(){
//        //Checks if the game has started though the gameStart boolean
//        if(gameStart && !exitGame){
//
//            //Redirects to the category selector
//            whatBoardToPlay();
//
//        } else if(!gameStart && !exitGame){
//
//            //Redirects to the start game screen where you can write "play"
//            startGame();
//
//        }
//    }
//
//    //public void createBoard(ArrayList<Question> list, int cat1, int cat2, int cat3, int cat4, int cat5, int cat6){
//    public void drawBoard(ArrayList<Question_board> list) {
//        String score1, score2, score3, score4, score5, score6;
//
//        //We print the header of the board (The list has 30 spots. Every 5 spot is a now line of questions)
//        tui.getBoardHeader();
//        tui.getBoardCategoryLeftAlignFormat("A: " + list.get(0).getCategory().getCategoryName(),
//                                            "B: " +list.get(5).getCategory().getCategoryName(),
//                                            "C: " +list.get(10).getCategory().getCategoryName(),
//                                            "D: " +list.get(15).getCategory().getCategoryName(),
//                                            "E: " +list.get(20).getCategory().getCategoryName(),
//                                            "F: " +list.get(25).getCategory().getCategoryName());
//
//        //We print 5 rows of point on the board
//        for( int i = 0 ; i < 5 ; i++ ){
//            tui.getBoardSeparator();
//            score1 = list.get(0 + i).getAnswered() == null ? String.valueOf(list.get(0 + i).getScore()) : list.get(0 + i).getAnswered();
//            score2 = list.get(5 + i).getAnswered() == null ? String.valueOf(list.get(5 + i).getScore()) : list.get(5 + i).getAnswered();
//            score3 = list.get(10 + i).getAnswered() == null ? String.valueOf(list.get(10 + i).getScore()) : list.get(10 + i).getAnswered();
//            score4 = list.get(15 + i).getAnswered() == null ? String.valueOf(list.get(15 + i).getScore()) : list.get(15 + i).getAnswered();
//            score5 = list.get(20 + i).getAnswered() == null ? String.valueOf(list.get(20 + i).getScore()) : list.get(20 + i).getAnswered();
//            score6 = list.get(25 + i).getAnswered() == null ? String.valueOf(list.get(25 + i).getScore()) : list.get(25 + i).getAnswered();
//
//
//            tui.getBoardScoreLeftAlignFormatRow(score1, score2, score3, score4, score5, score6);
//        }
//        tui.getBoardFooter();
//
//    }
//
//
//
//
//
//
//
//
//}
