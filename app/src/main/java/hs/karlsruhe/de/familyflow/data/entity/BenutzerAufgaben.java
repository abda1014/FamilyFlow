package hs.karlsruhe.de.familyflow.data.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;

// Die Annotation @Entity markiert die Klasse als Entity für Room, mit einer Tabelle namens "BenutzerAufgaben".
@Entity(
        tableName = "BenutzerAufgaben", // Der Name der Tabelle in der Datenbank
        primaryKeys = {"benutzerId", "aufgabeId"}, // Primärschlüssel ist eine Kombination aus benutzerId und aufgabeId
        indices = {
                // Indexe für die Fremdschlüsselspalten, um vollständige Tabellen-Scans zu vermeiden
                @Index(value = "benutzerId"),
                @Index(value = "aufgabeId")
        },
        foreignKeys = {
                // Fremdschlüssel für benutzerId, verweist auf die benutzerId in der Tabelle "Benutzer"
                @ForeignKey(
                        entity = Benutzer.class, // Die referenzierte Entität ist "Benutzer"
                        parentColumns = "benutzerId", // Die Spalte in der "Benutzer" Tabelle
                        childColumns = "benutzerId", // Die Spalte in der "BenutzerAufgaben" Tabelle
                        onDelete = CASCADE // Wenn der Benutzer gelöscht wird, wird auch der Eintrag in "BenutzerAufgaben" gelöscht
                ),
                // Fremdschlüssel für aufgabeId, verweist auf die aufgabeId in der Tabelle "Aufgabe"
                @ForeignKey(
                        entity = Aufgabe.class, // Die referenzierte Entität ist "Aufgabe"
                        parentColumns = "aufgabeId", // Die Spalte in der "Aufgabe" Tabelle
                        childColumns = "aufgabeId", // Die Spalte in der "BenutzerAufgaben" Tabelle
                        onDelete = CASCADE // Wenn die Aufgabe gelöscht wird, wird auch der Eintrag in "BenutzerAufgaben" gelöscht
                )
        }
)
public class BenutzerAufgaben {

    // Die ID des Benutzers, der mit der Aufgabe verbunden ist (Fremdschlüssel).
    @NonNull
    private String benutzerId;

    // Die ID der Aufgabe, die mit dem Benutzer verbunden ist (Fremdschlüssel).
    @NonNull
    private String aufgabeId;

    // Konstruktor für die Initialisierung eines "BenutzerAufgaben"-Objekts.
    public BenutzerAufgaben(@NonNull String benutzerId, @NonNull String aufgabeId) {
        this.benutzerId = benutzerId;
        this.aufgabeId = aufgabeId;
    }

    // -- Getter & Setter Methoden für die Instanzvariablen --

    @NonNull
    // Gibt die Benutzer-ID zurück.
    public String getBenutzerId() {
        return benutzerId;
    }

    // Setzt die Benutzer-ID.
    public void setBenutzerId(@NonNull String benutzerId) {
        this.benutzerId = benutzerId;
    }

    @NonNull
    // Gibt die Aufgaben-ID zurück.
    public String getAufgabeId() {
        return aufgabeId;
    }

    // Setzt die Aufgaben-ID.
    public void setAufgabeId(@NonNull String aufgabeId) {
        this.aufgabeId = aufgabeId;
    }
}
