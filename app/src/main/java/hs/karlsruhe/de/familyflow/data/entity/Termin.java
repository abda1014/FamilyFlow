package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Termin")
public class Termin {

    @NonNull
    @PrimaryKey
    private String terminId;  // UUID als String

    private String terminname;
    // Datum, Uhrzeit => laut ER-Modell "DATETIME(Date/Time)"
    private String datum;
    private String uhrzeit;

    // Wiederholung ENUM (z.B. "TÄGLICH", "WÖCHENTLICH", "KEINE", etc.)
    // Hier als String
    private String wiederholung;

    private String beschreibung;

    private boolean isDeleted; // Neue Variable hinzugefügt

    public Termin() {
        // Leerer Konstruktor
    }
    @Ignore
    public Termin(@NonNull String terminId,
                  String terminname,
                  String datum,
                  String uhrzeit,
                  String wiederholung,
                  String beschreibung,
                  boolean isDeleted) { // Konstruktor erweitert
        this.terminId = terminId;
        this.terminname = terminname;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.wiederholung = wiederholung;
        this.beschreibung = beschreibung;
        this.isDeleted = isDeleted;
    }

    // -- Getter & Setter --

    @NonNull
    public String getTerminId() {
        return terminId;
    }

    public void setTerminId(@NonNull String terminId) {
        this.terminId = terminId;
    }

    public String getTerminname() {
        return terminname;
    }

    public void setTerminname(String terminname) {
        this.terminname = terminname;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public String getWiederholung() {
        return wiederholung;
    }

    public void setWiederholung(String wiederholung) {
        this.wiederholung = wiederholung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
