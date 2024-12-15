package hs.karlsruhe.de.familyflow.entity;

public class Aufgabe {
    private String titel;
    private String beschreibung;
    private String datum;

    public Aufgabe(String titel, String beschreibung, String datum) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.datum = datum;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getDatum() {
        return datum;
    }
}
