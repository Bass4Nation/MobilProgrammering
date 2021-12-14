package com.kristoss.randomfacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class AllDocumentsFragment extends Fragment  {

    ArrayList<Map<String, Object>> list;
    ArrayList<String> idList;

    public AllDocumentsFragment(ArrayList<Map<String, Object>> list, ArrayList<String> idList){
        this.list = list;
        this.idList = idList;
    }
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_all_documents, container,false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new AllDocumentAdapter(list, idList));


        return view;
    }
}