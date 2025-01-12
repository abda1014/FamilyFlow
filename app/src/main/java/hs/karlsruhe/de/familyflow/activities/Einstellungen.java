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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_einstellung);
        //InitialisiereClickHandler(findViewById(R.id.settingsFrame));

    }


    public void InitialisiereClickHandler(View view) {

        //initialisiere Auslog-Button
        ImageButton settingsButton = findViewById(R.id.buttonLogout);
        settingsButton.setOnClickListener(v -> Ausloggen());
    }

    private void Ausloggen() {
        Intent intent = new Intent(Einstellungen.this, Login.class);
        startActivity(intent);
    }
}