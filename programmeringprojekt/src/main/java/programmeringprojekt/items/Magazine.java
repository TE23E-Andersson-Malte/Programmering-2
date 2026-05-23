package programmeringprojekt.items;

import programmeringprojekt.interfaces.Borrowable;

/*
 * Malte Andersson
 * Klassen Magazine är en barnklass till den abstrakta klassen LibraryItem
 * och representerar en tidning i biblioteket med unika egenskaper
 * som utgåvonummer, kategori och publiserat år
 * Magazine används av LibrarySystem som hanterar in- och utlånning samt lagring av tidnings-objekt
 * Klassen implementerar gränssnittet Borrowable
 * vilket gör att den måste definera funktioner för lån och retur av en tidning
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

    /***LÅN- OCH RETURFUNKTIONER***/
    @Override
    public boolean borrowItem(){
        if (getIsAvailable()) {
            setIsAvailable(false);
            IO.println("\nTidningen lånades ut");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void returnItem(){
        setIsAvailable(true);
        IO.println("\nTidningen lämnades tillbaka.");
    }
}
