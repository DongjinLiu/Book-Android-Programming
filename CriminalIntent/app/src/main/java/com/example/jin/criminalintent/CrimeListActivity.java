package com.example.jin.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by jin on 2017/4/10.
 */

public class CrimeListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment creatFragment() {
        return new CrimeFragment();
    }
}
