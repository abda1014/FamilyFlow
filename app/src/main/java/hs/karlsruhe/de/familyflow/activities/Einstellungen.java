package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });
    }

    // Methode für den Bestätigungsdialog
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Ausloggen?")
                .setMessage("Möchtest du dich wirklich ausloggen?")
                .setPositiveButton("Ja", (dialog, which) -> {
                    // Wenn der Benutzer "Ja" klickt, dann logout
                    logoutUser();
                })
                .setNegativeButton("Nein", (dialog, which) -> {
                    // Wenn der Benutzer "Nein" klickt, Dialog schließen
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    // Logout-Logik
    private void logoutUser() {

        Intent intent = new Intent(Einstellungen.this, Login.class);
        startActivity(intent);

        // Schließe die aktuelle Activity, um sie aus dem Stack zu entfernen
        finish();

        //  Zeige eine kurze Bestätigungsmeldung
        Toast.makeText(this, "Erfolgreich abgemeldet", Toast.LENGTH_SHORT).show();
    }
}
