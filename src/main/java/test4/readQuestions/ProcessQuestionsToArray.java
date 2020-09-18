package test4.readQuestions;



import test4.classes.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

public class ProcessQuestionsToArray {

    private ArrayList<Question> questionsList;
    private final QuestionReader questionReader;
    private Question question;

    public ProcessQuestionsToArray() {
        this.questionReader = new QuestionReader(new InputStreamReader(new InputStreamFromFile().injectInputStreamToReader()));
        this.questionsList = new ArrayList<>();
    }


    public ArrayList<Question> theProcessor() throws IOException, ParseException {
        addQuestionsToArray();

        filterWrongScores();

        return matchQuestionsPerCategory();
    }



    public void addQuestionsToArray() throws IOException, ParseException {

        //Loop trough the data file via the reader, and extract valid questions that match the questions length
        while ((this.question = questionReader.readQuestion()) != null) {

            //Add all questions to the questions array list one by one
            questionsList.add(question);

        }

//        for(int i = 0 ; i < 40 ; i++){
//            System.out.println(questionsList.get(i).toString());
//        }

    }

    public void filterWrongScores() {

        for (int i = 0; i < questionsList.size(); i++) {

            //Check if the score equals the values
            if (questionsList.get(i).getScore() == 0
                    ||
                    questionsList.get(i).getScore() == 1200
                    ||
                    questionsList.get(i).getScore() == 1600
                    ||
                    questionsList.get(i).getScore() == 2000
                    ||
                    questionsList.get(i).getScore() == 3400
                    ||
                    questionsList.get(i).getScore() == 3800
                    ||
                    questionsList.get(i).getScore() == 4000
                    ||
                    questionsList.get(i).getScore() == 5000
                    ||
                    questionsList.get(i).getScore() == 5200
                    ||
                    questionsList.get(i).getScore() == 6000) {

                //Remove the index fom the list
                questionsList.remove(i);

                //Go back one index, because the list is shorter because of the removal
                i--;

            }

        }

//        for(int j = 0 ; j < 15 ; j++){
//            System.out.println(questionsList.get(j).toString());
//        }

    }


    //Remove categories that don't have 5 questions
    public ArrayList<Question> matchQuestionsPerCategory() {

        //We make a altered list to put the values Questions in
        ArrayList<Question> alteredList = new ArrayList<Question>();

        for( int i = 0 ; i < questionsList.size()-4 ; i++ ) {

            if(questionsList.get(i).getCategory().getCategoryName().equals(questionsList.get(i + 1).getCategory().getCategoryName())
                    &&
                    questionsList.get(i).getCategory().getCategoryName().equals(questionsList.get(i + 2).getCategory().getCategoryName())
                    &&
                    questionsList.get(i).getCategory().getCategoryName().equals(questionsList.get(i + 3).getCategory().getCategoryName())
                    &&
                    questionsList.get(i).getCategory().getCategoryName().equals(questionsList.get(i + 4).getCategory().getCategoryName())){

                //We add the category entries to a new array
                alteredList.add(questionsList.get(i));
                alteredList.add(questionsList.get(i + 1));
                alteredList.add(questionsList.get(i + 2));
                alteredList.add(questionsList.get(i + 3));
                alteredList.add(questionsList.get(i + 4));

                //We skip the entries and continue
                i += 4;

            }

        }

//        for( int i = 0 ; i < 50 ; i++ ){
//            System.out.println("Index: " + i + " " + alteredList.get(i).toString());
//        }

        return alteredList;

    }




}
