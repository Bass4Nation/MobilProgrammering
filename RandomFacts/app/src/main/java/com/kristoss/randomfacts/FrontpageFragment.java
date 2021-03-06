package com.kristoss.randomfacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FrontpageFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btnFrontNext;

    public FrontpageFragment(){
        // require a empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).frontPageContent();
        view = inflater.inflate(R.layout.fragment_frontpage, container, false);


        btnFrontNext = (Button) view.findViewById(R.id.btnNext);
        btnFrontNext.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
//        Når man trykker neste så skjer dette
        ((MainActivity) getActivity()).changeFragment(new FrontpageFragment());
    }
}