package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Die Annotation @Entity markiert die Klasse als Entity für Room, mit einer Tabelle namens "Aufgabe".
@Entity(tableName = "Aufgabe")
public class Aufgabe {

    // Die Aufgabe-ID wird als Primärschlüssel verwendet (nicht null erlaubt).
    @NonNull
    @PrimaryKey
    private String aufgabeId; // UUID als String

    // Die Bezeichnung der Aufgabe (z.B. Titel oder Beschreibung).
    private String aufgabenbezeichnung;

    // Der Status der Aufgabe (z.B. "offen", "abgeschlossen").
    private String status;

    // Das Fälligkeitsdatum der Aufgabe, als String gespeichert.
    // Laut ER-Modell sollte es ein DATETIME-Typ sein, aber hier wird es als String gespeichert.
    private String faelligkeitsdatum;

    // Zusätzliche Notizen zur Aufgabe.
    private String notiz;

    // Eine Variable, die angibt, ob die Aufgabe gelöscht wurde.
    private boolean isDeleted; // Neue Variable hinzugefügt

    // Standard-Konstruktor ohne Parameter.
    public Aufgabe() {
        // Leerer Konstruktor
    }

    // Konstruktor mit Parametern für die Initialisierung der Aufgabe.
    @Ignore // Diese Konstruktor wird von Room ignoriert (nicht in die Datenbank geschrieben).
    public Aufgabe(@NonNull String aufgabeId,
                   String aufgabenbezeichnung,
                   String status,
                   String faelligkeitsdatum,
                   String notiz,
                   boolean isDeleted) { // Konstruktor erweitert
        this.aufgabeId = aufgabeId;
        this.aufgabenbezeichnung = aufgabenbezeichnung;
        this.status = status;
        this.faelligkeitsdatum = faelligkeitsdatum;
        this.notiz = notiz;
        this.isDeleted = isDeleted;
    }

    // -- Getter & Setter Methoden für die Instanzvariablen --

    @NonNull
    // Gibt die Aufgabe-ID zurück (primärer Schlüssel).
    public String getAufgabeId() {
        return aufgabeId;
    }

    // Setzt die Aufgabe-ID (primärer Schlüssel).
    public void setAufgabeId(@NonNull String aufgabeId) {
        this.aufgabeId = aufgabeId;
    }

    // Gibt die Aufgabenbezeichnung zurück.
    public String getAufgabenbezeichnung() {
        return aufgabenbezeichnung;
    }

    // Setzt die Aufgabenbezeichnung.
    public void setAufgabenbezeichnung(String aufgabenbezeichnung) {
        this.aufgabenbezeichnung = aufgabenbezeichnung;
    }

    // Gibt den Status der Aufgabe zurück.
    public String getStatus() {
        return status;
    }

    // Setzt den Status der Aufgabe.
    public void setStatus(String status) {
        this.status = status;
    }

    // Gibt das Fälligkeitsdatum zurück.
    public String getFaelligkeitsdatum() {
        return faelligkeitsdatum;
    }

    // Setzt das Fälligkeitsdatum.
    public void setFaelligkeitsdatum(String faelligkeitsdatum) {
        this.faelligkeitsdatum = faelligkeitsdatum;
    }

    // Gibt die Notizen zur Aufgabe zurück.
    public String getNotiz() {
        return notiz;
    }

    // Setzt die Notizen zur Aufgabe.
    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    // Gibt zurück, ob die Aufgabe gelöscht wurde.
    public boolean isDeleted() {
        return isDeleted;
    }

    // Setzt den Löschstatus der Aufgabe.
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
