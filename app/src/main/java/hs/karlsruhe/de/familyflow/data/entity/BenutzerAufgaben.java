package hs.karlsruhe.de.familyflow.data.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;

@Entity(
        tableName = "BenutzerAufgaben",
        primaryKeys = {"benutzerId", "aufgabeId"},
        indices = {
                // Index the foreign key columns to avoid full-table scans
                @Index(value = "benutzerId"),
                @Index(value = "aufgabeId")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Benutzer.class,
                        parentColumns = "benutzerId",
                        childColumns = "benutzerId",
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
