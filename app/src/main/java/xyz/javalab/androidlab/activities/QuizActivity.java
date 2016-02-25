package xyz.javalab.androidlab.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import xyz.javalab.androidlab.R;
import xyz.javalab.androidlab.model.Question;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "currentIndex";

    private Button btnTrue;
    private Button btnFalse;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private TextView txtQuestion;

    private int currentIndex = 0;
    private Question[] questions = {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
        }

        setUpWidgets();
        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private void setUpWidgets() {
        txtQuestion = (TextView) findViewById(R.id.text_question);
        txtQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });

        btnTrue = (Button) findViewById(R.id.button_true);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        btnFalse = (Button) findViewById(R.id.button_false);
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        btnNext = (ImageButton) findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });
        btnPrevious = (ImageButton) findViewById(R.id.button_previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
            }
        });
    }

    private void updateQuestion() {
        txtQuestion.setText(questions[currentIndex].getTextId());
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
        if (userPressedTrue == questions[currentIndex].isAnswerTrue()) {
            messageId = R.string.correct_toast;
        } else {
            messageId = R.string.incorrect_toast;
        }
        Toast.makeText(QuizActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }

}
