package com.example.familyflow.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.familyflow.entity.Benutzer;

import java.util.List;
@Dao
public interface BenutzerDao {

    @Insert
    void insertBenutzer(Benutzer... benutzer);

    @Update
    void updateBenutzer(Benutzer benutzer);

    // Anstelle von @Delete, machst du ein Soft Delete via @Query:
    @Query("UPDATE Benutzer SET isDeleted = 1 WHERE benutzerId = :id")
    void softDeleteBenutzer(String id);

    @Query("SELECT * FROM Benutzer WHERE benutzerId = :id LIMIT 1")
    Benutzer findBenutzerById(String id);

    // Hier solltest du z. B. NUR jene zurückgeben, die nicht gelöscht sind:
    @Query("SELECT * FROM Benutzer WHERE isDeleted = 0")
    List<Benutzer> getAllActiveBenutzer();
}
