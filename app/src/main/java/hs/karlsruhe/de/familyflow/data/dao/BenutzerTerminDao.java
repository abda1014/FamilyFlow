package hs.karlsruhe.de.familyflow.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import hs.karlsruhe.de.familyflow.data.entity.Termin;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerTermin;

import java.util.List;

@Dao
public interface BenutzerTerminDao {

    // Einfügen eines neuen Termins
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTermin(BenutzerTermin termin);

    // Aktualisieren eines bestehenden Termins
    @Query("UPDATE Termin SET terminname = :terminname, datum = :datum, uhrzeit = :uhrzeit, wiederholung = :wiederholung, beschreibung = :beschreibung, isDeleted = :isDeleted WHERE terminId = :terminId")
    void updateTermin(String terminId, String terminname, String datum, String uhrzeit, String wiederholung, String beschreibung, boolean isDeleted);

    // Weiche Löschung eines Termins
    @Query("UPDATE Termin SET isDeleted = 1 WHERE terminId = :terminId")
    void deleteTermin(String terminId);

    // Abrufen aller Termine für einen bestimmten Benutzer
    @Transaction
    @Query(" SELECT T.* FROM Termin T INNER JOIN benutzerterminn BT ON T.terminId = BT.terminId WHERE BT.benutzerId = :benutzerId AND T.isDeleted = 0")
    List<Termin> getTermineForBenutzer(String benutzerId);

    // Abrufen aller Benutzer mit ihren Terminen
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Benutzer b inner join benutzerterminn bt inner join termin t on b.benutzerId=bt.benutzerId and bt.terminId=t.terminId WHERE t.isDeleted = 0")
    List<BenutzerTermin> getAlleBenutzerMitTerminen();

    @Query("SELECT benutzerId FROM Termin t inner join benutzerterminn bt on bt.terminId= t.terminId where t.terminId = :terminId")
    List<String> getBeteiligteNutzerIds(String terminId);
}
