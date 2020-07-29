package com.example.readingshares.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.readingshares.R;
import com.example.readingshares.fragment.AccountFragment;
import com.example.readingshares.fragment.FavoriteFragment;
import com.example.readingshares.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment=null;
            switch (item.getItemId()){

                case R.id.home:
                    selectedFragment= new HomeFragment();
                    break;
                case R.id.favorite:
                    selectedFragment= new FavoriteFragment();
                    break;
                case R.id.account:
                    selectedFragment= new AccountFragment();
                    break;
                case R.id.setting:
                    selectedFragment= new SettingActivity();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
            return true;
        }
    };
}