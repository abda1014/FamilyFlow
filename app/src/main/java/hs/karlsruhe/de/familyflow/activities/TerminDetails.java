package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;

public class TerminDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details);

        // Beispiel: Hole den Termin aus dem Intent
        String termin = getIntent().getStringExtra("termin");

        TextView textView = findViewById(R.id.details_text);
        textView.setText(termin); // Zeige die Details
    }
}
