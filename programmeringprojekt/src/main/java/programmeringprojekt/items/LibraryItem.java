package programmeringprojekt.items;

/**
 * Filen innehåller den abstrakta klassen LibraryItem (biblioteksförenål)
 * som representerar ett föremål i ett bibliotek
 * och innehåller egenskaperna id, titel och isAvailable (om föremålet är tillgängligt för utlåning)
 * LibraryItem används av barnklasserna Book och Magazine
 * som ärver variabler och metoder
 * Eftersom LibraryItem är abstrkt kan inga objekt av denna klass skapas direkt, utan endast genom dess subklasser
 * Klassen implementerar gränssnittet comparable (vilket betyder att Book och Magazine också implementerar det) vilket gör det möjligt att sortera objekt efter titel
 * 
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public abstract class LibraryItem implements Comparable<LibraryItem>{
    protected String id;
    protected String title;
    protected boolean isAvailable;

    /***KONSTRUKTOR***/
    public LibraryItem(String id, String title, boolean isAvailable){
        this.id = id;
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Titel får inte vara tomt");
        }
        this.title = title.trim();
        this.isAvailable = isAvailable;
    }

    /***GETTERS***/
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsAvailable(){
        return isAvailable;
    }

    /***SETTERS***/
    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    //COMPARE TO metod
    public int compareTo(LibraryItem other){
        return this.title.compareToIgnoreCase(other.title);
    };

    //GET INFO
    public abstract String getInfo();
}
