package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "BenutzerAufgaben",
        primaryKeys = {"benutzerId", "aufgabeId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Benutzer.class,    // statt Benutzer.class
                        parentColumns = "benutzerId",  // statt nutzerId
                        childColumns = "benutzerId",   // statt nutzerId
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Aufgabe.class,
                        parentColumns = "aufgabeId",
                        childColumns = "aufgabeId",
                        onDelete = CASCADE
                )
        }
)
public class BenutzerAufgaben {

    @NonNull
    private String benutzerId;  

    @NonNull
    private String aufgabeId;

    public BenutzerAufgaben(@NonNull String benutzerId, @NonNull String aufgabeId) {
        this.benutzerId = benutzerId;
        this.aufgabeId = aufgabeId;
    }

    // -- Getter & Setter --

    @NonNull
    public String getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(@NonNull String benutzerId) {
        this.benutzerId = benutzerId;
    }

    @NonNull
    public String getAufgabeId() {
        return aufgabeId;
    }

    public void setAufgabeId(@NonNull String aufgabeId) {
        this.aufgabeId = aufgabeId;
    }
}
