package com.boostcamp.dreampicker.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.view.feed.FeedFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_a, new Fragment())
                .commit();
    }
}
