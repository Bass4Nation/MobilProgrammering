package com.kristoss.randomfacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

public class ADocumentFragment extends Fragment {
    View view;
    String data;



    public ADocumentFragment(String data) {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).singleDocData(data);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_a_document, container, false);

        return view;
    }
}