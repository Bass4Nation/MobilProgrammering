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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class FrontpageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView title, context;
    Button btnNext, btnToSrc;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_frontpage);


//        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //-------------- Bare for test informasjon ----------------------------
        List<Data> liste = new ArrayList<>();
        liste.add(new Data("Computer", "https://en.wikipedia.org/wiki/Computer", "A computer is a machine that can be programmed to carry out sequences of arithmetic or logical operations automatically. Modern computers can perform generic sets of operations known as programs. These programs enable computers to perform a wide range of tasks. A computer system is a \"complete\" computer that includes the hardware, operating system (main software), and peripheral equipment needed and used for \"full\" operation. This term may also refer to a group of computers that are linked and function together, such as a computer network or computer cluster."));
        liste.add(new Data("Android (operating system)", "https://en.wikipedia.org/wiki/Android_(operating_system)", "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets. Android is developed by a consortium of developers known as the Open Handset Alliance and commercially sponsored by Google. It was unveiled in November 2007, with the first commercial Android device, the HTC Dream, being launched in September 2008."));
        liste.add(new Data("Telephone", "https://en.wikipedia.org/wiki/Telephone", "A telephone is a telecommunications device that permits two or more users to conduct a conversation when they are too far apart to be heard directly. A telephone converts sound, typically and most efficiently the human voice, into electronic signals that are transmitted via cables and other communication channels to another telephone which reproduces the sound to the receiving user. The term is derived from Greek: τῆλε (tēle, far) and φωνή (phōnē, voice), together meaning distant voice. A common short form of the term is phone, which came into use almost immediately after the first patent was issued."));
        liste.add(new Data("Android Studio", "https://en.wikipedia.org/wiki/Android_Studio", "Android Studio is the official[7] integrated development environment (IDE) for Google's Android operating system, built on JetBrains' IntelliJ IDEA software and designed specifically for Android development.[8] It is available for download on Windows, macOS and Linux based operating systems or as a subscription-based service in 2020.[9][10] It is a replacement for the Eclipse Android Development Tools (E-ADT) as the primary IDE for native Android application development."));



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