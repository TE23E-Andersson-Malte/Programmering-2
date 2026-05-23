package programmeringprojekt.items;

import programmeringprojekt.loans.Borrowable;

/**
 * Klassen Magazine är en barnklass till den abstrakta klassen LibraryItem
 * och representerar en tidning i biblioteket med unika egenskaper
 * som utgåvonummer, kategori och publiserat år
 * Magazine används av LibrarySystem som hanterar in- och utlånning samt lagring av tidnings-objekt
 * Klassen implementerar gränssnittet Borrowable
 * vilket gör att den måste definera funktioner för lån och retur av en tidning
 * 
 * 
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
*/

public class Magazine extends LibraryItem implements Borrowable{
    private int issueNumber;
    private String category;
    private int publishedYear;

    /***KONSTRUKTOR***/
    public Magazine(String id, String title, boolean isAvailable, int issueNumber, String category, int publishedYear){
        super(id, title, isAvailable);
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Utgåvonummer måste vara ett positivt tal");
        }
        this.issueNumber = issueNumber;
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Kategori får inte vara tomt");
        }
        this.category = category;
        if (publishedYear <= 0) {
            throw new IllegalArgumentException("Publiceringsår får inte vara tomt");
        }
        this.publishedYear = publishedYear;
    }

    /***GETTERS***/
    public int getIssueNumber() {
        return issueNumber;
    }

    public String getCategory() {
        return category;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    //GET INFO
    @Override
    public String getInfo(){
        return "MAGAZINE | ID: " + id + " | Title: " + title +
         " | IsAvailable: " + isAvailable + " | IssueNumber: " + 
         issueNumber + " | Category: " + category + " | PublishedYear: " + publishedYear;
    }

/**
 * Försöker låna ut tidningen.
 * Om tidningen är tillgänglig markeras den som utlånad och true returneras.
 * Om den redan är utlånad returneras false.
 *
 * @return true om utlåningen lyckades, annars false
 */    @Override
    public boolean borrowItem(){
        if (getIsAvailable()) {
            setIsAvailable(false);
            IO.println("\nTidningen lånades ut");
            return true;
        } else {
            return false;
        }
    }

    /**
 * Markerar tidningen som återlämnad och gör den tillgänglig igen.
 * Skriver även ut ett meddelande om att tidningen lämnades tillbaka.
 */
    @Override
    public void returnItem(){
        setIsAvailable(true);
        IO.println("\nTidningen lämnades tillbaka.");
    }
}
