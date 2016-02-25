package xyz.javalab.androidlab;

public class Question {

    private int textId;
    private boolean answer;

    public Question(int textId, boolean answer) {
        this.textId = textId;
        this.answer = answer;
    }

    public int getTextId() {
        return textId;
    }

    public boolean isAnswerTrue() {
        return answer;
    }

}
