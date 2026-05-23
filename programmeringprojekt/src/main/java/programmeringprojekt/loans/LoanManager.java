package programmeringprojekt.loans;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * Hanterar alla lån i bibliotekssystemet.
 * Klassen ansvarar för att skapa, lagra, läsa in och ta bort lån,
 * samt att spara och läsa data från en JSON-fil med hjälp av Gson.
 *
 * Ansvar:
 * - Lägga till nya lån
 * - Ta bort lån baserat på itemId
 * - Ladda in lån från fil vid start
 * - Spara alla aktuella lån till fil
 *
 * Användning:
 * LoanManager används av LibrarySystem för att hantera alla låneoperationer.
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class LoanManager {
    private List<Loan> loans = new ArrayList<>();
    private Gson gson = new Gson();
    private final String FILE = "loans.json"; // final för att det inte ska kunna ändras senare

    /**
     * Skapar en ny instans av LoanManager.
     * Vid start läses alla befintliga lån in från lagringsfilen
     * för att systemet ska ha tillgång till aktuella låneposter.
     */
    public LoanManager() {
        // Läs in lån vid start
        loadLoans();
    }

    /**
     * Lägger till ett nytt lån i listan och sparar alla lån till JSON-filen.
     * 
     * @param loan det lån som ska läggas till
     */
    public void addLoan(Loan loan) {
        loans.add(loan);
        saveLoans();
    }

    /**
     * Tar bort ett lån baserat på objektets itemId.
     * Om ett lån med matchande itemId hittas tas det bort och listan sparas.
     * 
     * @param itemId ID för det lånade objektet som ska tas bort
     */
    public void removeLoan(String itemId) {
        // Loopa igenom listan med lån, hämta ett element i taget och kolla dess id,
        // jämför med id:t från inparametern, om de matchar ta bort elementet från
        // listan
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getItemId().equals(itemId)) {
                loans.remove(i);
                break;
            }
        }
        saveLoans();
    }

    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Sparar alla lån till JSON-filen.
     * Om filen inte kan skrivas ut skrivs ett felmeddelande ut.
     */
    private void saveLoans() {
        try (FileWriter fileWriter = new FileWriter(FILE)) {
            gson.toJson(loans, fileWriter);
        } catch (Exception e) {
            IO.println("Kunde inte spara lån...");
        }
    }

    /**
     * Läser in lån från JSON-filen och skapar Loan-objekt.
     * Om filen saknas eller inte kan läsas skapas en tom lista.
     */
    private void loadLoans() {
        try (FileReader fileReader = new FileReader(FILE)) {
            loans = gson.fromJson(fileReader, new TypeToken<ArrayList<Loan>>() {
            }.getType());
            if (loans == null) {
                loans = new ArrayList<>();
            }
        } catch (Exception e) {
            loans = new ArrayList<>();
        }
    }

}
