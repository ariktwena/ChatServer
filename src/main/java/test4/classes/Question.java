package test4.classes;

public class Question {

    protected final int id;
    protected final int score;
    protected final Category category;
    protected final String question;
    protected final String answer;

    public Question(int id, int score, Category category, String question, String answer) {
        this.id = id;
        this.score = score;
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Category getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "id: " + id +
                " Score: " + score +
                " Category: " + category.getCategoryName() +
                " Question: " + question +
                " Answer: " + answer
                ;
    }
}
