package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import hs.karlsruhe.de.familyflow.R;



public class Einstellungen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellung);

        // Logout-Button finden
       Button buttonLogout = findViewById(R.id.buttonLogout);

        // Setze den OnClickListener für den Logout-Button
        buttonLogout.setOnClickListener(v -> {
            // Beispiel-Action für Logout: Benutzer zurück zum Login-Bildschirm leiten
            logoutUser();
        });
    }

    // Logout-Logik
    private void logoutUser() {
        // Hier kannst du den Benutzer abmelden (z.B. bei Firebase Auth oder SharedPreferences)

        // Beispiel: Zur LoginActivity zurück navigieren
        Intent intent = new Intent(Einstellungen.this, Login.class);
        startActivity(intent);

        // Schließe die aktuelle Activity, um sie aus dem Stack zu entfernen
        finish();
    }
}
