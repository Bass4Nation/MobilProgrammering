package com.kristoss.randomfacts;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View inflatedView = getLayoutInflater().inflate(R.layout.fragment_frontpage,null);
        String update_str = getString(R.string.title);

        TextView text = (TextView) inflatedView.findViewById(R.id.textViewTitle1);
        System.out.println("----------------------\n" + text.getText());
        Log.d(TAG, "onCreate: " + text.getText());
        text.setText(update_str);
        System.out.println("----------------------\n" + text.getText());


        setContentView(R.layout.activity_main);


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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrontPageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_frontpage);
        }

        //----------- FrontPage ------------------
//        TextView textViewTitle = findViewById(R.id.textViewContent);




        //---------------------------------------


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_frontpage:
                View inflatedView = getLayoutInflater().inflate(R.layout.fragment_frontpage,null);
                String update_str = getString(R.string.title);

                TextView text = (TextView) inflatedView.findViewById(R.id.textViewTitle1);
                System.out.println("----------------------\n" + text.getText());
                Log.d(TAG, "onCreate: " + text.getText());
                text.setText(update_str);
                System.out.println("----------------------\n" + text.getText());

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrontPageFragment()).commit();

                break;
            case R.id.nav_documents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DocumentFragment()).commit();
                break;
            case R.id.nav_quiz:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment()).commit();
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

    public void frontPage(){
        setContentView(R.layout.fragment_frontpage);
        final TextView textViewTitle = findViewById(R.id.textViewTitle1);
        textViewTitle.setText(R.string.title);
    }
}