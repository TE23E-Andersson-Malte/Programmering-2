package programmeringprojekt.items;

/**
 * Representerar en film i bibliotekssystemet.
 *  Klassen ärver från Media (type, id, title, isAvailable) och lägger till egna variablar: genre och minutes.
 * Används av LibrarySystem för att hantera film-objekt
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */


public class Movie extends Media{
    private String genre;
    private int minutes;

    /***KONSTRUKTOR***/
    public Movie(String type, String id, String title, boolean isAvailable, String genre, int minutes){
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

    /***GETTERS***/
    public String getGenre() {
        return genre;
    }

    public int getMinutes() {
        return minutes;
    }

    //GET INFO
    public String getInfo(){
        return "MOVIE | ID: " + id + " | Title: " + title +
         " | isAvailable: " + isAvailable + " | Minutes: " + 
         minutes + " | Genre: " + genre;
    };

    

    
}
