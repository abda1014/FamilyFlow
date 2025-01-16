package hs.karlsruhe.de.familyflow.data.entity;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Die Annotation @Entity markiert die Klasse als Entity für Room, mit einer Tabelle namens "Benutzer".
@Entity(tableName = "Benutzer")
public class Benutzer {

    // Die Benutzer-ID wird als Primärschlüssel verwendet (nicht null erlaubt).
    @NonNull
    @PrimaryKey
    private String benutzerId;  // UUID als String

    // Vorname des Benutzers.
    private String vorname;

    // Nachname des Benutzers.
    private String nachname;

    // E-Mail-Adresse des Benutzers.
    private String email;

    // Das Geburtsdatum des Benutzers (laut ER-Modell als DATETIME gespeichert).
    // Hier wird es jedoch als String gespeichert, um die Vereinfachung zu gewährleisten.
    private String alterDatum;

    // Soft-Delete-Flag, das angibt, ob der Benutzer gelöscht wurde.
    private boolean isDeleted; // Soft Delete Flag

    // Der Passwort-Hash des Benutzers (für die sichere Speicherung des Passworts).
    private String passwordHash; // Passwort-Hash

    // Der Dateiname oder Pfad zum Profilbild des Benutzers.
    private String imageProfil;

    // Standard-Konstruktor ohne Parameter (für Room).
    public Benutzer() {
        // Leerer Konstruktor (für Room)
    }

    // Konstruktor mit Parametern, um ein Benutzerobjekt mit spezifischen Werten zu erstellen.
    @Ignore // Diese Konstruktor wird von Room ignoriert (nicht in die Datenbank geschrieben).
    public Benutzer(@NonNull String benutzerId,
                    String vorname,
                    String nachname,
                    String email,
                    String alterDatum,
                    boolean isDeleted,
                    String passwordHash,
                    String imageProfil) {
        this.benutzerId = benutzerId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.alterDatum = alterDatum;
        this.isDeleted = isDeleted;
        this.passwordHash = passwordHash;
        this.imageProfil = imageProfil;
    }

    // -- Getter & Setter Methoden für die Instanzvariablen --

    @NonNull
    // Gibt die Benutzer-ID zurück (primärer Schlüssel).
    public String getBenutzerId() {
        return benutzerId;
    }

    // Setzt die Benutzer-ID (primärer Schlüssel).
    public void setBenutzerId(@NonNull String benutzerId) {
        this.benutzerId = benutzerId;
    }

    // Gibt den Vornamen des Benutzers zurück.
    public String getVorname() {
        return vorname;
    }

    // Setzt den Vornamen des Benutzers.
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    // Gibt den Nachnamen des Benutzers zurück.
    public String getNachname() {
        return nachname;
    }

    // Setzt den Nachnamen des Benutzers.
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    // Gibt die E-Mail-Adresse des Benutzers zurück.
    public String getEmail() {
        return email;
    }

    // Setzt die E-Mail-Adresse des Benutzers.
    public void setEmail(String email) {
        this.email = email;
    }

    // Gibt das Geburtsdatum des Benutzers zurück.
    public String getAlterDatum() {
        return alterDatum;
    }

    // Setzt das Geburtsdatum des Benutzers.
    public void setAlterDatum(String alterDatum) {
        this.alterDatum = alterDatum;
    }

    // Gibt zurück, ob der Benutzer gelöscht wurde (Soft Delete).
    public boolean isDeleted() {
        return isDeleted;
    }

    // Setzt den Löschstatus des Benutzers.
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    // Öffentlicher Getter für den Passwort-Hash.
    public String getPasswordHash() {
        return passwordHash;
    }

    // Öffentlicher Setter für den Passwort-Hash.
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Gibt den Dateipfad oder Namen des Profilbilds zurück.
    public String getImageProfil() {
        return imageProfil;
    }

    // Setzt den Dateipfad oder Namen des Profilbilds.
    public void setImageProfil(String imageProfil) {
        this.imageProfil = imageProfil;
    }
}
