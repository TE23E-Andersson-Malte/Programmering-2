package programmeringprojekt.items;

import programmeringprojekt.loans.Borrowable;

/**
 * Abstrakt basklass för alla medietyper i bibliotekssystemet.
 * Innehåller gemensamma fält som id, titel, typ och tillgänglighet,
 * samt standardfunktioner för lån och retur enligt Borrowable.
 *
 * Klassen används som grund för t.ex. Movie, MusicAlbum och Game.
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public abstract class Media implements Comparable<Media>, Borrowable {
    protected String id;
    protected String title;
    protected boolean isAvailable;
    protected String type;

    /*** KONSTRUKTOR ***/
    public Media(String type, String id, String title, boolean isAvailable) {
        this.id = id;
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Titel får inte vara tomt");
        }
        this.title = title.trim();
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Typ får inte vara tomt");
        } else if (type.equals("game") && type.equals("music_album") && type.equals("movie")) {
            throw new IllegalArgumentException("Ogiltig typ av media");
        }
        this.type = type;
        this.isAvailable = isAvailable;
    }

    /*** GETTERS ***/
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public String getType() {
        return type;
    }

    /*** SETTERS ***/
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // COMPARE TO metod
    @Override
    public int compareTo(Media other) {
        return this.title.compareToIgnoreCase(other.title);
    };

    // GET INFO
    public abstract String getInfo();

    /**
     * Försöker låna ut objektet.
     * Om objektet är tillgängligt markeras det som utlånat och true returneras.
     * Om det redan är utlånat returneras false.
     *
     * @return true om utlåningen lyckades, annars false
     */
    public boolean borrowItem() {
        if (getIsAvailable()) {
            setIsAvailable(false);
            IO.println("\nObjektet lånades ut");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Markerar objektet som återlämnat och gör det tillgängligt igen.
     * Skriver även ut ett meddelande om att objektet lämnades tillbaka.
     */
    public void returnItem() {
        setIsAvailable(true);
        IO.println("\nObjektet lämnades tillbaka.");
    }
}
