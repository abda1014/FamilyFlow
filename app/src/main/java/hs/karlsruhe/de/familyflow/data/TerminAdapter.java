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

public class TerminAdapter extends ArrayAdapter<Termin> {

    private final Context context;
    private final List<Termin> termineListe;

    public TerminAdapter(@NonNull Context context, @NonNull List<Termin> termineListe) {
        super(context, R.layout.item_termin, termineListe);
        this.context = context;
        this.termineListe = termineListe;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_termin, parent, false);
        }

        Termin termin = termineListe.get(position);

        TextView textTerminName = convertView.findViewById(R.id.textTerminName);
        TextView textTerminDatum = convertView.findViewById(R.id.textTerminDatum);

        // Setzen der Textwerte
        textTerminName.setText(termin.getTerminname());
        textTerminDatum.setText(termin.getDatum()); // Datum des Termins anzeigen

        return convertView;
    }
}

