package com.kristoss.randomfacts;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.Map;

public class AllDocumentAdapter extends RecyclerView.Adapter<AllDocumentAdapter.MyViewHolder> {


    ArrayList<Map<String, Object>> list;
    ArrayList<String> idList;

    //https://www.youtube.com/watch?v=dxzpgzUoxes
//    Brukte denne videoen om hvordan et recyclerView blir laget.
    public AllDocumentAdapter (ArrayList<Map<String, Object>> list,ArrayList<String> idList){
        this.list = list;
        this.idList = idList;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all_documents_adapter, parent,false);
        return new MyViewHolder(view, list, idList);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDocumentAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position).get("title").toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ArrayList<Map<String, Object>> titles;
        ArrayList<String> idList;


        public MyViewHolder(@NonNull View itemView, ArrayList<Map<String, Object>> titles, ArrayList<String> idList) {
            super(itemView);
            textView = itemView.findViewById(R.id.all_doc_card_textview );
            this.titles = titles;
            this.idList = idList;

            itemView.setOnClickListener(this);

        }
//    https://www.youtube.com/watch?v=69C1ljfDvl0
//        For onClick i Adapteren til recyclerView

        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            ADocumentFragment aDocumentFragment = new ADocumentFragment(idList.get(getAdapterPosition()));
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, aDocumentFragment).commit();
        }
    }
}
