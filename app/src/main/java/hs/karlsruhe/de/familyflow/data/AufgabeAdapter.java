package hs.karlsruhe.de.familyflow.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;

// Adapterklasse für die Anzeige von Aufgaben in einer ListView
public class AufgabeAdapter extends ArrayAdapter<Aufgabe> {

    private final Context context;  // Kontext der Anwendung
    private final List<Aufgabe> aufgabenListe;  // Liste von Aufgaben

    // Konstruktor für die Initialisierung des Adapters
    public AufgabeAdapter(@NonNull Context context, @NonNull List<Aufgabe> aufgabenListe) {
        super(context, R.layout.item_aufgabe, aufgabenListe);  // Übergibt die Layout-Datei und die Liste an den Eltern-Adapter
        this.context = context;
        this.aufgabenListe = aufgabenListe;
    }

    // Methode, um jedes Element in der ListView darzustellen
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Falls die View nicht wiederverwendet werden kann, wird eine neue instanziiert
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_aufgabe, parent, false);
        }

        // Holen des Aufgabe-Objekts für die gegebene Position
        Aufgabe aufgabe = aufgabenListe.get(position);

        // Referenzen auf die TextViews im Layout
        TextView textAufgabeName = convertView.findViewById(R.id.textAufgabeName);
        TextView textAufgabeDatum = convertView.findViewById(R.id.textAufgabeDatum);

        // Setzen der Textwerte für die Aufgabenbezeichnung und das Fälligkeitsdatum
        textAufgabeName.setText(aufgabe.getAufgabenbezeichnung());
        textAufgabeDatum.setText(aufgabe.getFaelligkeitsdatum());  // Fälligkeitsdatum der Aufgabe anzeigen

        // Rückgabe der View für das aktuelle Element
        return convertView;
    }
}
