package com.boostcamp.dreampicker.view.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.view.FeedFragment;
import com.boostcamp.dreampicker.view.profile.ProfileFragment;
import com.boostcamp.dreampicker.view.ResultFragment;
import com.boostcamp.dreampicker.view.SearchFragment;
import com.boostcamp.dreampicker.view.UpLoadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private FeedFragment feedFragment = new FeedFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private UpLoadFragment upLoadFragment = new UpLoadFragment();
    private ResultFragment resultFragment = new ResultFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,feedFragment).commitAllowingStateLoss();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (menuItem.getItemId()) {

            case R.id.navigation_home:
                fragmentTransaction.replace(R.id.frame,feedFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_search:
                fragmentTransaction.replace(R.id.frame,searchFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_upload:
                fragmentTransaction.replace(R.id.frame,upLoadFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_notifications:
                fragmentTransaction.replace(R.id.frame,resultFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_profile:
                fragmentTransaction.replace(R.id.frame, ProfileFragment.getInstance())
                        .commitAllowingStateLoss();
                return true;
        }
        return false;
    }
}
