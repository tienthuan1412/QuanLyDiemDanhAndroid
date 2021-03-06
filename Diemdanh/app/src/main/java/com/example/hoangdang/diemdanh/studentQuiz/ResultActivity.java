package com.example.hoangdang.diemdanh.studentQuiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoangdang.diemdanh.R;
import com.example.hoangdang.diemdanh.SupportClass.AppVariable;
import com.example.hoangdang.diemdanh.SupportClass.SecurePreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.correct_question)
    TextView _correct_question;

    @BindView(R.id.message)
    TextView _message;

    @BindView(R.id.view_detail)
    Button _viewDetail;

    @BindView(R.id.close)
    Button _close;

    @BindView(R.id.toolbar_quiz_result)
    Toolbar toolbar;

    JSONObject quizConfig;
    int countCorrect;
    int total;
    boolean noAnswer;
    int quiz_type;

    public ArrayList<String> selecteds;
    public ArrayList<String> corrects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        selecteds = (ArrayList<String>)this.getIntent().getSerializableExtra("selecteds");
        corrects = (ArrayList<String>)this.getIntent().getSerializableExtra("corrects");

        ButterKnife.bind(this);

        toolbar.setTitle("QUIZ RESULT");

        quizConfig = null;
        SharedPreferences pref = new SecurePreferences(this);
        int temp = pref.getInt(AppVariable.QUIZ_BLANK, 0);

        noAnswer = temp == 0;

        countCorrect = pref.getInt(AppVariable.QUIZ_CORRECT, 0);
        total = pref.getInt(AppVariable.QUIZ_TOTAL, 0);
        quiz_type = pref.getInt(AppVariable.QUIZ_TYPE, 0);

        setButtonListener();
    }

    private void setButtonListener() {
        _close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        _viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail();
            }
        });

        _correct_question.setText("Correct: "
                + String.valueOf(countCorrect) + " out of "
                + String.valueOf(total));

        String text;

        if (noAnswer){
            text = "Your Attendance is not checked. You didn't answer any question.";
        }
        else {
            if (quiz_type == 0 || countCorrect == total){
                text = "Your Attendance is checked";
            }
            else {
                text = "Your Attendance is not checked. Miscellaneous quiz requires you to be 100% correct to be checked";
            }
        }

        _message.setText(text);
    }

    private void showDetail(){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("selecteds", selecteds);
        intent.putExtra("corrects", corrects);
        startActivity(intent);
    }
}
