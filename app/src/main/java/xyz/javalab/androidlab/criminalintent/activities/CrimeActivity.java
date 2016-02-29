package xyz.javalab.androidlab.criminalintent.activities;

import android.support.v4.app.Fragment;

import xyz.javalab.androidlab.criminalintent.fragments.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new CrimeFragment();
    }

}
