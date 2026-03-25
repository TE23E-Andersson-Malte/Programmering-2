import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;


public class App {
    public static void main(String[] args) throws Exception {
        
        /**************************''
        
        List<String> deltagare_ej_rensad;
        try {
            Path fil_sökväg = Paths.get("deltagare.txt");
            deltagare_ej_rensad = Files.readAllLines(fil_sökväg);
        } catch (Exception e) {
            IO.println("Fel vid filinläsning: " + e.getMessage()); 
            e.printStackTrace();
            return;
        }

        deltagare_ej_rensad.remove("NAMN PÅ DELTAGARE:");

        HashSet<String> deltagare_rensad = new HashSet<>(deltagare_ej_rensad);
        List<String> rensad_lista = new ArrayList<>(deltagare_rensad);

        Collections.sort(rensad_lista);

        try {
            Path fil_sökväg = Paths.get("sort_rens_deltagare.txt");
            Files.write(fil_sökväg, rensad_lista);
            IO.println("Filen inläst: " + deltagare_ej_rensad);
        } catch (Exception e) {
            IO.println("Fel vid skriv till fil: " + e.getMessage());
            e.printStackTrace();
        }

        *****************************/

        String n_text = "NAMN PÅ DELTAGARE:\n";
        try {
            Path fil_sökväg = Paths.get("personer.txt");
            Files.writeString(fil_sökväg, n_text);
            IO.println("Filen har sparats!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] names = {"Bob", "Gretchen", "Mehmet", "Howard", "Ivan", "Yiu"};

        try (FileWriter fw = new FileWriter("namn.txt")){
            for (int i = 0; i < names.length; i++) {
                fw.write(names[i] + "\n");
            }
            fw.close();
        } catch (FileNotFoundException e) {
            IO.println("Hittar inte filen: " + e.getMessage());
        } catch(IOException e){
            IO.println("Allmänt filfel: " + e.getMessage());
        }
    }
}
