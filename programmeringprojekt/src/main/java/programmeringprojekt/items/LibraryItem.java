package programmeringprojekt.items;

/**
 * Filen innehåller den abstrakta klassen LibraryItem (biblioteksförenål)
 * som representerar ett föremål i ett bibliotek
 * och innehåller egenskaperna id, titel och isAvailable (om föremålet är
 * tillgängligt för utlåning)
 * LibraryItem används av barnklasserna Book och Magazine
 * som ärver variabler och metoder
 * Eftersom LibraryItem är abstrkt kan inga objekt av denna klass skapas direkt,
 * utan endast genom dess subklasser
 * Klassen implementerar gränssnittet comparable (vilket betyder att Book och
 * Magazine också implementerar det) vilket gör det möjligt att sortera objekt
 * efter titel
 * 
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public abstract class LibraryItem implements Comparable<LibraryItem> {
    protected String id;
    protected String title;
    protected boolean isAvailable;

    /**
     * Skapar ett nytt LibraryItem‑objekt med angivet id, titel och tillgänglighet.
     *
     * @param id          unikt id för objektet
     * @param title       objektets titel
     * @param isAvailable om objektet är tillgängligt för utlåning
     * @throws IllegalArgumentException om titel är null eller tomt
     */
    public LibraryItem(String id, String title, boolean isAvailable) {
        this.id = id;
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Titel får inte vara tomt");
        }
        this.title = title.trim();
        this.isAvailable = isAvailable;
    }

    /**
     * Hämtar objektets id.
     *
     * @return objektets id
     */
    public String getId() {
        return id;
    }

    /**
     * Hämtar objektets titel.
     *
     * @return objektets titel
     */
    public String getTitle() {
        return title;
    }

    /**
     * Hämtar om objektet är tillgängligt för utlåning.
     *
     * @return true om objektet är tillgängligt, annars false
     */
    public boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * Uppdaterar objektets tillgänglighetsstatus.
     *
     * @param isAvailable true om objektet ska markeras som tillgängligt,
     *                    annars false
     */
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * Jämför två LibraryItem‑objekt baserat på titel
     * (alfabetiskt, ej skiftlägeskänsligt).
     *
     * @param other det andra objektet att jämföra med
     * @return negativt tal, noll eller positivt tal beroende på sorteringsordning
     */
    public int compareTo(LibraryItem other) {
        return this.title.compareToIgnoreCase(other.title);
    };

    /**
     * Implementeras av subklasser för att inkludera specifik information.
     */
    public abstract String getInfo();
}
