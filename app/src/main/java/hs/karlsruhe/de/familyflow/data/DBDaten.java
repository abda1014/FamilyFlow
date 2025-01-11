package hs.karlsruhe.de.familyflow.data;

import android.annotation.SuppressLint;
import android.content.Context;

import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@SuppressLint("SimpleDateFormat")
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

            //hinzufügen von schon existierenden Terminen wenn noch nicht getan
            TerminDao terminDao = db.terminDao();
            if(terminDao.getNextTermin() == null){
                //Datumsformat anlegen
                SimpleDateFormat datum = new SimpleDateFormat("dd.MM.yyyy");
                //Uhrzeitformat anlegen
                SimpleDateFormat uhrzeit = new SimpleDateFormat("HH:mm");

                //jetzige Zeit als Standard um noch nicht vergangene Termine zu erstellen
                Calendar datumUndUhrzeit = Calendar.getInstance();

                //Termine anlegen
                datumUndUhrzeit.add(Calendar.DAY_OF_MONTH, 1);
                datumUndUhrzeit.add(Calendar.HOUR_OF_DAY, 1);
                Termin ersterTermin = new Termin(UUID.randomUUID().toString(), "Geburtstag Gustav",
                        datum.format(datumUndUhrzeit.getTime()), uhrzeit.format(datumUndUhrzeit.getTime()),
                        "KEINE", "sein 13ter Kindergeburtstag", false  );

                datumUndUhrzeit.add(Calendar.DAY_OF_MONTH, 2);
                datumUndUhrzeit.add(Calendar.HOUR_OF_DAY, 3);
                Termin zweiterTermin = new Termin(UUID.randomUUID().toString(), "Fußballspiel",
                        datum.format(Calendar.getInstance().getTime()), uhrzeit.format(Calendar.getInstance().getTime()),
                        "KEINE", "Julian und Jonas spielen", false  );

                datumUndUhrzeit.add(Calendar.DAY_OF_MONTH, 3);
                datumUndUhrzeit.add(Calendar.HOUR_OF_DAY, 6);
                Termin dritterTermin = new Termin(UUID.randomUUID().toString(), "Musikunterricht",
                        datum.format(Calendar.getInstance().getTime()), uhrzeit.format(Calendar.getInstance().getTime()),
                        "WOECHENTLICH", "Gustavs Trompetenunterricht", false  );

                //Termine speichern
                terminDao.insertTermin(ersterTermin);
                terminDao.insertTermin(zweiterTermin);
                terminDao.insertTermin(dritterTermin);
            }

            //hinzufügen von schon existierenden Aufgaben wenn noch nicht getan
            AufgabeDao aufgabeDao = db.aufgabeDao();
            if(aufgabeDao.getNextAufgabe() == null){
                //Datumsformat anlegen
                SimpleDateFormat datum = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                //jetzige Zeit als Standard um noch nicht vergangene Aufgaben zu erstellen
                Calendar datumUndUhrzeit = Calendar.getInstance();

                //Aufgaben anlegen
                datumUndUhrzeit.add(Calendar.HOUR_OF_DAY, 2);
                Aufgabe ersteAufgabe = new Aufgabe(UUID.randomUUID().toString(), "Einkaufen", "INPROGRESS",
                        datum.format(datumUndUhrzeit.getTime()), "Sachen für Gustavs Geburtstag kaufen", false);

                datumUndUhrzeit.add(Calendar.DAY_OF_MONTH, 2);
                datumUndUhrzeit.add(Calendar.HOUR_OF_DAY, 3);
                Aufgabe zweiteAufgabe = new Aufgabe(UUID.randomUUID().toString(), "Oma und Opa Abholen",
                        "ANSTEHEND", datum.format(datumUndUhrzeit.getTime()),
                        "Oma und Opa kommen vorbei und müssen am Bahnhof abgeholt werden", false);

                datumUndUhrzeit.add(Calendar.DAY_OF_MONTH, 1);
                datumUndUhrzeit.add(Calendar.HOUR_OF_DAY, 1);
                Aufgabe dritteAufgabe = new Aufgabe(UUID.randomUUID().toString(), "Oma und Opa Abholen",
                        "ANSTEHEND", datum.format(datumUndUhrzeit.getTime()),
                        "Oma und Opa kommen vorbei und müssen am Bahnhof abgeholt werden", false);

                //Aufgaben speichern
                aufgabeDao.insertAufgabe(ersteAufgabe);
                aufgabeDao.insertAufgabe(zweiteAufgabe);
                aufgabeDao.insertAufgabe(dritteAufgabe);
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
