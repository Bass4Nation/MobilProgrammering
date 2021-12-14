package com.kristoss.randomfacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment implements View.OnClickListener {
    View view;
    Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);


        btnSearch = (Button) view.findViewById(R.id.search_button);
        btnSearch.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
//        Når man trykker search så skjer dette
        ((MainActivity) getActivity()).searchButton();
    }


}