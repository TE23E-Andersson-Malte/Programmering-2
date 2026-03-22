import java.util.List;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.nio.file.Path;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        
        /////////////////////STÄDA EPOSTER/////////////////////
            //För att kunna läsa igenom textfiler
            String eposter = "";
            try {
            eposter = Files.readString(Path.of("epost.txt"));
            } catch (Exception e) {
            IO.println("Fel på fil, meddelande: " + e.getMessage());
            }
        
            //Gör om strängen till array (nytt element för varje rad)
            String[] eposter_array = eposter.split(System.lineSeparator());
            //Gör om array til list
            List<String> epost_lista = Arrays.asList(eposter_array);
            IO.println("Antal innan rensning: " + epost_lista.size());
            //Skapa set med listan
            Set<String> eposter_set = new HashSet<String>(epost_lista);
            IO.println("Antal efter rensning: " + eposter_set.size());

        /////////////////////SÄKERHETSKONTROLL/////////////////////
        //För att kunna läsa igenom textfiler
            String bannlysta = "";
            try {
            bannlysta = Files.readString(Path.of("bannlysta.txt"));
            } catch (Exception e) {
            IO.println("Fel på fil, meddelande: " + e.getMessage());
            }

            String[] bannlysta_array = bannlysta.split(System.lineSeparator());
            //Gör om array til list
            List<String> bannlysta_lista = Arrays.asList(bannlysta_array);
            //Skapa set med listan
            Set<String> bannlysta_set = new HashSet<String>(bannlysta_lista);

            String kollaEpost = IO.readln("Skriv in epost för att kolla bannlyshet: ");
            if (bannlysta_set.contains(kollaEpost)) {
                IO.println(kollaEpost + " är bannlyst");
            } else {
                IO.println(kollaEpost + " är inte bannlyst");
            }

        /////////////////////VIP OCH VOLONTÄRER/////////////////////
            //För att kunna läsa igenom textfiler
            String vips = "";
            try {
            vips = Files.readString(Path.of("vips.txt"));
            } catch (Exception e) {
            IO.println("Fel på fil, meddelande: " + e.getMessage());
            }

            String[] vips_array = vips.split(System.lineSeparator());
            //Gör om array til list
            List<String> vips_Lista = Arrays.asList(vips_array);
            //Skapa set med listan
            Set<String> vips_set = new HashSet<String>(vips_Lista);

            ////////////////////////////////////////////////

            //För att kunna läsa igenom textfiler
            String volontärer = "";
            try {
            volontärer = Files.readString(Path.of("volontärer.txt"));
            } catch (Exception e) {
            IO.println("Fel på fil, meddelande: " + e.getMessage());
            }

            String[] volontärer_array = volontärer.split(System.lineSeparator());
            //Gör om array til list
            List<String> volontärer_Lista = Arrays.asList(volontärer_array);
            //Skapa set med listan
            Set<String> volontärer_set = new HashSet<String>(volontärer_Lista);

            ////////////////////////////////////////////////
            Set<String> superVolontärer = new HashSet<>(volontärer_set);
            superVolontärer.retainAll(vips_set);
            IO.println("\nPersoner med specialbadge: " + superVolontärer);

            Set<String> VipsOchVolontärer = new HashSet<>(volontärer_set);
            VipsOchVolontärer.addAll(vips_set);
            IO.println("\nVips och volontärer: " + VipsOchVolontärer);
    }
}
