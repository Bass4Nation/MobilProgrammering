package com.kristoss.randomfacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AllQuizFragment extends Fragment implements View.OnClickListener {
    View view;
    Button quizNext;

    public AllQuizFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).quizContent();


        view = inflater.inflate(R.layout.fragment_all_quiz, container, false);

        quizNext = (Button) view.findViewById(R.id.btn_quix_next);
        quizNext.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
//        Når man trykker neste så skjer dette
        ((MainActivity) getActivity()).changeFragment(new AllQuizFragment());
    }
}