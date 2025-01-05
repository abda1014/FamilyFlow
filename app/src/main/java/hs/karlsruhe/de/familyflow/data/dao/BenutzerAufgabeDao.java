package hs.karlsruhe.de.familyflow.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;

import java.util.List;

@Dao
public interface BenutzerAufgabeDao {

    // Einfügen einer neuen Aufgabe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAufgabe(Aufgabe aufgabe);

    // Aktualisieren einer bestehenden Aufgabe
    @Query("UPDATE Aufgabe SET aufgabenbezeichnung = :bezeichnung, status = :status, faelligkeitsdatum = :datum, notiz = :notiz, isDeleted = :isDeleted WHERE aufgabeId = :aufgabeId")
    void updateAufgabe(String aufgabeId, String bezeichnung, String status, String datum, String notiz, boolean isDeleted);

    // Weiche Löschung einer Aufgabe
    @Query("UPDATE Aufgabe SET isDeleted = 1 WHERE aufgabeId = :aufgabeId")
    void deleteAufgabe(String aufgabeId);

    // Abrufen aller Aufgaben für einen bestimmten Benutzer
    @Transaction@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM benutzeraufgaben ba inner join Aufgabe a on ba.aufgabeId=a.aufgabeId WHERE benutzerId = :benutzerId AND a.isDeleted = 0")
    BenutzerAufgaben getBenutzerWithAufgaben(String benutzerId);

    // Abrufen aller Benutzer mit ihren Aufgaben
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM Benutzer b inner join BenutzerAufgaben ba inner join Aufgabe a on b.benutzerId=ba.benutzerId and ba.aufgabeId=a.aufgabeId where a.isDeleted = 0")
    List<BenutzerAufgaben> getAlleBenutzerMitAufgaben();
}
