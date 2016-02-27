package xyz.javalab.androidlab.geoquiz.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import xyz.javalab.androidlab.R;

public class CheatActivity extends AppCompatActivity {

    private static final String KEY_CHEATED = "cheated";
    private static final String EXTRA_IS_ANSWER_TRUE = "xyz.javalab.androidlab.activities.question.is_anwser_true";

    private boolean isAnswerTrue;
    private boolean cheated = false;

    private TextView answerText;
    private Button showAnswerButton;

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
                hideButton();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CHEATED, cheated);
    }

    private void setUpWidgets() {
        answerText = (TextView) findViewById(R.id.answer_text);
        showAnswerButton = (Button) findViewById(R.id.show_answer_button);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheated = true;
                setResult(RESULT_OK);
                showAnswer();
                hideButton();
            }
        });
        TextView apiLevelText = (TextView) findViewById(R.id.api_level_text);
        apiLevelText.setText(R.string.api_level + Build.VERSION.SDK_INT);
    }

    private void showAnswer() {
        if (isAnswerTrue) {
            answerText.setText(R.string.true_button);
        } else {
            answerText.setText(R.string.false_button);
        }
    }

    private void hideButton() {
        if (!cheated && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = showAnswerButton.getWidth() / 2;
            int cy = showAnswerButton.getHeight() / 2;
            float radius = showAnswerButton.getWidth();
            Animator anim = ViewAnimationUtils.createCircularReveal(showAnswerButton, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    answerText.setVisibility(View.VISIBLE);
                    showAnswerButton.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            answerText.setVisibility(View.VISIBLE);
            showAnswerButton.setVisibility(View.INVISIBLE);
        }
    }

    public static Intent newIntent(Context context, boolean isAnswerTrue) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_IS_ANSWER_TRUE, isAnswerTrue);
        return intent;
    }

}
