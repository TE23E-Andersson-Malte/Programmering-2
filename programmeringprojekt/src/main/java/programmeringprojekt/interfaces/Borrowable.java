package programmeringprojekt.interfaces;

/*
 * Malte Andersson
 * Gränssnittet Borrowable gör objekt lån- och returbara
 * Klasserna som implementerar detta gränssnitt är Book och Magazine
 * och de måste ha egna implementationer av metoderna nedan
*/

public interface Borrowable {
    //Försök låna objekt
    public boolean borrowItem();

    //Försök lämna tillbaka objekt
    public void returnItem();
}
