package programmeringprojekt.items;

/**
 * Representerar ett musikalbum i bibliotekssystemet.
 * Klassen ärver från Media (type, id, title, isAvailable) och lägger till en egen variabel: artist.
 * Används av LibrarySystem för att hantera musikalbum-objekt
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class MusicAlbum extends Media {
    private String artist;

    /*** KONSTRUKTOR ***/
    public MusicAlbum(String type, String id, String title, boolean isAvailable, String artist) {
        super(type, id, title, isAvailable);
        if (artist == null || artist.isBlank()) {
            throw new IllegalArgumentException("Artist får inte vara tomt");
        }
        this.artist = artist.trim();
    }

    /*** GETTERS ***/
    public String getArtist() {
        return artist;
    }

    // GET INFO
    public String getInfo() {
        return "MUSIC ALBUM | ID: " + id + " | Title: " + title +
                " | isAvailable: " + isAvailable + " | Artist: " + artist;
    }
}
