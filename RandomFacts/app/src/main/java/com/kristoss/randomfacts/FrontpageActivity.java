package com.kristoss.randomfacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FrontpageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    TextView title, context;
    Button btnNext, btnToSrc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_frontpage);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        /*------------------------------------------------------*/

        List<Data> liste = new ArrayList<>();

        List<String> list = new ArrayList<>();
        list.add("Computer");
        list.add("HTML");
        list.add("CSS");
        list.add("Android Studio");
        list.add("JavaScript");
        list.add("Java (programming language)");

        liste.add(new Data("Computer"));
        liste.add(new Data("Java (programming language)"));
        liste.add(new Data("JavaScript"));
        liste.add(new Data("HTML"));
        liste.add(new Data("CSS"));
        liste.add(new Data("Android Studio"));

//        liste.add(new Data(dbTitle,dbUrl,dbContent));
// -------------- Bare slik som random virker selv om online database ikke virker
        if(liste.size() == 0){
            liste.add(new Data("Listen er tom","null", "Denne listen er tom"));
        }

        //---------------- Id's ---------------------------
        title = findViewById(R.id.textViewTitle1);
        context = findViewById(R.id.textViewContent);
        btnNext = findViewById(R.id.btnNext);
        btnToSrc = findViewById(R.id.btnToSource);


        Random random = new Random();
        int randomInt = random.nextInt(liste.size());
        String choosen = list.get(randomInt);

        String api = "https://en.wikipedia.org/w/rest.php/v1/page/" + choosen;
        String url = "https://en.wikipedia.org/wiki/" + choosen;

        try {
            System.out.println("---------------------------------------------");
            System.out.println("API : " + api);

            URL urls=new URL(api);

            System.out.println(urls.toString());

            URL oracle = new URL(api);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null){
                JSONObject json = new JSONObject(inputLine);
                String content = json.getString("source");

                context.setText(content);

//                System.out.println("----------- ende \n" + content);

            }
            in.close();


//---------- UI -----------------------------------
            title.setText(choosen);
            // -------- button --------------
            btnToSrc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
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

        }catch (Exception e){
            System.out.println("Error : "+e);
        }
        System.out.println("---------------------------------------------");





        /*--------------------------------------------------------*/




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