package com.example;

import com.google.gson.Gson; // För att skapa en gson objekt att läsa in
import com.google.gson.GsonBuilder; // Skapa Gson med mer lättläst output
import com.google.gson.reflect.TypeToken; // Skapa TypeToken
import java.nio.file.Files; // Filhantering
import java.nio.file.Paths; // Filhantering
import java.lang.reflect.Type; //För att Spara Typen från TypeToken
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String filNamn = "konton.json";
        
        try {
            //Läs in hela JSON-filen till en sträng
            String jsonInnehall = Files.readString(Paths.get(filNamn));
            //Definera typen för listan (eftersom det är en samling objekt)
            Type kontoListaTyp = new TypeToken<ArrayList<Konto>>(){}.getType();
            //Omvandla JSON-text till en ArrayList med konto-objekt
            ArrayList<Konto> allaKonton = gson.fromJson(jsonInnehall, kontoListaTyp);

            IO.println("Påbörjar återbetalning...");

            for (Konto k : allaKonton) {
                k.sätt_in(500);
                IO.println("Återbetalat 500kr till: " + k.getÄgare() + " Nytt saldo: " + k.getSaldo());
            }

            //Omvandla listan tillbaka till en JSON-sträng
            String uppdateradJson = gson.toJson(allaKonton);
            //Skriv ner den nya datan till en ny fil
            Files.writeString(Paths.get("uppdaterade_konton.json"), uppdateradJson);

            IO.println("....................");
            IO.println("Ändringar sparat i 'uppdaterade_konton.json'");
        
        } catch (Exception e) {
            System.err.println("Ett fel uppstod: " + e.getMessage());
        }
        
    }
}