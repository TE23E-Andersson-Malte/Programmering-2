package programmeringprojekt.items;

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
