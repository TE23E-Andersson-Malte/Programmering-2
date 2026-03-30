package lek5ovning4;

import com.google.gson.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import trafikverket.Bil;
import trafikverket.LastBil;
import trafikverket.Fordon;

public class Main {
    public static void main(String[] args) {
        List<Fordon> vagnpark = new ArrayList<>();
        String råData;
        try {
            råData = Files.readString(Path.of("fordon.json"));
        } catch (Exception e) {
            IO.println("Filen är fel/'finns ej', info: " + e.getMessage());
            return;
        }

        JsonArray jsonLista = JsonParser.parseString(råData).getAsJsonArray();

        for (JsonElement element : jsonLista) {
            JsonObject objekt = element.getAsJsonObject();
            String typ = objekt.get("typ").getAsString();
            String märke = objekt.get("märke").getAsString();
            String modell = objekt.get("modell").getAsString();
            String reg = objekt.get("regNr").getAsString();

            if(typ.equals("Bil")) {
                int platser = objekt.get("sittPlatser").getAsInt();
                vagnpark.add(new Bil(märke, modell, reg, platser));
            } else if (typ.equals("Lastbil")) {
                int max = objekt.get("maxLast").getAsInt();
                vagnpark.add(new LastBil(märke, modell, reg, max));
            }
        }

        IO.println("\n-----ANALYSRAPPORT-----");
        //1. FILTRERING: hur många fordon är bilar?
        long antalBilar = vagnpark.stream()
                .filter(f -> f instanceof Bil)
                .count();
        IO.println("Antal bilar i registret: " + antalBilar);

        //2. SORTERING: Visa alla fordon sorterade på reg-nummer
        IO.println("\nFordon sorterade på reg-nummer:");
        vagnpark.stream()
                .sorted() //Använder compareTo-metoden vi skrev i fordon
                .forEach(f -> IO.println(" > " + f.toString()));

        //3. MAPPING: Hämta alla märken som finns i registret (unika)
        List<String> unikaMärken = vagnpark.stream()
                .map(f -> f.getMärke()) //Hämtar bara märke
                .distinct() //Tar bort dubbletter
                .collect(Collectors.toList());
        IO.println("\nVi har fordon från följande märken: " + unikaMärken);

        //4. MATEMATIK: vad är den totala lastkapaciteten i alla lastbilar?
        int totalLast = vagnpark.stream()
                .filter(f -> f instanceof LastBil)
                .mapToInt(f -> ((LastBil) f).getLastadVikt())
                .sum();
        IO.println("\nTotal lastkapacitet i alla lastbilar: " + totalLast);
    }
    }