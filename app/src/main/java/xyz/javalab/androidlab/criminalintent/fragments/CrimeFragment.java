package xyz.javalab.androidlab.criminalintent.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import xyz.javalab.androidlab.R;
import xyz.javalab.androidlab.criminalintent.model.Crime;

public class CrimeFragment extends Fragment {

    private Crime crime;

    private EditText titleEditText;
    private Button dateButton;
    private CheckBox solvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        setUpWidgets(view);
        return view;
    }

    private void setUpWidgets(View view) {
        titleEditText = (EditText) view.findViewById(R.id.crime_title_text_view);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        CharSequence formattedDate = DateFormat.format("EEEE, MMM d, yyyy.", crime.getDate());
        String formattedDate = DateFormat.getLongDateFormat(getActivity()).format(crime.getDate());
        dateButton = (Button) view.findViewById(R.id.crime_date_button);
        dateButton.setText(formattedDate);
        dateButton.setEnabled(false);

        solvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved_check_box);
        solvedCheckBox.setChecked(crime.isSolved());
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });
    }

}
