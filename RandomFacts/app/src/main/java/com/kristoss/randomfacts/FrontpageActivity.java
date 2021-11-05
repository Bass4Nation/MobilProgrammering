package com.kristoss.randomfacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
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
import java.util.Collections;
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
        //        Må finne noe annet en dette for URL sjekk. Driver å sjekker ut Async. Hvordan det vil passe inn her.

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String eksWords = "Computer, Apex Legends, HTML, CSS, Android Studio, JavaScript, Java (programming language), Samsung, Wikipedia, Vue.js, Gradle, Android (operating system), React (JavaScript library), Next.js, Linux, Microsoft Windows";

        List<String> list = new ArrayList<>();
        Collections.addAll(list, eksWords.split(","));

        //---------------- Id's ---------------------------
        title = findViewById(R.id.textViewTitle1);
        context = findViewById(R.id.textViewContent);
        btnNext = findViewById(R.id.btnNext);
        btnToSrc = findViewById(R.id.btnToSource);

//        Viss scrolling trengs..
        context.setMovementMethod(new ScrollingMovementMethod());

//       Velger Random fra en liste
        Random random = new Random();
        int randomInt = random.nextInt(list.size());
        String choosen = list.get(randomInt);

//      API og weblink
        String api = "https://en.wikipedia.org/w/rest.php/v1/page/" + choosen;
        String url = "https://en.wikipedia.org/wiki/" + choosen;

        try {
            URL urls = new URL(api);
            System.out.println("---------------------------------------------");
            System.out.println("API : " + api);
            System.out.println(urls.toString());



            URL oracle = new URL(api);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject json = new JSONObject(inputLine);
                String content = json.getString("source");
                List<String> cSPLIT = new ArrayList<>();
                List<String> cSPLIT100 = new ArrayList<>();

                Collections.addAll(cSPLIT, content.split(" "));

                for (int i = 0; i < cSPLIT.size(); i++) {
                    String check = cSPLIT.get(i);
//                    Wikipedia har mange rare måter de finner ut hvor start infoen er.
                    if (check.equals("'''" + choosen + "'''")) {
                        System.out.println("Found U");
                        for (int y = 0; y < 100; y++) {
                            cSPLIT100.add(cSPLIT.get(i + y));
                        }
                    }
                    if (check.equals("('''" + choosen + "''')")) {
                        System.out.println("Found U");
                        for (int y = 0; y < 100; y++) {
                            cSPLIT100.add(cSPLIT.get(i + y));
                        }
                    }
                    if (check.equals("'''" + choosen.toLowerCase() + "'''")) {
                        System.out.println("Found U");
                        for (int y = 0; y < 100; y++) {
                            cSPLIT100.add(cSPLIT.get(i + y));
                        }
                    }
                }
                String output;
                if (cSPLIT100.isEmpty()) {
                    // om if statement ikke virker så viser den alt info istedenfor..
//                    Som oftes er det når den leter etter noe med to ord ell mer.
                    output = TextUtils.join(" ", cSPLIT);
                } else {
                    output = TextUtils.join(" ", cSPLIT100);
                }
                System.out.println(output);

                context.setText(output);
            }
            in.close();


//---------- UI -----------------------------------
            title.setText(choosen);
            // -------- button --------------
            btnToSrc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Går til nettleseren med url fra API
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });


            btnNext.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            }));

        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        System.out.println("---------------------------------------------");


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