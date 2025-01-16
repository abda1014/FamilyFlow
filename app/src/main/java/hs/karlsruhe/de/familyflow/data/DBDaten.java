package hs.karlsruhe.de.familyflow.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

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

            // Profilbilder-URLs der Benutzer
            String imageUrl_1 = "https://raw.githubusercontent.com/abda1014/FamilyFlow/main/app/src/main/res/mipmap-hdpi/happy_profil.png";
            String imageUrl_2 = "https://raw.githubusercontent.com/abda1014/FamilyFlow/Profilbild/app/src/main/res/mipmap-hdpi/angry_profil.png";
            String imageUrl_3 = "https://raw.githubusercontent.com/abda1014/FamilyFlow/main/app/src/main/res/mipmap-hdpi/glasses.png";
            //String imageUrl_4 = "https://raw.githubusercontent.com/abda1014/FamilyFlow/main/app/src/main/res/mipmap-hdpi/money.png";

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
                    neuerBenutzer.setImageProfil(imageUrl_1);

                    benutzerDao.insertBenutzer(neuerBenutzer);

                    Log.d("DBDaten", "Benutzer eingefügt: " + neuerBenutzer.getBenutzerId());
                }
            }

            // Standard Password, um sich leicht mit allen Testnutzern anmelden zu können
            String standardPassword = "123";
            String hashedStandardPassword = hashPassword(standardPassword);

            // weitere existierende Nutzer anlegen
            if (hashedStandardPassword != null) {
                String emailGustav = "gustav@hka.de";
                Benutzer existierenderBenutzer = benutzerDao.findBenutzerByEmailAndPassword(emailGustav, hashedStandardPassword);
                if (existierenderBenutzer == null) {
                    Benutzer gustav = new Benutzer(
                            UUID.randomUUID().toString(), "Gustav", "Klein",
                            emailGustav, "1990-05-15", false, hashedStandardPassword, imageUrl_2
                    );
                    benutzerDao.insertBenutzer(gustav);
                }

                String emailAnna = "anna@hka.de";
                existierenderBenutzer = benutzerDao.findBenutzerByEmailAndPassword(emailAnna, hashedStandardPassword);
                if (existierenderBenutzer == null) {
                    Benutzer anna = new Benutzer(
                            UUID.randomUUID().toString(), "Anna", "Klein",
                            emailAnna, "1995-09-10", false, hashedStandardPassword, imageUrl_3
                    );
                    benutzerDao.insertBenutzer(anna);
                }

                String emailLisa = "lisa@hka.de";
                existierenderBenutzer = benutzerDao.findBenutzerByEmailAndPassword(emailLisa, hashedStandardPassword);
                if (existierenderBenutzer == null) {
                    Benutzer lisa = new Benutzer(
                            UUID.randomUUID().toString(), "Lisa", "Klein",
                            emailLisa, "2001-03-25", false, hashedStandardPassword,null
                    );
                    benutzerDao.insertBenutzer(lisa);
                }
            }

            // Hinzufügen von schon existierenden Terminen, wenn noch nicht geschehen
            TerminDao terminDao = db.terminDao();
            if (terminDao.getNextTermin() == null) {
                // Datumsformat anlegen
                SimpleDateFormat datum = new SimpleDateFormat("dd.MM.yyyy");
                // Uhrzeitformat anlegen
                SimpleDateFormat uhrzeit = new SimpleDateFormat("HH:mm");

                // Jetzige Zeit als Standard um noch nicht vergangene Termine zu erstellen
                Calendar datumUndUhrzeit = Calendar.getInstance();

                // Termine anlegen
                datumUndUhrzeit.set(2025, 1, 17, 15, 30);
                Termin ersterTermin = new Termin(UUID.randomUUID().toString(), "Geburtstag Gustav",
                        datum.format(datumUndUhrzeit.getTime()), uhrzeit.format(datumUndUhrzeit.getTime()),
                        "KEINE", "Sein 13ter Kindergeburtstag", false);

                datumUndUhrzeit.set(2025, 1, 19, 11, 0);
                Termin zweiterTermin = new Termin(UUID.randomUUID().toString(), "Fußballspiel",
                        datum.format(datumUndUhrzeit.getTime()), uhrzeit.format(datumUndUhrzeit.getTime()),
                        "KEINE", "Julian und Jonas spielen", false);

                datumUndUhrzeit.set(2025, 1, 20, 16, 0);
                Termin dritterTermin = new Termin(UUID.randomUUID().toString(), "Musikunterricht",
                        datum.format(datumUndUhrzeit.getTime()), uhrzeit.format(datumUndUhrzeit.getTime()),
                        "WOECHENTLICH", "Gustavs Trompetenunterricht", false);

                // Termine speichern
                terminDao.insertTermin(ersterTermin);
                terminDao.insertTermin(zweiterTermin);
                terminDao.insertTermin(dritterTermin);
            }

            // Hinzufügen von schon existierenden Aufgaben, wenn noch nicht geschehen
            AufgabeDao aufgabeDao = db.aufgabeDao();
            if (aufgabeDao.getNextAufgabe() == null) {
                // Datumsformat anlegen
                SimpleDateFormat datum = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                // Jetzige Zeit als Standard um noch nicht vergangene Aufgaben zu erstellen
                Calendar datumUndUhrzeit = Calendar.getInstance();

                // Aufgaben anlegen
                datumUndUhrzeit.set(2025, 1, 17, 14, 0);
                Aufgabe ersteAufgabe = new Aufgabe(UUID.randomUUID().toString(), "Einkaufen", "INPROGRESS",
                        datum.format(datumUndUhrzeit.getTime()), "Sachen für Gustavs Geburtstag kaufen", false);

                datumUndUhrzeit.set(2025, 1, 19, 13, 0);
                Aufgabe zweiteAufgabe = new Aufgabe(UUID.randomUUID().toString(), "Oma und Opa Abholen",
                        "ANSTEHEND", datum.format(datumUndUhrzeit.getTime()),
                        "Oma und Opa kommen vorbei und müssen am Bahnhof abgeholt werden", false);

                datumUndUhrzeit.set(2025, 2, 1, 15, 0);
                Aufgabe dritteAufgabe = new Aufgabe(UUID.randomUUID().toString(), "Steuern machen",
                        "ANSTEHEND", datum.format(datumUndUhrzeit.getTime()),
                        "Manche Unterlagen sind noch im Keller", false);

                // Aufgaben speichern
                aufgabeDao.insertAufgabe(ersteAufgabe);
                aufgabeDao.insertAufgabe(zweiteAufgabe);
                aufgabeDao.insertAufgabe(dritteAufgabe);
            }
        }).start();
    }

    // Definiert eine Methode, die ein Passwort als Eingabe akzeptiert und dessen SHA-256-Hash berechnet
    public static String hashPassword(String password) {
        try {
            // Erstellt eine Instanz des MessageDigest-Objekts für den SHA-256-Algorithmus
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Konvertiert das Passwort in ein Byte-Array und berechnet den SHA-256-Hash
            byte[] hash = digest.digest(password.getBytes());
            // Erstellt einen StringBuilder, um das Hash-Ergebnis in einen Hexadezimal-String zu konvertieren
            StringBuilder hexString = new StringBuilder();

            // Iteriert durch jedes Byte im Hash-Ergebnis
            for (byte b : hash) {
                // Konvertiert das Byte in einen Hexadezimal-String
                String hex = Integer.toHexString(0xff & b);
                // Fügt eine führende Null hinzu, wenn die Hexadezimaldarstellung nur ein Zeichen lang ist
                if (hex.length() == 1) hexString.append('0');
                // Fügt das Hexadezimalzeichen zur Ergebniszeichenkette hinzu
                hexString.append(hex);
            }
            // Gibt den vollständigen Hexadezimal-String als Ergebnis zurück
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) { // Falls der Algorithmus "SHA-256" nicht gefunden wird
            // Gibt den Stack-Trace des Fehlers aus (Debugging-Zwecke)
            e.printStackTrace();
            // Gibt null
            return null;
        }
    }
}