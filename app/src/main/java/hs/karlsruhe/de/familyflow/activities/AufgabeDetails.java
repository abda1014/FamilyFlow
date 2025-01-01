package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;

public class AufgabeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_details);

        // Beispiel: Hole die Aufgabe aus dem Intent
        String aufgabe = getIntent().getStringExtra("aufgabe");

        TextView textView = findViewById(R.id.details_text);
        textView.setText(aufgabe); // Zeige die Details
    }
}
