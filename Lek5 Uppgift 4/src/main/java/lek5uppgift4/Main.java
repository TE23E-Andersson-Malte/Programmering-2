package lek5uppgift4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Colonist> colonists = new ArrayList<>();

        // (Namn, Ålder, Yrke, Syreförbrukning L/h, Credits)
        colonists.add(new Colonist("Astra Nova", 28, "Scientist", 15.5, 42000));
        colonists.add(new Colonist("Jebediah Kerman", 45, "Engineer", 22.0, 15000));
        colonists.add(new Colonist("Sarah Green", 34, "Farmer", 12.0, 52000));
        colonists.add(new Colonist("Xander Cage", 19, "Technician", 18.5, 500));
        colonists.add(new Colonist("Dr. Aris Thorne", 52, "Scientist", 14.0, 95000));
        colonists.add(new Colonist("Luna Lovegood", 24, "Botanist", 10.5, 12000));
        colonists.add(new Colonist("Marcus Vane", 41, "Engineer", 25.5, 63000));
        colonists.add(new Colonist("Ripley Scott", 39, "Scientist", 16.0, 31000));
        colonists.add(new Colonist("Ben Solo", 31, "Pilot", 19.0, 28000));
        colonists.add(new Colonist("Hera Syndulla", 29, "Pilot", 17.5, 45000));
        colonists.add(new Colonist("Gaius Baltar", 48, "Scientist", 13.5, 110000));
        colonists.add(new Colonist("Samwise Gamgee", 36, "Farmer", 11.5, 8000));

        // 1. Filtrera och samla (filter, collect): Hitta alla "Scientists" och spara
        // dem i en ny List.
        // Logik: En enkel filtrering där vi jämför yrket med en textsträng.
        List<Colonist> scientists = colonists.stream()
                .filter(f -> f.getProfession().equals("Scientist"))
                .collect(Collectors.toList());
        System.out.println("Scientists: " + scientists);

        // 2. Filtrera och samla (filter, collect): Hitta alla "Scientists" som är under
        // 40 år och spara dem i en ny List.
        // Logik: Här använder vi två filter efter varandra. Det går också att använda
        // ett filter med &&, men två filter är ofta mer lättläst för elever.
        List<Colonist> ungaScientists = colonists.stream()
                .filter(f -> f.getProfession().equals("Scientist"))
                .filter(f -> f.getAge() < 40)
                .collect(Collectors.toList());
        System.out.println("Scientists under 40 år: " + ungaScientists);

        // 3. Hitta alla veteraner (Filter) Skapa en ny lista som innehåller alla
        // kolonister som är 40 år eller äldre. Skriv ut hur många de är.
        // Logik: Vi filtrerar fram alla som är 40+, sparar i en lista och använder
        // .size() för att få fram antalet.
        List<Colonist> veterans = colonists.stream()
                .filter(f -> f.getAge() >= 40)
                .collect(Collectors.toList());
        System.out.println("Colonists över 40: " + veterans.size());

        // 4. Skapa en namnlista (Map) Skapa en lista som bara innehåller namnen
        // (String) på alla kolonister i registret.
        // Logik: Vi använder map för att "transformera" streamen från att innehålla
        // hela objekt till att bara innehålla namnen.
        List<String> colonister = colonists.stream()
                .map(f -> f.getName())
                .collect(Collectors.toList());
        System.out.println("Namnen på colonisterna: " + colonister);

        // 5. Räkna ingenjörer (Count) Räkna hur många personer i listan som har yrket
        // "Engineer". Skriv ut resultatet (en long).
        // Logik: count() är en inbyggd slutstation som räknar hur många element som
        // passerat genom filtret.
        long antalEngineer = colonists.stream()
                .filter(f -> f.getProfession().equals("Engineer"))
                .count();
        System.out.println("Antal engineers: " + antalEngineer);

        // 6. Beräkna total förmögenhet (Sum) Räkna ut den totala summan av alla credits
        // som alla kolonister äger tillsammans.
        // Logik: Vi måste först göra om streamen till en "siffer-stream" (mapToInt) för
        // att kunna använda den inbyggda sum()-metoden.
        int totalCredits = colonists.stream()
                .mapToInt(f -> f.getCredits())
                .sum();
        System.out.println("Total summa credits: " + totalCredits);

        // 7. Sök efter en specifik person (anyMatch) Kontrollera om det finns någon
        // person i registret som heter "Astra Nova". Programmet
        // Logik: Istället för en loop som letar, frågar vi streamen om "någon matchar"
        // detta villkor. Det ger oss ett snabbt svar: true eller false.
        boolean letaAstraNova = colonists.stream()
                .anyMatch(f -> f.getName().equals("Astra Nova"));
        System.out.println("Finns Astra Nova: " + letaAstraNova);

    }
}