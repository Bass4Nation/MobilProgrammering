package com.kristoss.randomfacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CreateQuizFragment extends Fragment implements View.OnClickListener {
    View view;
    Button btn_save_quiz_true, btn_save_quiz_false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((MainActivity) getActivity()).createAQuiz();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_quiz, container, false);
        btn_save_quiz_true = (Button) view.findViewById(R.id.btn_true_quiz);
        btn_save_quiz_true.setOnClickListener(this);
        btn_save_quiz_false = (Button) view.findViewById(R.id.btn_false_quiz);
        btn_save_quiz_false.setOnClickListener(this);


        return view;
    }

    public void onClick(View v) {
//        Når man trykker search så skjer dette
        switch (v.getId()) {
            case R.id.btn_true_quiz:
                ((MainActivity) getActivity()).createAQuizTrue();
            break;
            case R.id.btn_false_quiz:
                ((MainActivity) getActivity()).createAQuizFalse();
            break;
        }
    }


}