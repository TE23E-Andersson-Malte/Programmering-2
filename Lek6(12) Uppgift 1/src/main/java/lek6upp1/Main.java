package lek6upp1;

import com.google.gson.Gson; // För att skapa en gson objekt att läsa in
import com.google.gson.GsonBuilder; // Skapa Gson med mer lättläst output
import com.google.gson.reflect.TypeToken; // Skapa TypeToken
import java.nio.file.Files; // Filhantering
import java.nio.file.Path;
import java.nio.file.Paths; // Filhantering
import java.lang.reflect.Type; //För att Spara Typen från TypeToken
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        
        /************DEL A*************/
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type typ = new TypeToken<ArrayList<Person>>(){}.getType();

        ArrayList<Person> medlemmar = new ArrayList<>();
        ArrayList<Person> fHjälpen = new ArrayList<>();

            //Läs in
        try {
            String jsonMedlemmar = Files.readString(Path.of("medlemmar.json"));
            medlemmar = gson.fromJson(jsonMedlemmar, typ);
        } catch (Exception e) {
            System.out.println("Fel på inläsning: " + e.getMessage());
        }
            //Läs in
        try {
            String jsonFH = Files.readString(Path.of("f_hjälpen.json"));
            fHjälpen = gson.fromJson(jsonFH, typ);
        } catch (Exception e) {
            System.out.println("Fel på inläsning: " + e.getMessage());
        }

        Set<Person> medlemmarSet = new HashSet<>(medlemmar);
        Set<Person> fHjälpenSet = new HashSet<>(fHjälpen);

        Set<Person> säkerhetsansvarigaSet = new HashSet<>(medlemmarSet);
        säkerhetsansvarigaSet.retainAll(fHjälpenSet);
        IO.println("Säkerhetsansvariga: " + säkerhetsansvarigaSet + "| Antal: " + säkerhetsansvarigaSet.size());

        try {
            String sparaFil = gson.toJson(medlemmarSet);
        Files.writeString(Path.of("säkerhetsansvariga.json"), sparaFil);
        } catch (Exception e) {
            System.out.println("Fel på sparande av fil: " + e.getMessage());
        }

        /************DEL B*************/
        ArrayList<Person> besökare = new ArrayList<>();
        ArrayList<Person> avstängda = new ArrayList<>();

            //Läs in
        try {
            String jsonBesökare = Files.readString(Path.of("besökande.json"));
            besökare = gson.fromJson(jsonBesökare, typ);
        } catch (Exception e) {
            System.out.println("Fel på inläsning: " + e.getMessage());
        }
            //Läs in
        try {
            String jsonAvstängda = Files.readString(Path.of("avstängda.json"));
            avstängda = gson.fromJson(jsonAvstängda, typ);
        } catch (Exception e) {
            System.out.println("Fel på inläsning: " + e.getMessage());
        }

        Set<Person> besökareSet = new HashSet<>(besökare);
        Set<Person> avstängdaSet = new HashSet<>(avstängda);

        Set<Person> deltagareSet = new HashSet<>(medlemmarSet);
        deltagareSet.addAll(besökareSet);
        deltagareSet.removeAll(avstängdaSet);

        IO.println("Deltagare: " + deltagareSet + "| Antal: " + deltagareSet.size());

        try {
            String sparaFil = gson.toJson(deltagareSet);
        Files.writeString(Path.of("deltagare.json"), sparaFil);
        } catch (Exception e) {
            System.out.println("Fel på sparande av fil: " + e.getMessage());
        }
    }
}