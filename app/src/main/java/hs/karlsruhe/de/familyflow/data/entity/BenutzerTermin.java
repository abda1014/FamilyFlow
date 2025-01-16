package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

// Die Annotation @Entity markiert die Klasse als Entity für Room, mit einer Tabelle namens "BenutzerTerminn".
@Entity(
        tableName = "BenutzerTerminn", // Der Name der Tabelle in der Datenbank
        primaryKeys = {"benutzerId", "terminId"}, // Primärschlüssel ist eine Kombination aus benutzerId und terminId
        indices = {
                // Indexe für die Fremdschlüsselspalten, um vollständige Tabellen-Scans zu vermeiden
                @Index(value = "benutzerId"),
                @Index(value = "terminId")
        },
        foreignKeys = {
                // Fremdschlüssel für benutzerId, verweist auf die benutzerId in der Tabelle "Benutzer"
                @ForeignKey(
                        entity = Benutzer.class, // Die referenzierte Entität ist "Benutzer"
                        parentColumns = "benutzerId", // Die Spalte in der "Benutzer" Tabelle
                        childColumns = "benutzerId", // Die Spalte in der "BenutzerTerminn" Tabelle
                        onDelete = CASCADE // Wenn der Benutzer gelöscht wird, wird auch der Eintrag in "BenutzerTerminn" gelöscht
                ),
                // Fremdschlüssel für terminId, verweist auf die terminId in der Tabelle "Termin"
                @ForeignKey(
                        entity = Termin.class, // Die referenzierte Entität ist "Termin"
                        parentColumns = "terminId", // Die Spalte in der "Termin" Tabelle
                        childColumns = "terminId", // Die Spalte in der "BenutzerTerminn" Tabelle
                        onDelete = CASCADE // Wenn der Termin gelöscht wird, wird auch der Eintrag in "BenutzerTerminn" gelöscht
                )
        }
)
public class BenutzerTermin {

    // Die ID des Benutzers, der mit dem Termin verbunden ist (Fremdschlüssel).
    @NonNull
    private String benutzerId;

    // Die ID des Termins, der mit dem Benutzer verbunden ist (Fremdschlüssel).
    @NonNull
    private String terminId;

    // Konstruktor für die Initialisierung eines "BenutzerTermin"-Objekts.
    public BenutzerTermin(@NonNull String benutzerId, @NonNull String terminId) {
        this.benutzerId = benutzerId;
        this.terminId = terminId;
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
    // Gibt die Termin-ID zurück.
    public String getTerminId() {
        return terminId;
    }

    // Setzt die Termin-ID.
    public void setTerminId(@NonNull String terminId) {
        this.terminId = terminId;
    }
}
