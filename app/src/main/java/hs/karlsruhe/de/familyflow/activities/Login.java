package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.DBDaten;


public class Login extends AppCompatActivity {

    private EditText eingabeMail;
    private EditText eingabePasswort;
    private TextView fehleranzeige; // Fehleranzeige

    // Simulierte Benutzerdaten
    // private final String SIMULATION_MAIL = "HKA@hka.de";
    // private final String SIMULATION_PASSWORT = "Testuser123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Testdaten initialisieren
        DBDaten.initialisiereDaten(this);

        // Initialisiere Frames als ConstraintLayout
        ConstraintLayout frameInitial = findViewById(R.id.frameInitial);
        ConstraintLayout frameLogin = findViewById(R.id.frameLogin);

        // Initialisiere UI-Elemente
        eingabeMail = findViewById(R.id.editTextMail);
        eingabePasswort = findViewById(R.id.editTextPasswort);
        fehleranzeige = findViewById(R.id.fehlermeldung);

        // Login-Button Frame 1
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Zeigt Frame 2 mit Eingabefeldern und versteckt das Initial-Frame
        buttonLogin.setOnClickListener(v -> {
            frameInitial.setVisibility(View.GONE);
            frameLogin.setVisibility(View.VISIBLE);
        });

        // Login-Button Frame 2 initialisieren
        Button buttonNewLogin = findViewById(R.id.buttonNewLogin);

        // Überprüft die Eingaben und navigiert zur Pinnwand-Activity
        buttonNewLogin.setOnClickListener(v -> userLogin());
    }

    private void userLogin() {
        String mail = eingabeMail.getText().toString().trim();
        String passwort = eingabePasswort.getText().toString().trim();

        // Hash des eingegebenen Passworts berechnen
        String hashedPassword = DBDaten.hashPassword(passwort);

        if (hashedPassword == null) {
            runOnUiThread(() -> fehleranzeige.setText("Fehler bei der Passwort-Hash-Berechnung."));
            return;
        }

        new Thread(() -> {
            AppDatabase db = DatabaseManager.getDatabase(this);
            BenutzerDao benutzerDao = db.benutzerDao();
            Benutzer benutzer = benutzerDao.findBenutzerByEmailAndPassword(mail, hashedPassword);

            runOnUiThread(() -> {
                if (benutzer != null) {
                    // Weiterleitung zur Pinnwand
                    Intent intent = new Intent(Login.this, Pinnwand.class);

                    // Zum Test, ob die Weiterleitung von Login geht.
                    //Intent intent = new Intent(Login.this, LoginTestActivity.class);

                    startActivity(intent);
                } else {
                    // Fehlermeldung anzeigen
                    fehleranzeige.setVisibility(View.VISIBLE);
                    fehleranzeige.setText("Die eingegebenen Daten sind falsch.");
                }
            });
        }).start();
    }

}
