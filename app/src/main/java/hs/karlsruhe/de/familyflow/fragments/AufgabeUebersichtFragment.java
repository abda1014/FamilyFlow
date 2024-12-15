package hs.karlsruhe.de.familyflow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import hs.karlsruhe.de.familyflow.R;

public class AufgabeUebersichtFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aufgabe_uebersicht, container, false);

        // Button-Klick zum Öffnen der Details
        Button buttonDetails = view.findViewById(R.id.button_details);
        buttonDetails.setOnClickListener(v -> {
            AufgabeDetailsFragment detailsFragment = new AufgabeDetailsFragment();
            ((hs.karlsruhe.de.familyflow.activities.AufgabeActivity) getActivity()).navigateTo(detailsFragment);
        });

        return view;
    }
}
