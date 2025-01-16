package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Die Annotation @Entity markiert die Klasse als Entity für Room, mit einer Tabelle namens "Termin".
@Entity(tableName = "Termin")
public class Termin {

    // Die Termin-ID wird als Primärschlüssel verwendet (nicht null erlaubt).
    @NonNull
    @PrimaryKey
    private String terminId;  // UUID als String

    // Der Name des Termins (z.B. "Team Meeting", "Arzttermin").
    private String terminname;

    // Das Datum des Termins (laut ER-Modell als DATETIME gespeichert).
    // Hier wird es als String gespeichert.
    private String datum;

    // Die Uhrzeit des Termins.
    private String uhrzeit;

    // Wiederholungs-Status des Termins (z.B. "TÄGLICH", "WÖCHENTLICH", "KEINE").
    // Hier als String gespeichert.
    private String wiederholung;

    // Eine detaillierte Beschreibung des Termins.
    private String beschreibung;

    // Soft-Delete-Flag, das angibt, ob der Termin gelöscht wurde.
    private boolean isDeleted; // Neue Variable hinzugefügt

    // Standard-Konstruktor ohne Parameter (für Room).
    public Termin() {
        // Leerer Konstruktor
    }

    // Konstruktor mit Parametern, um ein Terminobjekt mit spezifischen Werten zu erstellen.
    @Ignore // Diese Konstruktor wird von Room ignoriert (nicht in die Datenbank geschrieben).
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

    // -- Getter & Setter Methoden für die Instanzvariablen --

    @NonNull
    // Gibt die Termin-ID zurück (primärer Schlüssel).
    public String getTerminId() {
        return terminId;
    }

    // Setzt die Termin-ID (primärer Schlüssel).
    public void setTerminId(@NonNull String terminId) {
        this.terminId = terminId;
    }

    // Gibt den Namen des Termins zurück.
    public String getTerminname() {
        return terminname;
    }

    // Setzt den Namen des Termins.
    public void setTerminname(String terminname) {
        this.terminname = terminname;
    }

    // Gibt das Datum des Termins zurück.
    public String getDatum() {
        return datum;
    }

    // Setzt das Datum des Termins.
    public void setDatum(String datum) {
        this.datum = datum;
    }

    // Gibt die Uhrzeit des Termins zurück.
    public String getUhrzeit() {
        return uhrzeit;
    }

    // Setzt die Uhrzeit des Termins.
    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    // Gibt die Wiederholung des Termins zurück.
    public String getWiederholung() {
        return wiederholung;
    }

    // Setzt die Wiederholung des Termins.
    public void setWiederholung(String wiederholung) {
        this.wiederholung = wiederholung;
    }

    // Gibt die Beschreibung des Termins zurück.
    public String getBeschreibung() {
        return beschreibung;
    }

    // Setzt die Beschreibung des Termins.
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    // Gibt zurück, ob der Termin gelöscht wurde (Soft Delete).
    public boolean isDeleted() {
        return isDeleted;
    }

    // Setzt den Löschstatus des Termins.
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
