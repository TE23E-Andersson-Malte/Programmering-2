package programmeringprojekt.loans;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

public class LoanManager {
    private List<Loan> loans = new ArrayList<>();
    private Gson gson = new Gson();
    private final String FILE = "loans.json"; //final för att det inte ska kunna ändras senare

    public LoanManager(){
        //Läs in lån vid start
        loadLoans();
    }

    //Lägg till ett lån och spara
    public void addLoan(Loan loan){
        loans.add(loan);
        saveLoans();
    }

    //Metod för att ta bort ett lån
    public void removeLoan(String itemId){
        //Loopa igenom listan med lån, hämta ett element i taget och kolla dess id, jämför med id:t från inparametern, om de matchar ta bort elementet från listan
        for (int i = 0; i < loans.size(); i++){
            if (loans.get(i).getItemId().equals(itemId)) {
                loans.remove(i);
                break;
            }
        }
        saveLoans();
    }

    public List<Loan> getLoans(){
        return loans;
    }

    //Försök skapa en ny filewriter för json-filen, skriv om lånen i lokala listan till JSON-format
    private void saveLoans(){
        try (FileWriter fileWriter = new FileWriter(FILE)){
            gson.toJson(loans, fileWriter);
        } catch (Exception e) {
            IO.println("Kunde inte spara lån...");
        }
    }

    //Försök skapa en ny filereader för json-filen, läs  inte lånen och skapa loan-objekt för varje json-objekt, om inget läses in skapa en ny lista för lånen
    private void loadLoans(){
        try (FileReader fileReader = new FileReader(FILE)){
            loans = gson.fromJson(fileReader, new TypeToken<ArrayList<Loan>>(){}.getType());
            if (loans == null) {
                loans = new ArrayList<>();
            }
        } catch (Exception e) {
            loans = new ArrayList<>();
        }
    }

}
