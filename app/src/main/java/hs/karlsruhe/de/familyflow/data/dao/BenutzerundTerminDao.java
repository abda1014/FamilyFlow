package com.example.familyflow.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.familyflow.entity.Benutzer;

import java.util.List;

@Dao
public interface BenutzerDao {

    // 1) Benutzer anlegen (oder mehrere auf einmal)
    @Insert
    void insertBenutzer(Benutzer... benutzer);

    // 2) Benutzer aktualisieren
    @Update
    void updateBenutzer(Benutzer benutzer);

    // 3) Benutzer löschen
    @Query("UPDATE Benutzer SET isDeleted = 1 WHERE benutzerId = :id")
    void deleteBenutzer(Benutzer benutzer);

    // 4) Einen Benutzer per ID abfragen
    @Query("SELECT * FROM Benutzer WHERE benutzerId = :id LIMIT 1")
    Benutzer findBenutzerById(String id);

    // 5) Alle Benutzer abrufen
    @Query("SELECT * FROM Benutzer")
    List<Benutzer> getAllBenutzer();
}
