package com.example.familyflow.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Nutzer")
public class Nutzer {

    @NonNull
    @PrimaryKey
    private String nutzerId;  // UUID als String

    private String vorname;
    private String nachname;
    private String email;

    // Alter -> laut ER-Modell "Datetime(Date)"
    // Hier vereinfacht als String gespeichert
    private String alterDatum;

    public Nutzer() {
        // Leerer Konstruktor (für Room)
    }

    public Nutzer(@NonNull String nutzerId,
                  String vorname,
                  String nachname,
                  String email,
                  String alterDatum) {
        this.nutzerId = nutzerId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.alterDatum = alterDatum;
    }

    // -- Getter & Setter --

    @NonNull
    public String getNutzerId() {
        return nutzerId;
    }

    public void setNutzerId(@NonNull String nutzerId) {
        this.nutzerId = nutzerId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlterDatum() {
        return alterDatum;
    }

    public void setAlterDatum(String alterDatum) {
        this.alterDatum = alterDatum;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
