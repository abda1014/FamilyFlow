package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
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

    public Benutzer() {
        // Leerer Konstruktor (für Room)
    }

    public Benutzer(@NonNull String benutzerId,
                    String vorname,
                    String nachname,
                    String email,
                    String alterDatum,
                    boolean isDeleted,
                    String passwordHash) {
        this.benutzerId = benutzerId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.alterDatum = alterDatum;
        this.isDeleted = isDeleted;
        this.passwordHash = passwordHash;
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

    // **Kein öffentlicher Getter für passwordHash**
    private String getPasswordHash() {
        return passwordHash;
    }

    // Öffentlicher Setter für passwordHash
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
