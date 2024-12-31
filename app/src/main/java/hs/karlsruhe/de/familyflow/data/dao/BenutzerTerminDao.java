package hs.karlsruhe.de.familyflow.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.Termin;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerTermin;

import java.util.List;

@Dao
public interface BenutzerTerminDao {

    // Einfügen eines neuen Termins
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTermin(Termin termin);

    // Aktualisieren eines bestehenden Termins
    @Query("UPDATE Termin SET terminname = :terminname, datum = :datum, uhrzeit = :uhrzeit, wiederholung = :wiederholung, beschreibung = :beschreibung, isDeleted = :isDeleted WHERE terminId = :terminId")
    void updateTermin(String terminId, String terminname, String datum, String uhrzeit, String wiederholung, String beschreibung, boolean isDeleted);

    // Weiche Löschung eines Termins
    @Query("UPDATE Termin SET isDeleted = 1 WHERE terminId = :terminId")
    void deleteTermin(String terminId);

    // Abrufen aller Termine für einen bestimmten Benutzer
    @Transaction
    @Query("SELECT * FROM Benutzer WHERE benutzerId = :nutzerId AND isDeleted = 0")
    BenutzerTermin getBenutzerWithTermine(String nutzerId);

    // Alternativ: Direkte Abfrage aller Termine für einen Benutzer
    @Query("SELECT * FROM Termin WHERE benutzerId = :nutzerId AND isDeleted = 0")
    List<Termin> getTermineForBenutzer(String nutzerId);

    // Abrufen aller Benutzer mit ihren Terminen
    @Transaction
    @Query("SELECT * FROM Benutzer WHERE isDeleted = 0")
    List<BenutzerTermin> getAlleBenutzerMitTerminen();
}
