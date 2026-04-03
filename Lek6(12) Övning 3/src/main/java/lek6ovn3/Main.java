package lek6ovn3;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Skapa register?
        ArrayList<Fordon> register = new ArrayList<>();
        //Skapa gson?
        Gson gson = new Gson();

        //1. LÄS FRÅN JSON
        try(Reader reader = new FileReader("fordon.json")){
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject objekt = element.getAsJsonObject();
                String typ = objekt.get("typ").getAsString();

                //Avgör vilken klass som ska skapas baserat på "typ" i JSON
                if (typ.equalsIgnoreCase("Bil")) {
                    register.add(gson.fromJson(objekt, Bil.class));
                } else if (typ.equalsIgnoreCase("Motorcykel")){
                    register.add(gson.fromJson(objekt, Motorcykel.class));
                } else if (typ.equalsIgnoreCase("Lastbil")){
                    register.add(gson.fromJson(objekt, Lastbil.class));
                }
            }

            System.out.println("Inläsning klar. Antal fordon: " + register.size());

        } catch (IOException e) {
            System.out.println("Kunde inte läsa filen: " + e.getMessage());
        }

        //2. MODIFIERA DATA (Sätt alla reg till AVSTÄLLD)
        for (Fordon f : register) {
            f.setReg("AVSTÄLLD");
        }

        //3. SKRIV TILLBAKA TILL JSON
        try (Writer writer = new FileWriter("fordon_uppdaterad.json")){
            gson.toJson(register, writer);
            System.out.println("Resultatet har sparats till 'fordon_uppdaterad.json'");
        } catch (Exception e) {
            System.out.println("Kunde inte spara filen: " + e.getMessage());
        }
    }
}