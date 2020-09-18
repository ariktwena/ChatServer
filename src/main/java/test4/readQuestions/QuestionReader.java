package test4.readQuestions;


import test4.classes.Category;
import test4.classes.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class QuestionReader {


    private int count = 0;

    //We set the BufferedReader til the "reader" variable
    private final BufferedReader reader;

    //LineCounter so we know witch line has the error in the line reader
    private int lineCounter = 0;

    public QuestionReader(BufferedReader reader) {

        this.reader = reader;
    }

    public QuestionReader(Reader reader) {

        this(new BufferedReader(reader));
    }


    //We read the lines from the data file
    public Question readQuestion() throws IOException, ParseException {

        //We initialize the variables
        Category category;
        String line, question, answer;
        int id, score;

        //We read on line
        line = reader.readLine();

        //We check to see if the line is not null/empty
        if(line != null){

            //Make a new line
            lineCounter += 1;

            //We make a token array of the line - 4 tokens
            String[] token = line.split("\t");

            //We sort the tokens
            if (token.length == 4){

                try{
                    //We increase the id count
                    count += 1;

                    //We set the variables
                    id = count;
                    score = Integer.parseInt(token[0]);
                    category = new Category(token[1]);
                    question = token[2];
                    answer = token[3];

                    //We create and return a new Question object
                    return new Question(id, score, category, question, answer);

                } catch (NumberFormatException e){

                    throw new ParseException("Expected an integer in fields 1, but got \"" + token[0] + "\"", lineCounter);
                }


            } else {

                throw new ParseException("Expected 4 fields, got: " + token.length, lineCounter);

            }

        }

        //We return null if something goes wrong
        return null;
    }


}
