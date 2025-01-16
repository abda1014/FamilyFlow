package hs.karlsruhe.de.familyflow.data.entity;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Benutzer")
public class Benutzer {

    @NonNull
    @PrimaryKey
    private String benutzerId;  // UUID als String
    private String vorname;
    private String nachname;
    private String email;

    // Alter -> laut ER-Modell "Datetime(Date)"
    // Hier vereinfacht als String gespeichert
    private String alterDatum;

    private boolean isDeleted; // Soft Delete Flag

    private String passwordHash; // Passwort-Hash

    private String imageProfil;


    public Benutzer() {
        // Leerer Konstruktor (für Room)
    }
    @Ignore
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

    // -- Getter & Setter --

    @NonNull
    public String getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(@NonNull String benutzerId) {
        this.benutzerId = benutzerId;
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

    // Öffentlicher Getter für passwordHash
    public String getPasswordHash() {
        return passwordHash;
    }

    // Öffentlicher Setter für passwordHash
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Getter und Setter für das Profilbild
    public String getImageProfil() {
        return imageProfil;
    }
    public void setImageProfil(String imageProfil) {
        this.imageProfil = imageProfil;
    }

    @NonNull
    @Override
    public String toString() {
        return vorname + " " + nachname; // Oder z. B. vorname + " " + nachname + " (" + email + ")"
    }
}
