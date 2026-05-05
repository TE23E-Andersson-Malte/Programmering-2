package programmeringprojekt;

/*
 * Malte Andersson
 * Klassen Magazine är en barnklass till den abstrakta klassen LibraryItem
 * och representerar en tidning i biblioteket med unika egenskaper
 * som utgåvanummer, kategori och publiserat år
 * Magazine används av LibrarySystem som hanterar in- och utlånning samt lagring av tidnings-objekt
 * Klassen implementerar gränssnittet Borrowable
 * vilket gör att den måste definera funktioner för lån och retur av en tidning
*/

public class Magazine extends LibraryItem implements Borrowable{
    private int issueNumber;
    private String category;
    private int publishedYear;

    /***KONSTRUKTOR***/
    public Magazine(String id, String title, boolean isAvailable, int issueNumber, String category, int publishedYear){
        super(id, title, isAvailable);
        this.issueNumber = issueNumber;
        this.category = category;
        this.publishedYear = publishedYear;
    }

    /***GETTERS***/
    public int getIssueNumber() {
        return issueNumber;
    }

    public String getCategory() {
        return category;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    //GET INFO
    @Override
    public String getInfo(){
        return "Book - ID: " + id + ", Title: " + title +
         ", IsAvailable: " + isAvailable + ", IssueNumber: " + 
         issueNumber + ", Category: " + category + ", PublishedYear: " + publishedYear;
    }

    /***LÅN- OCH RETURFUNKTIONER***/
    @Override
    public boolean borrowItem(){
        if (getIsAvailable()) {
            setIsAvailable(false);
            IO.println("\nTidningen lånades ut");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void returnItem(){
        setIsAvailable(true);
        IO.println("\nTidningen lämnades tillbaka.");
    }
}
