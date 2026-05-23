package programmeringprojekt.items;

/**
 * Representerar en film i bibliotekssystemet.
 * Klassen ärver från Media (type, id, title, isAvailable) och lägger till egna
 * variablar: genre och minutes.
 * Används av LibrarySystem för att hantera film-objekt
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class Movie extends Media {
    private String genre;
    private int minutes;

    /**
     * Skapar ett nytt Movie‑objekt.
     * Ärver type, id, title och isAvailable från Media och lägger till
     * filmens genre och längd i minuter.
     *
     * @param type        typen av media (t.ex. "movie")
     * @param id          unikt id för filmen
     * @param title       filmens titel
     * @param isAvailable om filmen är tillgänglig för utlåning
     * @param genre       filmens genre
     * @param minutes     filmens längd i minuter
     * @throws IllegalArgumentException om genre är null eller tomt,
     *                                  eller om minutes är mindre än eller lika med
     *                                  noll
     */
    public Movie(String type, String id, String title, boolean isAvailable, String genre, int minutes) {
        super(type, id, title, isAvailable);
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Genre får inte vara tomt");
        }
        this.genre = genre.trim();
        if (minutes <= 0) {
            throw new IllegalArgumentException("Antal minuter måste vara positiv");
        }
        this.minutes = minutes;
    }

    /**
     * Hämtar filmens genre.
     *
     * @return filmens genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Hämtar filmens längd i minuter.
     *
     * @return antal minuter filmen är lång
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Returnerar en textrepresentation av filmen,
     * inklusive id, titel, tillgänglighet, längd och genre.
     *
     * @return en formaterad sträng med filmens information
     */
    public String getInfo() {
        return "MOVIE | ID: " + id + " | Title: " + title +
                " | isAvailable: " + isAvailable + " | Minutes: " +
                minutes + " | Genre: " + genre;
    };

}
