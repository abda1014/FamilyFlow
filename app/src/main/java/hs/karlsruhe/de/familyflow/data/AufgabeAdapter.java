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

public class AufgabeAdapter extends ArrayAdapter<Aufgabe> {

    private final Context context;
    private final List<Aufgabe> aufgabenListe;

    public AufgabeAdapter(@NonNull Context context, @NonNull List<Aufgabe> aufgabenListe) {
        super(context, R.layout.item_aufgabe, aufgabenListe);
        this.context = context;
        this.aufgabenListe = aufgabenListe;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_aufgabe, parent, false);
        }

        Aufgabe aufgabe = aufgabenListe.get(position);

        TextView textAufgabeName = convertView.findViewById(R.id.textAufgabeName);
        TextView textAufgabeDatum = convertView.findViewById(R.id.textAufgabeDatum);

        textAufgabeName.setText(aufgabe.getAufgabenbezeichnung());
        textAufgabeDatum.setText(aufgabe.getFaelligkeitsdatum());

        return convertView;
    }
}
