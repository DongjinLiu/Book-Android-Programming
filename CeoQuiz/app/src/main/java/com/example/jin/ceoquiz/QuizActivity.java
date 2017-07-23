package com.example.jin.ceoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private  Button mNextButton;
    private TextView mQuestionTextView;

    private Question[] mQusetionBank=new Question[]{
            new Question(R.string.question_1,true),
            new Question(R.string.question_2,false),
            new Question(R.string.question_3,false)
    };

    private int mCurrentInde=0;

    public QuizActivity() {
    }

    private void updateQuestion(){
        int question=mQusetionBank[mCurrentInde].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQusetionBank[mCurrentInde].isAnswerTrue();

        int messageResId=0;

        if (userPressedTrue==answerIsTrue){
            messageResId=R.string.correct_toast;
        }
        else{
            messageResId=R.string.incorrect_taost;
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
        updateQuestion();

        mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentInde =(mCurrentInde+1)%mQusetionBank.length;
                updateQuestion();
            }
        });
    }
}
