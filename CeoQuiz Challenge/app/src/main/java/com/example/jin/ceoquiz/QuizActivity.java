package com.example.jin.ceoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;

    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_1,true),
            new Question(R.string.question_2,false),
            new Question(R.string.question_3,false)
    };

    private int mCurrentIndex=0;
    private boolean mIsCheater;



    private static final String TAG="QuizActivity";
    private static final String KEY_INDEX="index";
    private static final int REQUEST_CODE_CHEAT=0;

    private void updateQuestion(){
        //Log.d(TAG, "updateQuestion: text for question #"+mCurrentInde,new Exception());
        int question=mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId=0;

        if (mIsCheater){
            messageResId=R.string.judgment_toast;
        }
        else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
        if (savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
        }

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

        mPrevButton=(ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex!=0){
                    mCurrentIndex--;
                    mIsCheater=false;
                    updateQuestion();
                }
                else{
                    Toast.makeText(QuizActivity.this,getResources().getString(R.string.firstone_message),Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNextButton=(ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mCurrentIndex>=mQuestionBank.length-1){
                    Toast.makeText(QuizActivity.this,getResources().getString(R.string.lastone_message),Toast.LENGTH_SHORT).show();
                }
                else{
                    mCurrentIndex++;
                    mIsCheater=false;
                    updateQuestion();
                }
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex>=mQuestionBank.length-1){
                    Toast.makeText(QuizActivity.this,getResources().getString(R.string.lastone_message),Toast.LENGTH_SHORT).show();
                }
                else{
                    mCurrentIndex++;
                    mIsCheater=false;
                    updateQuestion();
                }
            }
        });

        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i=new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i=CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK){   //这儿把resultCode写成了requestCode结果调了一晚上，该死的代码提示
            return;
        }
        if (requestCode==REQUEST_CODE_CHEAT){
            if (data==null){
                return;
            }
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }
}