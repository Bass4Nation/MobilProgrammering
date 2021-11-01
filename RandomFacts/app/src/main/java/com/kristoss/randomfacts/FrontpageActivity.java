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


public class FrontpageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    TextView title, context;
    Button btnNext, btnToSrc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_frontpage);


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //-------------- Bare for test informasjon ----------------------------
        List<Data> liste = new ArrayList<>();
        db.collection("facts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String dbTitle, dbUrl, dbContent;
                        dbTitle = document.getId();
                        dbUrl = document.getData().get("url").toString();
                        dbContent = document.getData().get("content").toString();
                        liste.add(new Data(dbTitle,dbUrl,dbContent));
// -------------- Bare slik som random virker selv om online database ikke virker
                        if(liste.size() == 0){
                            liste.add(new Data("Listen er tom","null", "Denne listen er tom"));
                        }

                        Random random = new Random();
                        int randomInt = random.nextInt(liste.size());
                        Data choosen = liste.get(randomInt);

//---------------- Id's ---------------------------
                        title = findViewById(R.id.textViewTitle1);
                        context = findViewById(R.id.textViewContent);
                        btnNext = findViewById(R.id.btnNext);
                        btnToSrc = findViewById(R.id.btnToSource);
//---------- UI -----------------------------------
                        title.setText(choosen.getMainTitle());
                        context.setText(choosen.getMainContext());
                        // -------- button --------------
                        btnToSrc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(choosen.getUrl()));
                                startActivity(i);
                            }
                        });
                        btnNext.setOnClickListener((new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent myInt = new Intent(getApplicationContext(), FrontpageActivity.class);
                                startActivity(myInt);
                            }
                        }));



                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });



//---------------- Toolbar/ NAV stuff -------------------------------
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