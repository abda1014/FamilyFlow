package com.example.familyflow.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.familyflow.entity.Aufgabe;

import java.util.List;

@Dao
public interface AufgabeDao {

    @Insert
    void insertAufgabe(Aufgabe... aufgaben);

    @Update
    void updateAufgabe(Aufgabe aufgabe);

    // Soft-Delete -> Nur Markieren statt Löschen
    @Query("UPDATE Aufgabe SET isDeleted = 1 WHERE aufgabeId = :id")
    void softDeleteAufgabe(String id);

    // Alle *nicht gelöschten* Aufgaben
    @Query("SELECT * FROM Aufgabe WHERE isDeleted = 0")
    List<Aufgabe> getAllActiveAufgaben();

    // (Optional) Methode, um auch gelöschte zu sehen
    @Query("SELECT * FROM Aufgabe")
    List<Aufgabe> getAllAufgabenIncludingDeleted();

    // Eine Aufgabe per ID (egal, ob gelöscht oder nicht)
    @Query("SELECT * FROM Aufgabe WHERE aufgabeId = :id LIMIT 1")
    Aufgabe findAufgabeById(String id);
}

