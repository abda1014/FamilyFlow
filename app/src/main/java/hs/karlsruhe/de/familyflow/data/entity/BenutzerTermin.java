package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "BenutzerTerminn",
        primaryKeys = {"benutzerId", "terminId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Benutzer.class,    // statt Benutzer.class
                        parentColumns = "benutzerId",  // statt nutzerId
                        childColumns = "benutzerId",   // statt nutzerId
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Termin.class,
                        parentColumns = "terminId",
                        childColumns = "terminId",
                        onDelete = CASCADE
                )
        }
)
public class BenutzerTermin {

    @NonNull
    private String benutzerId;  // statt nutzerId

    @NonNull
    private String terminId;

    public BenutzerTermin(@NonNull String benutzerId, @NonNull String terminId) {
        this.benutzerId = benutzerId;
        this.terminId = terminId;
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
    public String getTerminId() {
        return terminId;
    }

    public void setTerminId(@NonNull String terminId) {
        this.terminId = terminId;
    }
}
