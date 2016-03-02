package xyz.javalab.androidlab.criminalintent.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab instance;
    private List<Crime> crimes = new ArrayList<>();

    private CrimeLab() {}

    public static CrimeLab getInstance(Context context) {
        if (instance == null) {
            instance = new CrimeLab();
        }
        return instance;
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : crimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    public void addCrime(Crime crime) {
        crimes.add(crime);
    }

}
