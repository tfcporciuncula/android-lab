package xyz.javalab.androidlab.criminalintent.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import xyz.javalab.androidlab.R;
import xyz.javalab.androidlab.criminalintent.fragments.CrimeFragment;
import xyz.javalab.androidlab.criminalintent.model.Crime;
import xyz.javalab.androidlab.criminalintent.model.CrimeLab;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "xyz.javalab.androidlab.criminalintent.crime_id";

    private List<Crime> crimes;
    private ViewPager viewPager;

    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        crimes = CrimeLab.getInstance(this).getCrimes();

        viewPager = (ViewPager) findViewById(R.id.activity_crime_view_pager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.newInstance(crimes.get(position).getId());
            }

            @Override
            public int getCount() {
                return crimes.size();
            }
        });

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        setCurrentCrime(crimeId);
    }

    private void setCurrentCrime(UUID crimeId) {
        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getId().equals(crimeId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
