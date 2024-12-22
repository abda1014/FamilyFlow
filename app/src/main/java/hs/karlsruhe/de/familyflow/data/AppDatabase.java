package com.example.familyflow.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.familyflow.data.entity.Nutzer;
import com.example.familyflow.data.entity.Aufgabe;
import com.example.familyflow.data.entity.Termin;
import com.example.familyflow.data.entity.Nutzer_Aufgaben;
import com.example.familyflow.data.entity.Nutzer_Termine;

@Database(
        entities = {
                Nutzer.class,
                Aufgabe.class,
                Termin.class,
                Nutzer_Aufgaben.class,
                Nutzer_Termine.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    // Hier kannst du DAO-Interfaces angeben:
    // public abstract NutzerDao nutzerDao();
    // public abstract AufgabeDao aufgabeDao();
    // public abstract TerminDao terminDao();
    // public abstract NutzerAufgabenDao nutzerAufgabenDao();
    // public abstract NutzerTermineDao nutzerTermineDao();
}
