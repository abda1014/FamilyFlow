package hs.karlsruhe.de.familyflow.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "BenutzerTerminn",
        primaryKeys = {"benutzerId", "terminId"},
        indices = {
                @Index(value = "benutzerId"),
                @Index(value = "terminId")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Benutzer.class,
                        parentColumns = "benutzerId",
                        childColumns = "benutzerId",
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
    private String benutzerId;

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
