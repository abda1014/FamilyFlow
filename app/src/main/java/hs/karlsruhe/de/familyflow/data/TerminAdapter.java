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
import hs.karlsruhe.de.familyflow.data.entity.Termin;

// Adapterklasse für die Anzeige von Terminen in einer ListView
public class TerminAdapter extends ArrayAdapter<Termin> {

    private final Context context;  // Kontext der Anwendung
    private final List<Termin> termineListe;  // Liste von Terminen

    // Konstruktor für die Initialisierung des Adapters
    public TerminAdapter(@NonNull Context context, @NonNull List<Termin> termineListe) {
        super(context, R.layout.item_termin, termineListe);  // Übergibt die Layout-Datei und die Liste an den Eltern-Adapter
        this.context = context;
        this.termineListe = termineListe;
    }

    // Methode, um jedes Element in der ListView darzustellen
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Falls die View nicht wiederverwendet werden kann, wird eine neue instanziiert
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_termin, parent, false);
        }

        // Holen des Terminobjekts für die gegebene Position
        Termin termin = termineListe.get(position);

        // Referenzen auf die TextViews im Layout
        TextView textTerminName = convertView.findViewById(R.id.textTerminName);
        TextView textTerminDatum = convertView.findViewById(R.id.textTerminDatum);

        // Setzen der Textwerte für den Terminname und das Datum
        textTerminName.setText(termin.getTerminname());
        textTerminDatum.setText(termin.getDatum());  // Datum des Termins anzeigen

        // Rückgabe der View für das aktuelle Element
        return convertView;
    }
}
