package com.boostcamp.dreampicker.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpLoadFragment extends Fragment {


    public UpLoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up_load, container, false);
    }

}
