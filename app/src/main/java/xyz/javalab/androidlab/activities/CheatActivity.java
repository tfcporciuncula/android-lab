package xyz.javalab.androidlab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xyz.javalab.androidlab.R;

public class CheatActivity extends AppCompatActivity {

    private static final String KEY_CHEATED = "cheated";
    private static final String EXTRA_IS_ANSWER_TRUE = "xyz.javalab.androidlab.activities.question.is_anwser_true";

    private boolean isAnswerTrue;
    private boolean cheated = false;

    private TextView txtAnswer;
    private Button btnShowAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        isAnswerTrue = getIntent().getBooleanExtra(EXTRA_IS_ANSWER_TRUE, false);

        setUpWidgets();

        if (savedInstanceState != null) {
            cheated = savedInstanceState.getBoolean(KEY_CHEATED);
            if (cheated) {
                setResult(RESULT_OK);
                showAnswer();
            }
        }
    }

    private void setUpWidgets() {
        txtAnswer = (TextView) findViewById(R.id.text_answer);
        btnShowAnswer = (Button) findViewById(R.id.button_show_answer);
        btnShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheated = true;
                setResult(RESULT_OK);
                showAnswer();
            }
        });
    }

    private void showAnswer() {
        if (isAnswerTrue) {
            txtAnswer.setText(R.string.true_button);
        } else {
            txtAnswer.setText(R.string.false_button);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CHEATED, cheated);
    }

    public static Intent newIntent(Context context, boolean isAnswerTrue) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_IS_ANSWER_TRUE, isAnswerTrue);
        return intent;
    }

}
