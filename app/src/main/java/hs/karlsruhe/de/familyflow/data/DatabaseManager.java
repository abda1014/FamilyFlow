package hs.karlsruhe.de.familyflow.data;

import android.content.Context;

import androidx.room.Room;

import hs.karlsruhe.de.familyflow.data.AppDatabase;

public class DatabaseManager {
    // Singleton-Instanz der AppDatabase
    private static AppDatabase INSTANCE;

    // Diese Methode gibt die Instanz der Datenbank zurück.
    // Wenn die Instanz noch nicht existiert, wird sie hier erstellt.
    public static AppDatabase getDatabase(Context context) {
        // Überprüfen, ob die Instanz bereits existiert
        if (INSTANCE == null) {
            // Synchronisation, um sicherzustellen, dass nur ein Thread die Instanz erstellt
            synchronized (DatabaseManager.class) {
                // Doppelte Überprüfung, ob die Instanz immer noch null ist
                if (INSTANCE == null) {
                    // Erstellen der Datenbank-Instanz mit Room
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(), // Verwendet die ApplicationContext, um Speicherlecks zu vermeiden
                                    AppDatabase.class, // Die Datenbankklasse
                                    "familyflow_database" // Der Name der Datenbankdatei
                            )
                            // .fallbackToDestructiveMigration() // Falls die Migration fehlschlägt, wird die Datenbank zerstört und neu erstellt (nur im Entwicklungsstadium)
                            .build(); // Baue die Datenbank
                }
            }
        }
        // Gibt die Datenbank-Instanz zurück
        return INSTANCE;
    }
}
