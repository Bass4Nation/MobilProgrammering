package com.kristoss.randomfacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button btnTrue, btnFalse, btnNext;
    TextView question, answer;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_quiz);

        List<Data> list = new ArrayList<>();
        list.add(new Data("Er det år 2021?","true"));
        list.add(new Data("Er det år 2020?","false"));

        Random random = new Random();
        int number = random.nextInt(list.size());

        Data choosen = list.get(number);

        String userChoose = "";

        // ----------- Id ------------

        question = findViewById(R.id.txtview_question);
        answer = findViewById(R.id.txtview_answer);
        btnNext = findViewById(R.id.btn_next);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        // ------------ Ui --------------

        question.setText(choosen.getQuestion());



        btnFalse.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            answer.setText(answerCheck(choosen.getAnswer(),btnFalse.getText().toString()));
        });

        btnTrue.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            answer.setText(answerCheck(choosen.getAnswer(),btnTrue.getText().toString()));
        });



        // -------------Button next ----------------
        btnNext.setOnClickListener((v -> {
            Intent myInt = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(myInt);
        }));



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) { // Rotate
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrontpageActivity()).commit();
            navigationView.setCheckedItem(R.id.nav_frontpage);
        }


    }

    public String answerCheck(String answer, String chosen){
        String valuation = "";
        if(answer.equals(chosen)){
           valuation  = "Correct!!";
        }else{
            valuation = "Wrong... Try again..";
        }
       return valuation;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent frontpage = new Intent(getApplicationContext(), FrontpageActivity.class);
        Intent quiz = new Intent(getApplicationContext(), QuizActivity.class);
        Intent doc = new Intent(getApplicationContext(), FrontpageActivity.class);


        switch (item.getItemId()){
            case R.id.nav_frontpage:
                startActivity(frontpage);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrontPageFragment()).commit();

                break;
            case R.id.nav_documents:
                startActivity(quiz);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DocumentFragment()).commit();
                break;
            case R.id.nav_quiz:
                startActivity(quiz);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}