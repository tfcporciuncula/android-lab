package xyz.javalab.androidlab.criminalintent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import xyz.javalab.androidlab.R;
import xyz.javalab.androidlab.criminalintent.activities.CrimePagerActivity;
import xyz.javalab.androidlab.criminalintent.model.Crime;
import xyz.javalab.androidlab.criminalintent.model.CrimeLab;

public class CrimeListFragment extends Fragment {

    private static final String KEY_IS_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView recyclerView;
    private CrimeAdapter adapter;
    private boolean isSubtitleNotVisible = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        if (savedInstanceState != null) {
            isSubtitleNotVisible = savedInstanceState.getBoolean(KEY_IS_SUBTITLE_VISIBLE);
        }

        setUpWidgets(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_SUBTITLE_VISIBLE, isSubtitleNotVisible);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle_menu_item);
        if (isSubtitleNotVisible) {
            subtitleItem.setTitle(R.string.show_subtitle);
        } else {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_crime_menu_item) {
            Crime crime = new Crime();
            CrimeLab.getInstance(getActivity()).addCrime(crime);
            Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.show_subtitle_menu_item) {
            isSubtitleNotVisible = !isSubtitleNotVisible;
            getActivity().invalidateOptionsMenu();
            updateSubtitle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSubtitle() {
        int crimeCount = CrimeLab.getInstance(getActivity()).getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (isSubtitleNotVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void setUpWidgets(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.crime_recicler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUi();
    }

    private void updateUi() {
        List<Crime> crimes = CrimeLab.getInstance(getActivity()).getCrimes();

        if (adapter == null) {
            adapter = new CrimeAdapter(crimes);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }


    private class CrimeHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView dateTextView;
        private CheckBox solvedCheckBox;

        private Crime crime;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(CrimePagerActivity.newIntent(getActivity(), crime.getId()));
                }
            });

            titleTextView  = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            dateTextView   = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            solvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    crime.setSolved(isChecked);
                }
            });
        }

        public void bindCrime(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            dateTextView.setText(crime.getDate().toString());
            solvedCheckBox.setChecked(crime.isSolved());
        }

    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            holder.bindCrime(crimes.get(position));
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }

    }

}
