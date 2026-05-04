package programmeringprojekt;

public class Book extends LibraryItem implements Borrowable{
    private String author;
    private String genre;
    private int pages;

    /***KONSTRUKTOR***/
    public Book(String id, String title, boolean isAvailable, String author, String genre, int pages){
        super(id, title, isAvailable);
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    /***GETTERS***/
    public String getAuthor(){
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    //GET INFO
    @Override
    public String getInfo(){
        return "Book - ID: " + id + ", Title: " + title +
         ", isAvailable: " + isAvailable + ", Author: " + 
         author + ", Genre: " + genre + ", Pages: " + pages;
    }

    /***LÅN- OCH RETURFUNKTIONER***/
    public boolean borrowItem(){
        if (getIsAvailable()) {
            setIsAvailable(false);
            IO.println("\nBoken lånades ut");
            return true;
        } else {
            return false;
        }
    }

    public void returnItem(){
        setIsAvailable(true);
        IO.println("\nBoken lämnades tillbaka.");
    }
}
