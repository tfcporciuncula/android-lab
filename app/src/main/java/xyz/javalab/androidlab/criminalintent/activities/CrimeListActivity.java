package xyz.javalab.androidlab.criminalintent.activities;

import android.support.v4.app.Fragment;

import xyz.javalab.androidlab.criminalintent.fragments.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new CrimeListFragment();
    }

}
