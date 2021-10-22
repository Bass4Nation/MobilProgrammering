package com.kristoss.randomfacts;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DocumentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Button save;
    EditText title, context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_document);


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        for (int i = 0; i < list.size(); i++){
//            Map<String, Object> city = new HashMap<>();
//            Data element = list.get(i);
//            city.put("id", i);
//            city.put("question", element.getQuestion());
//            city.put("answer", element.getAnswer());
//
//            db.collection("questions").document(element.getQuestion())
//                    .set(city)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "DocumentSnapshot successfully written!");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error writing document", e);
//                        }
//                    });
//        }



//----------------ID ------------------------------
        save = findViewById(R.id.btn_save);
        title = findViewById(R.id.input_title);
        context = findViewById(R.id.input_context);

        save.setOnClickListener(v ->{
// Må få inplementert noe "POST" til online databasen med dette. Tenker det er det letteste her.
// Men blir printet ut det som blir skrevet i appen
            System.out.println(title.getText());
            System.out.println(context.getText());
        });



//--------------- NAV ---------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) { // Rotate
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