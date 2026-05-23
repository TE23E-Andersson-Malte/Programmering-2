package programmeringprojekt.loans;

/**
 * Gränssnittet Borrowable gör objekt lån- och returbara
 * Klasserna som implementerar detta gränssnitt är Book och Magazine
 * och de måste ha egna implementationer av metoderna nedan
 * 
 * @author Malte Andersson
* @version 1.0
* @since 2026
*/

public interface Borrowable {
    //Försök låna objekt
    public boolean borrowItem();

    //Försök lämna tillbaka objekt
    public void returnItem();
}
