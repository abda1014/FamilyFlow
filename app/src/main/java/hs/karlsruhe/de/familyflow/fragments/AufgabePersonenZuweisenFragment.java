package hs.karlsruhe.de.familyflow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hs.karlsruhe.de.familyflow.R;

public class AufgabePersonenZuweisenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // TODO: Logik für das Zuweisen von Personen hinzufügen

        return inflater.inflate(R.layout.fragment_aufgabe_personen_zuweisen, container, false);
    }
}
