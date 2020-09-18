package test4.readQuestions;


import test4.classes.Question;
import test4.classes.Question_board;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;


public class InputStreamFromFile {

    public InputStream injectInputStreamToReader() throws IOException, ParseException {

        //We set the InputStream to our file in the resource folder
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("master_season1-35clean.tsv");

        // If the InputStream is null we display a message
        if(inputStream == null){

            System.out.println("The files InputStream is empty/null");

            return null;


        } else {

            return inputStream;

            //return inputStream;

//            //We convert the inputStream through a converter "new InputStreamReader(inputStream)" and paas it to the constructor
//            QuestionReader questionReader = new QuestionReader(new InputStreamReader(inputStream));
//
//            ProcessQuestionsToArray processQuestionsToArray = new ProcessQuestionsToArray(questionReader);
//
//            ArrayList<Question> processedList = processQuestionsToArray.theProcessor();


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

//    public ArrayList<ArrayList<Question_board>> injectInputStreamToReader() throws IOException, ParseException {
//
//        //We set the InputStream to our file in the resource folder
//        InputStream inputStream = this.getClass()
//                .getClassLoader()
//                .getResourceAsStream("master_season1-35clean.tsv");
//
//        // If the InputStream is null we display a message
//        if(inputStream == null){
//
//            System.out.println("The files InputStream is empty/null");
//
//            return null;
//
//
//        } else {
//
//            return processQuestions(inputStream);
//
//            //return inputStream;
//
////            //We convert the inputStream through a converter "new InputStreamReader(inputStream)" and paas it to the constructor
////            QuestionReader questionReader = new QuestionReader(new InputStreamReader(inputStream));
////
////            ProcessQuestionsToArray processQuestionsToArray = new ProcessQuestionsToArray(questionReader);
////
////            ArrayList<Question> processedList = processQuestionsToArray.theProcessor();
//
//
////            ProcessQuestionsForGame processQuestionsForGameEasy = new ProcessQuestionsForGame(processedList, true);
////            ArrayList<Question_board> gameQuestionsForEasyGame = processQuestionsForGameEasy.theProcessor();
////
////            ProcessQuestionsForGame processQuestionsForGameHard = new ProcessQuestionsForGame(processedList, false);
////            ArrayList<Question_board> gameQuestionsForHardGame = processQuestionsForGameHard.theProcessor();
////
////            Game game = new Game(gameQuestionsForEasyGame, gameQuestionsForHardGame, scanner, writer);
////
////            game.processor();
//        }
//
//
//    }



//    public ArrayList<ArrayList<Question_board>> processQuestions(InputStream inputStream) throws IOException, ParseException {
//
//        //We convert the inputStream through a converter "new InputStreamReader(inputStream)" and paas it to the constructor
//        QuestionReader questionReader = new QuestionReader(new InputStreamReader(inputStream));
//
//        ProcessQuestionsToArray processQuestionsToArray = new ProcessQuestionsToArray(questionReader);
//
////        return new ProcessQuestionsToArray(questionReader).theProcessor();
//
//        return processQuestionsToArray.theProcessor();
//    }







//    public static void main(String[] args) throws IOException, ParseException {
//        new InputStreamFromFile().injectInputStreamToReader(new Scanner(System.in), new PrintWriter(System.out));
//    }

}
