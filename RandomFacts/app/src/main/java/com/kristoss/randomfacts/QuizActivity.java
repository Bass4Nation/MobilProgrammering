package com.kristoss.randomfacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    Button btnTrue, btnFalse, btnNext;
    TextView question, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_quiz);

        List<Data> list = new ArrayList<>();
        list.add(new Data("Er det år 2021?",true));
        list.add(new Data("Er det år 2020?",false));

        Random random = new Random();
        int number = random.nextInt(list.size());

        Data choosen = list.get(number);

        // ----------- Id ------------

        question = findViewById(R.id.txtview_question);
        answer = findViewById(R.id.txtview_answer);
        btnNext = findViewById(R.id.btn_next);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        // ------------ Ui --------------

        question.setText(choosen.getQuestion());

        if(choosen.isAnswer()){
            answer.setText("Correct!!");
        }else
        {
            answer.setText("Wrong.... Try again");
        }




        // -------------Button next ----------------
        btnNext.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInt = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(myInt);
            }
        }));



    }
}