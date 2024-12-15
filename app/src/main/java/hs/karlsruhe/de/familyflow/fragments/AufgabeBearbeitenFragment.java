package hs.karlsruhe.de.familyflow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hs.karlsruhe.de.familyflow.R;

public class AufgabeBearbeitenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aufgabe_bearbeiten, container, false);

        // TODO: Logik für das Bearbeiten einer Aufgabe hinzufügen

        return view;
    }
}
