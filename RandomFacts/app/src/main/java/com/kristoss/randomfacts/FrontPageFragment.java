package com.kristoss.randomfacts;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.support.v4.app.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FrontPageFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = getLayoutInflater().inflate(R.layout.fragment_frontpage,null);
        String update_str = getString(R.string.title);

        TextView text = (TextView) inflatedView.findViewById(R.id.textViewTitle1);
        System.out.println("----------------------\n" + text.getText());
        Log.d(TAG, "onCreate: " + text.getText());
        text.setText(update_str);
        System.out.println("----------------------\n" + text.getText());

        return inflater.inflate(R.layout.fragment_frontpage, container,false);
    }
}
