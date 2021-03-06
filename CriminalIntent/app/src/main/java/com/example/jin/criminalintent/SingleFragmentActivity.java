package com.example.jin.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by jin on 2017/4/10.
 */

public abstract class SingleFragmentActivity extends FragmentActivity{

    protected abstract Fragment creatFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);

        if (fragment==null){
            fragment=creatFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
