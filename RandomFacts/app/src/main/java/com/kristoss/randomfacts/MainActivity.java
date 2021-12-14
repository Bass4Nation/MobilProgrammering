package com.kristoss.randomfacts;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawer;
    TextView title, context, question, answer, titleSearch, contextSearch, titleSingleDoc, contentSingleDoc;
    Button btnNext, btnToSrc, quizBtnTrue, quizBtnFalse, btnNextSearch, btnToSrcSearch, btnSearch;
    EditText searchResult, titleDoc, contextDoc, questionEdit;
    String docContent, docTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ---------------- Toolbar / NAV stuff -----------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.frontpage);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            changeFragment(new FrontpageFragment());
        }
    }


    // ----------- Frontpage ------------------
    public void frontPageContent() {

// https://www.geeksforgeeks.org/how-to-fix-android-os-networkonmainthreadexception/
//        Set at dette går for å skrive over default methodes for å koble til internettet.
//        Kunne også brukt AsyncTask men var usikker på hvordan det virket da jeg lagde denne.

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> list = new ArrayList<>();
        String[] firebaseCollections = {"wiki", "wiki_all"};

//        Velger å hente den som heter wiki siden det er mye mindre artikler i den delen av databasen
//        kan sette fiirebaseColletion til 1 om man vil at den skal sjekke mellom 8 tusen artikler.
//        Er over 16 millioner artikkler i wikipedia. Så fant ikke om det var en enkel måte å laste opp alle titler som csv eller txt i firebase.

        db.collection(firebaseCollections[0])
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getData().get("title").toString());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        //---------------- Id's ---------------------------
                        title = findViewById(R.id.textViewTitle1);
                        context = findViewById(R.id.textViewContent);
                        btnNext = findViewById(R.id.btnNext);
                        btnToSrc = findViewById(R.id.btnToSource);

//        Viss scrolling trengs..
                        context.setMovementMethod(new ScrollingMovementMethod());

//       Velger Random fra en liste
//                        Å gjøre det på denne måten er veldig tungvint men ser ikke at det går å
//                        incremente id med +1 på firebase. Så ser ikke en mulighet slik at jeg trenger bare å hente en artikkel.
//                        Så nå henter den alle artikkler i databasen så velger den en av dem her.
                        Random random = new Random();
                        int randomInt = random.nextInt(list.size());
                        String choosen = list.get(randomInt);

//      API og weblink
                        String api = "https://en.wikipedia.org/w/rest.php/v1/page/" + choosen;
                        String url = "https://en.wikipedia.org/wiki/" + choosen;
