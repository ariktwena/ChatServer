package test4.readQuestions;


import java.io.InputStream;


public class InputStreamFromFile {

    public InputStream injectInputStreamToReader(){

        //We set the InputStream to our file in the resource folder
        java.io.InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("master_season1-35clean.tsv");

        // If the InputStream is null we display a message
        if(inputStream == null){

            System.out.println("The files InputStream is empty/null");

            return null;


        } else {

//            //We convert the inputStream through a converter "new InputStreamReader(inputStream)" and paas it to the constructor
//            QuestionReader questionReader = new QuestionReader(new InputStreamReader(inputStream));
//
//            ProcessQuestionsToArray processQuestionsToArray = new ProcessQuestionsToArray(questionReader);
//
//            ArrayList<Question> processedList = processQuestionsToArray.theProcessor();

            return inputStream;



//            ProcessQuestionsForGame processQuestionsForGameEasy = new ProcessQuestionsForGame(processedList, true);
//            ArrayList<Question_board> gameQuestionsForEasyGame = processQuestionsForGameEasy.theProcessor();
//
//            ProcessQuestionsForGame processQuestionsForGameHard = new ProcessQuestionsForGame(processedList, false);
//            ArrayList<Question_board> gameQuestionsForHardGame = processQuestionsForGameHard.theProcessor();
//
//            Game game = new Game(gameQuestionsForEasyGame, gameQuestionsForHardGame, scanner, writer);
//
//            game.processor();
        }


    }

//    public static void main(String[] args) throws IOException, ParseException {
//        new InputStreamFromFile().injectInputStreamToReader(new Scanner(System.in), new PrintWriter(System.out));
//    }

}
