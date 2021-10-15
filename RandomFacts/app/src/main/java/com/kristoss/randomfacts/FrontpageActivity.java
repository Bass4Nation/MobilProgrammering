package com.kristoss.randomfacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FrontpageActivity extends AppCompatActivity  {
    TextView title, context;
    Button btnNext, btnToSrc;
    DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<Data> liste = new ArrayList<>();
        liste.add(new Data("Computer","https://en.wikipedia.org/wiki/Computer", "A computer is a machine that can be programmed to carry out sequences of arithmetic or logical operations automatically. Modern computers can perform generic sets of operations known as programs. These programs enable computers to perform a wide range of tasks. A computer system is a \"complete\" computer that includes the hardware, operating system (main software), and peripheral equipment needed and used for \"full\" operation. This term may also refer to a group of computers that are linked and function together, such as a computer network or computer cluster."));
        liste.add(new Data("Android (operating system)","https://en.wikipedia.org/wiki/Android_(operating_system)", "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets. Android is developed by a consortium of developers known as the Open Handset Alliance and commercially sponsored by Google. It was unveiled in November 2007, with the first commercial Android device, the HTC Dream, being launched in September 2008."));
        liste.add(new Data("Telephone","https://en.wikipedia.org/wiki/Telephone", "A telephone is a telecommunications device that permits two or more users to conduct a conversation when they are too far apart to be heard directly. A telephone converts sound, typically and most efficiently the human voice, into electronic signals that are transmitted via cables and other communication channels to another telephone which reproduces the sound to the receiving user. The term is derived from Greek: τῆλε (tēle, far) and φωνή (phōnē, voice), together meaning distant voice. A common short form of the term is phone, which came into use almost immediately after the first patent was issued."));
        liste.add(new Data("Android Studio","https://en.wikipedia.org/wiki/Android_Studio", "Android Studio is the official[7] integrated development environment (IDE) for Google's Android operating system, built on JetBrains' IntelliJ IDEA software and designed specifically for Android development.[8] It is available for download on Windows, macOS and Linux based operating systems or as a subscription-based service in 2020.[9][10] It is a replacement for the Eclipse Android Development Tools (E-ADT) as the primary IDE for native Android application development."));

        Random random = new Random();
        int randomInt = random.nextInt(liste.size());
        Data choosen = liste.get(randomInt);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_frontpage);
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


}