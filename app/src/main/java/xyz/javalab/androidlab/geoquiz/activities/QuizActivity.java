package xyz.javalab.androidlab.geoquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import xyz.javalab.androidlab.R;
import xyz.javalab.androidlab.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_IS_CHEATER = "isCheater";
    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView questionText;

    private int currentIndex = 0;
    private Question[] questions = {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true)
    };

    private boolean[] isCheater = new boolean[questions.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
            isCheater = savedInstanceState.getBooleanArray(KEY_IS_CHEATER);
        }

        setUpWidgets();
        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putBooleanArray(KEY_IS_CHEATER, isCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHEAT && resultCode == RESULT_OK) {
            isCheater[currentIndex] = true;
        }
    }

    private void setUpWidgets() {
        questionText = (TextView) findViewById(R.id.question_text);
        questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });

        Button trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        Button falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        ImageButton nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });
        ImageButton previousButton = (ImageButton) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
            }
        });

        Button cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CheatActivity.newIntent(QuizActivity.this, questions[currentIndex].isAnswerTrue());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    private void updateQuestion() {
        questionText.setText(questions[currentIndex].getTextId());
    }

    private void showNextQuestion() {
        currentIndex = (currentIndex + 1) % questions.length;
        updateQuestion();
    }

    private void showPreviousQuestion() {
        currentIndex = currentIndex - 1;
        if (currentIndex < 0) {
            currentIndex = questions.length - 1;
        }
        updateQuestion();
    }

    private void checkAnswer(boolean userPressedTrue) {
        int messageId;
        if (isCheater[currentIndex]) {
            messageId = R.string.judgment_toast;
        } else if (userPressedTrue == questions[currentIndex].isAnswerTrue()) {
            messageId = R.string.correct_toast;
        } else {
            messageId = R.string.incorrect_toast;
        }
        Toast.makeText(QuizActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }

}