//                        ---------- Behandling av API -------------------
// Her så leser den hvert ord i API requesten. Den sjekker etter mye. Men informasjonen er ikke enda riktig formatert for å bli vist enda
//                        Har vært vanskelig å behandle denne api'en. Hadde jeg hatt mer tid, så skulle jeg ha fått den riktig behandlet.
//                        Dette under er en veldig tungvint måte som den er behandlet på. Men virker ish som den skal.
//                        Men krever litt resurser for å gjøre alt dette.
                        try {
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
// Behandling av API
                                for (int i = 0; i < cSPLIT.size(); i++) {
                                    String check = cSPLIT.get(i);
//                    Wikipedia har mange rare måter de finner ut hvor start infoen er.
                                    if (check.equals("'''" + choosen + "'''")) {
                                        for (int y = 0; y < 100; y++) {
                                            cSPLIT100.add(cSPLIT.get(i + y));
                                        }
                                    }
                                    if (check.equals("('''" + choosen + "''')")) {
                                        for (int y = 0; y < 100; y++) {
                                            cSPLIT100.add(cSPLIT.get(i + y));
                                        }
                                    }
                                    if (check.equals("'''" + choosen.toLowerCase() + "'''")) {
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
//                                Også setter den ferdig behandlet tekst til textView'et på forsiden
                                context.setText(output);
                            }
                            in.close();


//---------- UI -----------------------------------
                            title.setText(choosen);
                            // -------- button --------------
//                            Går bare til Api link adressen. Starter nettleseren på mobilen
                            btnToSrc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                    Går til nettleseren med url fra API
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            });

                        } catch (Exception e) {
                            System.out.println("Error : " + e);
                        }
                    }
                });
    }

    //    ------------ Search Fragment ----------------------

    public void searchButton() {

//        Må finne noe annet en dette for URL sjekk. Driver å sjekker ut Async. Hvordan det vil passe inn her.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        /*------------------------------------------------------*/
        btnSearch = findViewById(R.id.search_button);
        searchResult = findViewById(R.id.search_field);


        String choosen = searchResult.getText().toString();

        //---------------- Id's ---------------------------
        titleSearch = findViewById(R.id.textViewTitle1);
        contextSearch = findViewById(R.id.textViewContent);
        btnNextSearch = findViewById(R.id.btnNext);
        btnToSrcSearch = findViewById(R.id.btnToSource);

        contextSearch.setMovementMethod(new ScrollingMovementMethod());


        String api = "https://en.wikipedia.org/w/rest.php/v1/page/" + choosen;
        String url = "https://en.wikipedia.org/wiki/" + choosen;
//Alt av behandling er forklart i forside funskjonen
        try {
            URL urls = new URL(api);

            System.out.println(urls.toString());

            URL oracle = new URL(api);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject json = new JSONObject(inputLine);
                String content = json.getString("source");
                List<String> tester = new ArrayList<>();
                List<String> tester2 = new ArrayList<>();

                Collections.addAll(tester, content.split(" "));

                for (int i = 0; i < tester.size(); i++) {
                    String check = tester.get(i);
                    if (check.equals("'''" + choosen + "'''")) {
                        System.out.println("Found U");
                        for (int y = 0; y < 100; y++) {
                            tester2.add(tester.get(i + y));
                        }
                    }
                    if (check.equals("('''" + choosen + "''')")) {
                        System.out.println("Found U");
                        for (int y = 0; y < 100; y++) {
                            tester2.add(tester.get(i + y));
                        }
                    }
                    if (check.equals("'''" + choosen.toLowerCase() + "'''")) {
                        System.out.println("Found U");
                        for (int y = 0; y < 100; y++) {
                            tester2.add(tester.get(i + y));
                        }
                    }
                }
                String output;
                if (tester2.isEmpty()) {
                    output = TextUtils.join(" ", tester);
                } else {
                    output = TextUtils.join(" ", tester2);
                }
                System.out.println(output);

                contextSearch.setText(output);
            }
            in.close();


//---------- UI -----------------------------------
            titleSearch.setText(choosen);
            btnToSrcSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });


        } catch (Exception e) {
            System.out.println("Error : " + e);
            String notFoundMessage = "Search result not found";
            titleSearch.setText(notFoundMessage);
            contextSearch.setText("");
        }
        System.out.println("---------------------------------------------");


    }


    // ---------- Få alle quizzes -------------------------------
    public void quizContent() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //-------------- Bare for å hente informasjon ----------------------------
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
                                if (list.size() == 0) {
                                    list.add(new Data("Listen er tom", "true"));
                                }

                                //-------------- Bare for å hente informasjon ----------------------------
                                Random random = new Random();
                                int number = random.nextInt(list.size());

                                Data choosen = list.get(number);

                                // ----------- Id ------------

                                question = findViewById(R.id.txtview_question);
                                answer = findViewById(R.id.txtview_answer);
                                quizBtnTrue = findViewById(R.id.btnTrue);
                                quizBtnFalse = findViewById(R.id.btnFalse);

                                // ------------ Ui --------------

                                question.setText(choosen.getQuestion());


                                quizBtnFalse.setOnClickListener(v -> {
                                    // Code here executes on main thread after user presses button
                                    answer.setText(answerCheck(choosen.getAnswer(), quizBtnFalse.getText().toString()));
                                });

                                quizBtnTrue.setOnClickListener(v -> {
                                    // Code here executes on main thread after user presses button
                                    answer.setText(answerCheck(choosen.getAnswer(), quizBtnTrue.getText().toString()));

                                });
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    //----------- Check answer function for alle quizzer ---------
    public String answerCheck(String answer, String chosen) {
        String valuation = "";
        if (answer.equals(chosen)) {
            valuation = "Correct!!";
        } else {
            valuation = "Wrong... Try again..";
        }
        return valuation;
    }

    // --------------- Alle Dokumenter ----------------
    public void allDocsContent() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //-------------- Bare for å hente informasjon ----------------------------
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        db.collection("documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                list.add(document.getData());
                                idList.add(document.getId());
                            }
                            changeFragment(new AllDocumentsFragment(list, idList));

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    //------------- Se et dokument ----------------
    public void singleDocData(String sessionId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        Lager en name/id som blir kalt på i neste activity.
//        String sessionId = getIntent().getStringExtra("ID_FOR_VALGTE_DOKUMENTET");
        System.out.println(sessionId);

        DocumentReference docRef = db.collection("documents").document(sessionId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        titleSingleDoc = findViewById(R.id.single_title_doc1);
                        contentSingleDoc = findViewById(R.id.single_content_doc1);

                        docTitle = document.getData().get("title").toString();
                        docContent = document.getData().get("content").toString();
//                                Setter Tittel og dokumenttekst på siden
                        titleSingleDoc.setText(docTitle);
                        contentSingleDoc.setText(docContent);

                    } else {
                        System.out.println("Dokumentet eksisterer ikke.");
                    }

                } else {
                    Log.w(TAG, "Error getting document.", task.getException());
                }
            }
        });
    }

    // --------------- Lag et dokument ----------------
    public void createADocument() {
        //----------------ID ------------------------------

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        titleDoc = findViewById(R.id.input_title);
        contextDoc = findViewById(R.id.input_context);
// Må få inplementert noe "POST" til online databasen med dette. Tenker det er det letteste her.

        // Add a new document with a generated ID
        Map<String, Object> doc = new HashMap<>();
        doc.put("title", titleDoc.getText().toString());
        doc.put("content", contextDoc.getText().toString());


        db.collection("documents").document()
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Context contextToast = getApplicationContext();
                        CharSequence text = titleDoc.getText().toString() + ": saved!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextToast, text, duration);
                        toast.show();

                        titleDoc.setText("");
                        contextDoc.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    // --------------- Lag en quiz for true or false ----------------

    public void createAQuizTrue() {
        questionEdit = findViewById(R.id.question);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a new document with a generated ID
        Map<String, Object> doc = new HashMap<>();
        doc.put("answer", "true");


        db.collection("quizTime").document(questionEdit.getText().toString())
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Context contextToast = getApplicationContext();
                        CharSequence text = "Question saved!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextToast, text, duration);
                        toast.show();

                        questionEdit.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    public void createAQuizFalse() {
        questionEdit = findViewById(R.id.question);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a new document with a generated ID
        Map<String, Object> doc = new HashMap<>();
        doc.put("answer", "false");


        db.collection("quizTime").document(questionEdit.getText().toString())
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Context contextToast = getApplicationContext();
                        CharSequence text = "Question saved!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextToast, text, duration);
                        toast.show();

                        questionEdit.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    //----------- Endre Fragment som blir vist på Activity ---------------
    public void changeFragment(Fragment fragment) {
//        Alt som trengs for å bytte til et annet fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }


    //    ------------ NAV bar --------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_frontpage:
            case R.id.frontpage:
//                Går til forsiden med side nav og bottom nav
                changeFragment(new FrontpageFragment());
                break;
            case R.id.nav_quiz:
            case R.id.quiz:
                changeFragment(new AllQuizFragment());
                break;
            case R.id.nav_all_documents:
            case R.id.alldocuments:
                allDocsContent();
                break;
            case R.id.nav_search:
                changeFragment(new SearchFragment());
                break;
            case R.id.nav_documents:
//                Lage et dokument
                changeFragment(new CreateDocumentFragment());
                break;
            case R.id.nav_create_quiz:
                changeFragment(new CreateQuizFragment());
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