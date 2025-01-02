package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.fragments.AufgabeDetailsFragment;
import hs.karlsruhe.de.familyflow.fragments.AufgabeUebersichtFragment;

public class AufgabeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe); // Layout für den Fragment-Container

        // Starte das Übersicht-Fragment beim ersten Laden
        if (savedInstanceState == null) {
            loadFragment(new AufgabeUebersichtFragment());
        }
    }

    // Methode zum Laden eines Fragments
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AufgabeDetailsFragment())
                .addToBackStack(null)
                .commit();

    }

    // Ermöglicht anderen Fragments die Navigation
    public void navigateTo(Fragment fragment) {
        loadFragment(fragment);
    }
}
