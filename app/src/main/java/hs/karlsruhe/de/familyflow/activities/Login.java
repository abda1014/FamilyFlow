package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import hs.karlsruhe.de.familyflow.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisiere Frames als ConstraintLayout
        ConstraintLayout frameInitial = findViewById(R.id.frameInitial);
        ConstraintLayout frameLogin = findViewById(R.id.frameLogin);

        // Ursprünglicher Login-Button
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Zeigt Frame mit Eingabefeldern und versteckt das Initial-Frame
        buttonLogin.setOnClickListener(v -> {
            frameInitial.setVisibility(View.GONE);
            frameLogin.setVisibility(View.VISIBLE);
        });

        // Neuen Login-Button initialisieren
        Button buttonNewLogin = findViewById(R.id.buttonNewLogin);

        // Navigiert zur Pinnwand-Activity
        buttonNewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Pinnwand.class);
            startActivity(intent);
        });
    }
}
