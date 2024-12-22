package com.example.familyflow.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.familyflow.entity.Termin;

import java.util.List;

@Dao
public interface TerminDao {

    // 1) Insert – einen oder mehrere Termine anlegen
    @Insert
    void insertTermin(Termin... termine);

    // 2) Update – Termin-Daten ändern (z. B. Datum, Uhrzeit etc.)
    @Update
    void updateTermin(Termin termin);

    // 3) Soft-Delete – statt physischem Löschen nur markieren
    @Query("UPDATE Termin SET isDeleted = 1 WHERE terminId = :id")
    void softDeleteTermin(String id);

    // 4) Eine Termin per ID holen (unabhängig vom isDeleted-Status)
    @Query("SELECT * FROM Termin WHERE terminId = :id LIMIT 1")
    Termin findTerminById(String id);

    // 5a) Alle Termine, die NICHT gelöscht sind
    @Query("SELECT * FROM Termin WHERE isDeleted = 0")
    List<Termin> getAllActiveTermine();

    // 5b) (optional) Alle Termine inkl. gelöschter
    @Query("SELECT * FROM Termin")
    List<Termin> getAllTermineIncludingDeleted();
}
