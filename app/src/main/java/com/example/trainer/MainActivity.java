package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button start_playAgain, button0, button1, button2, button3;
    ConstraintLayout questionLayout;
    TextView countdownTime, answered_questions,total_score,sum_text_view,correct_wrong;
    Random random;
    ArrayList<Integer> answers;
    int correct_answer_location,total_questions_answered,correct_answered_questions;
    Handler handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListner();
        questionLayout.setVisibility(View.INVISIBLE);
        total_score.setVisibility(View.INVISIBLE);
        correct_wrong.setVisibility(View.INVISIBLE);
        random = new Random();
        answers = new ArrayList<Integer>();
        handle = new Handler();

    }
    private void initListner()
    {
        start_playAgain=findViewById(R.id.start_playAgain);
        button0=findViewById(R.id.button0);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        questionLayout=findViewById(R.id.questionsLayout);
        countdownTime=findViewById(R.id.countdownTime);
        answered_questions=findViewById(R.id.answered_questions);
        total_score=findViewById(R.id.totalScore);
        sum_text_view=findViewById(R.id.sum_text_view);
        correct_wrong=findViewById(R.id.correct_wrong);
    }

    public void start(View view)
    {
        total_questions_answered=0;
        correct_answered_questions=0;
        start_playAgain.setVisibility(View.INVISIBLE);
        questionLayout.setVisibility(View.VISIBLE);
        total_score.setVisibility(View.INVISIBLE);
        newQuestion();
        CountDownTimer count_timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTime.setText("00:"+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                countdownTime.setText("00:00");
                answered_questions.setText("0/0");
                questionLayout.setVisibility(View.INVISIBLE);
                start_playAgain.setText("Play Again");
                start_playAgain.setVisibility(View.VISIBLE);
                total_score.setVisibility(View.VISIBLE);
                total_score.setText("You scored "+correct_answered_questions+"/"+total_questions_answered);
            }
        }.start();
    }

    private void newQuestion()
    {

        answers.clear();
        int number1=random.nextInt(21);
        int number2=random.nextInt(21);
        int sum=number1+number2;
        correct_answer_location=random.nextInt(4);
        sum_text_view.setText(String.valueOf(number1)+" + "+String.valueOf(number2));

        for(int i=0;i<4;i++)
        {
            if(i==correct_answer_location) {
                //
                answers.add(sum);
            }
            else {
                //
                int wrong_answer=random.nextInt(41);
                while(wrong_answer==(number1+number2))
                {
                    wrong_answer=random.nextInt(41);
                }
                answers.add(wrong_answer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void checkAnswer(View view)
    {
        if(Integer.toString(correct_answer_location).equals(view.getTag().toString()))
        {
            correct_wrong.setVisibility(View.VISIBLE);
            correct_wrong.setAlpha(1);
            correct_wrong.setText("Hey!you got that");
            total_questions_answered++;
            correct_answered_questions++;
            answered_questions.setText(correct_answered_questions+"/"+total_questions_answered);
        }
        else
        {
            correct_wrong.setVisibility(View.VISIBLE);
            correct_wrong.setAlpha(1);
            correct_wrong.setText("Sorry!wrong answer");
            total_questions_answered++;
            answered_questions.setText(correct_answered_questions+"/"+total_questions_answered);
        }
        questionLayout.animate().alpha(0).setDuration(1500);
        correct_wrong.animate().alpha(0).setDuration(1500);
        //newQuestion();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                newQuestion();
                questionLayout.animate().alpha(1).setDuration(1000);
            }
        },1000);

    }


}