package programmeringprojekt.items;

/**
 * Representerar ett musikalbum i bibliotekssystemet.
 * Klassen ärver från Media (type, id, title, isAvailable) och lägger till en
 * egen variabel: artist.
 * Används av LibrarySystem för att hantera musikalbum-objekt
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class MusicAlbum extends Media {
    private String artist;

    /**
     * Skapar ett nytt MusicAlbum‑objekt.
     * Ärver grundläggande egenskaper från Media och lägger till artist.
     *
     * @param type        typen av media (t.ex. "MusicAlbum")
     * @param id          unikt id för albumet
     * @param title       albumets titel
     * @param isAvailable om albumet är tillgängligt för utlåning
     * @param artist      namnet på artisten
     * @throws IllegalArgumentException om artist är null eller tomt
     */
    public MusicAlbum(String type, String id, String title, boolean isAvailable, String artist) {
        super(type, id, title, isAvailable);
        if (artist == null || artist.isBlank()) {
            throw new IllegalArgumentException("Artist får inte vara tomt");
        }
        this.artist = artist.trim();
    }

    /**
     * Hämtar artistens namn.
     *
     * @return artistens namn
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Returnerar en textrepresentation av musikalbumet,
     * inklusive id, titel, tillgänglighet och artist.
     *
     * @return en formaterad sträng med albumets information
     */
    public String getInfo() {
        return "MUSIC ALBUM | ID: " + id + " | Title: " + title +
                " | isAvailable: " + isAvailable + " | Artist: " + artist;
    }
}
