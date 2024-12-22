public class DatabaseManager {
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "familyflow_database"
                            )
                            // .fallbackToDestructiveMigration() // nur im Entwicklungsstadium
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
