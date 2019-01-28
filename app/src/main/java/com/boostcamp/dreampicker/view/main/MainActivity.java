package com.boostcamp.dreampicker.view.main;

import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.view.MyPageFragment;
import com.boostcamp.dreampicker.view.ResultFragment;
import com.boostcamp.dreampicker.view.SearchFragment;
import com.boostcamp.dreampicker.view.UpLoadFragment;
import com.boostcamp.dreampicker.view.feed.FeedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager = getSupportFragmentManager();

    private FeedFragment feedFragment = new FeedFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private UpLoadFragment upLoadFragment = new UpLoadFragment();
    private ResultFragment resultFragment = new ResultFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {

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
                    case R.id.navigation_my_page:
                        fragmentTransaction.replace(R.id.frame,myPageFragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,feedFragment).commitAllowingStateLoss();

         BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}