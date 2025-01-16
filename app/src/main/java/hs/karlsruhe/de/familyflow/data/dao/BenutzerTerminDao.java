package hs.karlsruhe.de.familyflow.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

//import hs.karlsruhe.de.familyflow.data.entity.Termin;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerTermin;

import java.util.List;

@Dao
public interface BenutzerTerminDao {

    // Einfügen eines neuen Termins
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTermin(BenutzerTermin termin);

    // Löschen eines BenutzerTermins
    @Query("DELETE FROM BenutzerTerminn WHERE benutzerId = :benutzerId AND terminId = :terminId")
    void deleteBenutzerTermin( String terminId,String benutzerId);

    @Transaction@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM BenutzerTerminn   WHERE terminId = :terminId ")
    BenutzerAufgaben getBenutzerWithTermin(String terminId);


}
