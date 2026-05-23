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

    /**
     * Skapar ett nytt Book‑objekt med angivet id, titel, tillgänglighet,
     * författare, genre och antal sidor.
     *
     * @param id          unikt id för boken
     * @param title       bokens titel
     * @param isAvailable om boken är tillgänglig för utlåning
     * @param author      bokens författare
     * @param genre       bokens genre
     * @param pages       antal sidor i boken (måste vara positivt)
     * @throws IllegalArgumentException om author eller genre är null eller tomt,
     *                                  eller om pages är mindre än eller lika med
     *                                  noll
     */
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

    /**
     * Hämtar bokens författare.
     *
     * @return författarens namn
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Hämtar bokens genre.
     *
     * @return bokens genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Hämtar antal sidor i boken.
     *
     * @return antal sidor
     */
    public int getPages() {
        return pages;
    }

    /**
     * Returnerar en textrepresentation av boken,
     * inklusive id, titel, tillgänglighet, författare, genre och antal sidor.
     *
     * @return en formaterad sträng med bokens information
     */
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
