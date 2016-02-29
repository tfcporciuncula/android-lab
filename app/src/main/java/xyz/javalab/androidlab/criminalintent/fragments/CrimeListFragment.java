package xyz.javalab.androidlab.criminalintent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import xyz.javalab.androidlab.R;
import xyz.javalab.androidlab.criminalintent.activities.CrimeActivity;
import xyz.javalab.androidlab.criminalintent.activities.CrimePagerActivity;
import xyz.javalab.androidlab.criminalintent.model.Crime;
import xyz.javalab.androidlab.criminalintent.model.CrimeLab;

public class CrimeListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CrimeAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        setUpWidgets(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
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
