package programmeringprojekt.items;

public class Game extends Media{
    private String genre;
    private int age;

    /***KONSTRUKTOR***/
    public Game(String type, String id, String title, boolean isAvailable, String genre, int age){
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

    /***GETTERS***/
    public String getGenre() {
        return genre;
    }

    public int getAge() {
        return age;
    }

    //GET INFO
    public String getInfo(){
        return "GAME | ID: " + id + " | Title: " + title +
         " | isAvailable: " + isAvailable + " | Age: " + 
         age + " | Genre: " + genre;
    };

    

    
}
