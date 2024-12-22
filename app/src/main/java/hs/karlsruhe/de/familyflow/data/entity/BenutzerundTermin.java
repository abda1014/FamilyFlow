package com.example.familyflow.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "Benutzer_Aufgaben",
        primaryKeys = {"benutzerId", "aufgabeId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Benutzer.class,    // statt Nutzer.class
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
public class Benutzer_Aufgaben {

    @NonNull
    private String benutzerId;  // statt nutzerId

    @NonNull
    private String aufgabeId;

    public Benutzer_Aufgaben(@NonNull String benutzerId, @NonNull String aufgabeId) {
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
