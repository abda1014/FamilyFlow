package hs.karlsruhe.de.familyflow.data;

import android.content.Context;

import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DBDaten {

    public static void initialisiereDaten(Context context) {
        new Thread(() -> {
            AppDatabase db = DatabaseManager.getDatabase(context);
            BenutzerDao benutzerDao = db.benutzerDao();

            // Passwort-Hash für Testbenutzer generieren
            String email = "HKA@hka.de";
            String plainPassword = "Testuser123";
            String hashedPassword = hashPassword(plainPassword);

            if (hashedPassword != null) {
                // Prüfung, ob der Benutzer schon existiert
                Benutzer existierenderBenutzer = benutzerDao.findBenutzerByEmailAndPassword(email, hashedPassword);
                if (existierenderBenutzer == null) {
                    // Testbenutzer einfügen
                    Benutzer neuerBenutzer = new Benutzer();
                    neuerBenutzer.setBenutzerId("1"); // Feste ID oder UUID
                    neuerBenutzer.setEmail(email);
                    neuerBenutzer.setPasswordHash(hashedPassword);
                    neuerBenutzer.setVorname("Test");
                    neuerBenutzer.setNachname("User");
                    neuerBenutzer.setAlterDatum("2000-01-01");
                    neuerBenutzer.setDeleted(false);

                    benutzerDao.insertBenutzer(neuerBenutzer);
                }
            }
        }).start();
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
