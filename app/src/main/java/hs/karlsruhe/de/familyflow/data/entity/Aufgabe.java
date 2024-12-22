package com.example.familyflow.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Aufgabe")
public class Aufgabe {

    @NonNull
    @PrimaryKey
    private String aufgabeId; // UUID als String

    private String aufgabenbezeichnung;
    private String status;
    // Fälligkeitsdatum laut ER-Modell: "DATETIME(Date)"
    // Hier als String oder später per TypeConverter
    private String faelligkeitsdatum;
    private String notiz;

    public Aufgabe() {
        // Leerer Konstruktor
    }

    public Aufgabe(@NonNull String aufgabeId,
                   String aufgabenbezeichnung,
                   String status,
                   String faelligkeitsdatum,
                   String notiz) {
        this.aufgabeId = aufgabeId;
        this.aufgabenbezeichnung = aufgabenbezeichnung;
        this.status = status;
        this.faelligkeitsdatum = faelligkeitsdatum;
        this.notiz = notiz;
    }

    // -- Getter & Setter --

    @NonNull
    public String getAufgabeId() {return aufgabeId;}

    public void setAufgabeId(@NonNull String aufgabeId) {this.aufgabeId = aufgabeId;}

    public String getAufgabenbezeichnung() {return aufgabenbezeichnung;}

    public void setAufgabenbezeichnung(String aufgabenbezeichnung) {this.aufgabenbezeichnung = aufgabenbezeichnung;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getFaelligkeitsdatum() {return faelligkeitsdatum;}

    public void setFaelligkeitsdatum(String faelligkeitsdatum) {this.faelligkeitsdatum = faelligkeitsdatum;}

    public String getNotiz() {return notiz;}

    public void setNotiz(String notiz) {this.notiz = notiz;}
    public boolean isDeleted() {return isDeleted;}

    public void setDeleted(boolean deleted) {isDeleted = deleted;}
}
