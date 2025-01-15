package hs.karlsruhe.de.familyflow.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Termin;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerTermin;
import hs.karlsruhe.de.familyflow.data.dao.*;

@Database(
        entities = {
                Benutzer.class,
                Aufgabe.class,
                Termin.class,
                BenutzerAufgaben.class,
                BenutzerTermin.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

     public abstract BenutzerDao benutzerDao();
     public abstract AufgabeDao aufgabeDao();
     public abstract TerminDao terminDao();
     public abstract BenutzerAufgabeDao benutzerAufgabenDao();
     public abstract BenutzerTerminDao benutzerTermineDao();

     /**
     // Singleton-Instanz
     private static AppDatabase instance;

     // Diese Methode stellt sicher, dass nur eine Instanz der Datenbank existiert
     public static synchronized AppDatabase getDatabase(Context context) {
          if (instance == null) {
               instance = Room.databaseBuilder(context.getApplicationContext(),
                               AppDatabase.class, "familyflow_database")
                       .build();
          }
          return instance;
     }
      **/
}
