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

// Die Annotation @Database definiert die Room-Datenbank und die zugehörigen Entitäten.
@Database(
        entities = {
                Benutzer.class,       // Entität für die Benutzer
                Aufgabe.class,        // Entität für die Aufgaben
                Termin.class,         // Entität für die Termine
                BenutzerAufgaben.class,  // Entität für die Zuordnung von Benutzern und Aufgaben
                BenutzerTermin.class   // Entität für die Zuordnung von Benutzern und Terminen
        },
        version = 1,  // Die Versionsnummer der Datenbank, wird bei Änderungen der Datenbankstruktur erhöht
        exportSchema = false  // Verhindert, dass das Schema der Datenbank exportiert wird
)
public abstract class AppDatabase extends RoomDatabase {

     // Abstrakte Methoden, um Zugriff auf die DAOs (Data Access Objects) zu ermöglichen
     // Jedes DAO stellt Methoden bereit, um auf die entsprechenden Entitäten zuzugreifen
     public abstract BenutzerDao benutzerDao();         // DAO für Benutzer
     public abstract AufgabeDao aufgabeDao();           // DAO für Aufgaben
     public abstract TerminDao terminDao();             // DAO für Termine
     public abstract BenutzerAufgabeDao benutzerAufgabenDao(); // DAO für Benutzer-Aufgaben-Beziehungen
     public abstract BenutzerTerminDao benutzerTermineDao();   // DAO für Benutzer-Termin-Beziehungen

     /**
      // Singleton-Instanz der Datenbank
      private static AppDatabase instance;

      // Diese Methode stellt sicher, dass nur eine Instanz der Datenbank existiert.
      // Sie verwendet Room's databaseBuilder, um die Datenbank zu erstellen oder auf die vorhandene Instanz zuzugreifen.
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
