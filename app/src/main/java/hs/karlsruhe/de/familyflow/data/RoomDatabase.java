@Database(
        entities = {
                Benutzer.class,
                Aufgabe.class,
                Termin.class,
                // Join-Tabellen
                Benutzer_Aufgaben.class,
                Benutzer_Termine.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BenutzerDao benutzerDao();
    public abstract AufgabeDao aufgabeDao();
    public abstract TerminDao terminDao();
    // Join-DAOs
    public abstract BenutzerAufgabenDao benutzerAufgabenDao();
    public abstract BenutzerTermineDao benutzerTermineDao();
}
