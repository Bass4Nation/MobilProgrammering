package com.kristoss.randomfacts;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button btnTrue, btnFalse, btnNext;
    TextView question, answer;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_quiz);


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //-------------- Bare for test informasjon ----------------------------
        List<Data> list = new ArrayList<>();
        db.collection("quizTime")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                System.out.println(document.getId() + "-----------------------------------------");

                                list.add(new Data(document.getId(), document.getData().get("answer").toString()));
// -------------- Bare slik som random virker selv om online database ikke virker
                                if(list.size() == 0){
                                    list.add(new Data("Listen er tom","true"));
                                }

                                //-------------- Bare for test informasjon ----------------------------
                                Random random = new Random();
                                int number = random.nextInt(list.size());

                                Data choosen = list.get(number);

                                String userChoose = "";

                                // ----------- Id ------------

                                question = findViewById(R.id.txtview_question);
                                answer = findViewById(R.id.txtview_answer);
                                btnTrue = findViewById(R.id.btnTrue);
                                btnFalse = findViewById(R.id.btnFalse);

                                // ------------ Ui --------------

                                question.setText(choosen.getQuestion());


                                btnFalse.setOnClickListener(v -> {
                                    // Code here executes on main thread after user presses button
                                    answer.setText(answerCheck(choosen.getAnswer(), btnFalse.getText().toString()));
                                });

                                btnTrue.setOnClickListener(v -> {
                                    // Code here executes on main thread after user presses button
                                    answer.setText(answerCheck(choosen.getAnswer(), btnTrue.getText().toString()));

                                });


                                // -------------Button next ----------------


                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        btnNext = findViewById(R.id.btn_next);


        btnNext.setOnClickListener((v -> {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }));

// ---------------- Toolbar / NAV stuff -----------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_frontpage);
        }


    }
//----------- Check answer function ---------
    public String answerCheck(String answer, String chosen) {
        String valuation = "";
        if (answer.equals(chosen)) {
            valuation = "Correct!!";
        } else {
            valuation = "Wrong... Try again..";
        }
        return valuation;
    }

//--------------------------------------------------------------
@Override
public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Intent frontpage = new Intent(getApplicationContext(), FrontpageActivity.class);
    Intent quiz = new Intent(getApplicationContext(), QuizActivity.class);
    Intent doc = new Intent(getApplicationContext(), DocumentActivity.class);
    Intent allDoc = new Intent(getApplicationContext(), AllDocuments.class);
    Intent createQuiz = new Intent(getApplicationContext(), CreateQuiz.class);
    Intent search = new Intent(getApplicationContext(), Search.class);


    switch (item.getItemId()) {
        case R.id.nav_frontpage:
            startActivity(frontpage);
            break;
        case R.id.nav_documents:
            startActivity(doc);
            break;
        case R.id.nav_quiz:
            startActivity(quiz);
            break;
        case R.id.nav_all_documents:
            startActivity(allDoc);
            break;
        case R.id.nav_create_quiz:
            startActivity(createQuiz);
            break;
        case R.id.nav_search:
            startActivity(search);
            break;
    }

    drawer.closeDrawer(GravityCompat.START);

    return true;

}
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}