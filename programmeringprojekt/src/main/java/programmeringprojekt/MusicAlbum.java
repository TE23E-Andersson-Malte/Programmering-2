package programmeringprojekt;

public class MusicAlbum extends Media {
    private String artist;

    /*** KONSTRUKTOR ***/
    public MusicAlbum(String id, String title, boolean isAvailable, String artist) {
        super(id, title, isAvailable);
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
