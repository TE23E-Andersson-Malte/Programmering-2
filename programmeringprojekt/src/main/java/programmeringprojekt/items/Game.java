package programmeringprojekt.items;

/**
 * Representerar ett spel i bibliotekssystemet.
 * Ärver från Media och lägger till genre och rekommenderad ålder.
 *
 * Klassen används av LibrarySystem för att hantera och visa information
 * om spel som kan lånas ut.
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class Game extends Media {
    private String genre;
    private int age;

    /**
     * Skapar ett nytt Game‑objekt.
     * Ärver type, id, title och isAvailable från Media och lägger till
     * spelets genre och rekommenderad ålder.
     *
     * @param type        medietyp (t.ex. "game")
     * @param id          unikt id för spelet
     * @param title       spelets titel
     * @param isAvailable om spelet är tillgängligt för utlåning
     * @param genre       spelets genre
     * @param age         rekommenderad ålder för spelet
     * @throws IllegalArgumentException om genre är null eller tomt,
     *                                  eller om age är mindre än eller lika med
     *                                  noll
     */
    public Game(String type, String id, String title, boolean isAvailable, String genre, int age) {
        super(type, id, title, isAvailable);
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Genre får inte vara tomt");
        }
        this.genre = genre.trim();
        if (age <= 0) {
            throw new IllegalArgumentException("Ålder måste vara positiv");
        }
        this.age = age;
    }

    /**
     * Hämtar spelets genre.
     *
     * @return spelets genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Hämtar spelets rekommenderade ålder.
     *
     * @return åldersrekommendationen
     */
    public int getAge() {
        return age;
    }

    /**
     * Returnerar en textrepresentation av spelet,
     * inklusive id, titel, tillgänglighet, åldersgräns och genre.
     *
     * @return en formaterad sträng med spelets information
     */
    public String getInfo() {
        return "GAME | ID: " + id + " | Title: " + title +
                " | isAvailable: " + isAvailable + " | Age: " +
                age + " | Genre: " + genre;
    };

}
