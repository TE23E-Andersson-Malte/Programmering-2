package lek6uppgift2;

import com.google.gson.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Lista för anställda
        ArrayList<Personal> anställda = new ArrayList<>();
        String rådata;

        // Läs in
        try {
            rådata = Files.readString(Path.of("anställda.json"));
        } catch (Exception e) {
            IO.println("Fel på inläsning: " + e.getMessage());
            return;
        }

        JsonArray jsonLista = JsonParser.parseString(rådata).getAsJsonArray();

        for (JsonElement element : jsonLista) {
            JsonObject object = element.getAsJsonObject();
            String typ = object.get("typ").getAsString();
            String namn = object.get("namn").getAsString();
            long personnummer = object.get("personnummer").getAsLong();
            int lön = object.get("lon").getAsInt();
            String avdelning = object.get("avdelning").getAsString();

            if (typ.equals("Programmerare")) {
                String programSprak = object.get("programSprak").getAsString();
                anställda.add(new Programmerare(typ, namn, personnummer, lön, avdelning, programSprak));
            } else if (typ.equals("Projektledare")){
                int antalProjekt = object.get("antalProjekt").getAsInt();
                anställda.add(new Projektledare(typ, namn, personnummer, lön, avdelning, antalProjekt));
            }
        }

        //*******UPGIFTER*******/
            //1.
                anställda.stream()
                    .map(p -> p.getNamn())
                    .sorted()
                    .forEach(p -> System.out.println(p));
            //2. 
                long antalProgrammerare = anställda.stream()
                    .filter(p -> p instanceof Programmerare)
                    .count();
                long antalProjektledare = anställda.stream()
                    .filter(p -> p instanceof Projektledare)
                    .count();

                IO.println("\nAntal programmerare: " + antalProgrammerare + " | Antal projektledare: " + antalProjektledare);

            //3.
                //Använd stream för att sortera fram top 5 personal med högst lön
                List<Personal> högstLön = anställda.stream()
                    .sorted(Comparator.comparing((Personal p) -> p.getLön()).reversed())
                    .limit(5)
                    .sorted(Comparator.comparing((Personal p) -> p.getNamn()))
                    .toList();

                //Skriv ut
                IO.println("\nTop 5 högst lön: ");
                for (Personal person : högstLön) {
                    IO.println(person);
                }

                //Beräkna totallön
                int totalLön = 0;
                for (Personal personal : högstLön) {
                    totalLön += personal.getLön();
                }
                IO.println("Totala summan av deras löner: " + totalLön);

            //4.
                //Beräkna medelvärde och hanntera eventuella tomma listor
                double medelLön = anställda.stream()
                    .mapToDouble((Personal p) -> p.getLön())
                    .average()
                    .orElse(0);

                double medelLönProgrammerare = anställda.stream()
                    .filter(p -> p instanceof Programmerare)
                    .mapToDouble((Personal p) -> p.getLön())
                    .average()
                    .orElse(0);

                double medelLönProjektledare = anställda.stream()
                    .filter(p -> p instanceof Projektledare)
                    .mapToDouble((Personal p) -> p.getLön())
                    .average()
                    .orElse(0);

                //Skriv ut
                IO.println("\nHela företagets medellön: " + medelLön); 
                IO.println("Programmerarnas medellön: " + medelLönProgrammerare + "| Projektledarnas medellön: " + medelLönProjektledare);
            
            //5.
                //Filtrera ut programmerare och gruppera språk tillsammans med antal användare
                Map<String, Long> antalSpråk = anställda.stream()
                    .filter(p -> p instanceof Programmerare)
                    .map(p -> (Programmerare) p)
                    .collect(Collectors.groupingBy((Programmerare p) -> p.getProgramSprak(), Collectors.counting()));

                //Hitta mest använda språket
                String mestAnväntSpråk = antalSpråk.entrySet().stream()
                    .max((p1, p2) -> p1.getValue().compareTo(p2.getValue()))
                    .map(p -> p.getKey())
                    .orElse("Inget språk");
                
                //Skriv ut
                IO.println("Mest använt språk: " + mestAnväntSpråk);
    }
}