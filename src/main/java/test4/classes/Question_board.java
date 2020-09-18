package test4.classes;

public class Question_board extends Question{

    private String answered;


    public Question_board(int id, int score, Category category, String question, String answer) {
        super(id, score, category, question, answer);
        this.answered = null;
    }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    @Override
    public String toString() {
        return "Question_board answer: " + answered +
                " Id: " + id +
                " Score: " + score +
                " Category: " + category.getCategoryName() +
                " Question: " + question +
                " Answer: " + answer;
    }

}
