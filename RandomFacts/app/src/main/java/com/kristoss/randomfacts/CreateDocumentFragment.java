package com.kristoss.randomfacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CreateDocumentFragment extends Fragment  implements View.OnClickListener  {
    View view;
    Button btnSave;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_document, container, false);


        btnSave = (Button) view.findViewById(R.id.btn_save_doc);
        btnSave.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).createADocument();
    }
}