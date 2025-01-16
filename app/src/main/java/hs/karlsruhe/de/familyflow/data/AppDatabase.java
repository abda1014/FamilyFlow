package hs.karlsruhe.de.familyflow.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
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
}
