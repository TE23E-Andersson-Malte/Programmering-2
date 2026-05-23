package programmeringprojekt.items;

import programmeringprojekt.loans.Borrowable;

/**
 * Klassen Book är en barnklass till den abstrakta klassen LibraryItem
 * och representerar en bok i biblioteket med unika egenskaper
 * som författare, genre och antal sidor
 * Book används av LibrarySystem som hanterar in- och utlånning samt lagring av
 * bok-objekt
 * Klassen implementerar gränssnittet Borrowable
 * vilket gör att den måste definera funktioner för lån och retur av en bok
 * 
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class Book extends LibraryItem implements Borrowable {
    private String author;
    private String genre;
    private int pages;

    /*** KONSTRUKTOR ***/
    public Book(String id, String title, boolean isAvailable, String author, String genre, int pages) {
        super(id, title, isAvailable);
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Författare får inte vara tomt");
        }
        this.author = author;
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Genre får inte vara tomt");
        }
        this.genre = genre;
        if (pages <= 0) {
            throw new IllegalArgumentException("Antal sidor måste vara ett positivt tal");
        }
        this.pages = pages;
    }

    /*** GETTERS ***/
    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    // GET INFO
    @Override
    public String getInfo() {
        return "BOOK | ID: " + id + " | Title: " + title +
                " | isAvailable: " + isAvailable + " | Author: " +
                author + " | Genre: " + genre + " | Pages: " + pages;
    }

    /**
     * Försöker låna ut boken.
     * Om boken är tillgänglig markeras den som utlånad och true returneras.
     * Om den redan är utlånad returneras false.
     *
     * @return true om utlåningen lyckades, annars false
     */
    public boolean borrowItem() {
        if (getIsAvailable()) {
            setIsAvailable(false);
            IO.println("\nBoken lånades ut");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Markerar boken som återlämnad och gör den tillgänglig igen.
     * Skriver även ut ett meddelande om att boken lämnades tillbaka.
     */
    public void returnItem() {
        setIsAvailable(true);
        IO.println("\nBoken lämnades tillbaka.");
    }
}
