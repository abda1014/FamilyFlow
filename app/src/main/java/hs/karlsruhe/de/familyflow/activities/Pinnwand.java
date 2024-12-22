package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hs.karlsruhe.de.familyflow.R;


public class Pinnwand extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinnwand);
    }

    public void calculateClickHandler(View view) {

        //Stellt sicher das der Klick auf den Berechnen Button behandelt wird
//        if (view.getId() == R.id.berechnenButton) {
//
//            try {
//                //Definiere die Referenzen zu den Feldern
//                gewichtText = (EditText) findViewById(R.id.gewichtText);
//                hoeheText = (EditText) findViewById(R.id.hoeheText);
//                resultText = (TextView) findViewById(R.id.resultLabel);
//
//
//                //Erhält die Werte des Benutzers
//                float gewicht = Float.parseFloat(gewichtText.getText().toString());
//                float hoehe = Float.parseFloat(hoeheText.getText().toString());
//
//
//                //Berechnet den BMI Wert
//                float bmiWert = berechneBMI(gewicht, hoehe);
//
//                // Interpretiert den Wert
//                String bmiInterpretation = interpretiereBMI(bmiWert);
//
//                // Setzt den Wert in das Result Feld welches angezeigt wird
//
//                resultText.setText(bmiWert + " - " + bmiInterpretation);
//
//                //System.out.println in anderen Umgebungen
//                Context context = getApplicationContext();
//                CharSequence text = bmiInterpretation;
//                int duration = Toast.LENGTH_LONG;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//
//            }catch (NumberFormatException e) {
//
//                resultText.setText("Falsche Eingabe");
//                resultText.setTextColor(Color.RED);
//            }
//        }
    }

    public void beendenHandler(View view){

//        if(view.getId() == R.id.beendenButton){
//            finish();
//            System.exit(0);
//        }
    }
}
