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

    /**
     * Skapar ett nytt Media‑objekt med angiven typ, id, titel och tillgänglighet.
     *
     * @param type        medietyp (t.ex. "movie", "game", "music_album")
     * @param id          unikt id för objektet
     * @param title       objektets titel
     * @param isAvailable om objektet är tillgängligt för utlåning
     * @throws IllegalArgumentException om titel eller typ är null eller tomt,
     *                                  eller om typen inte är giltig
     */
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
     * Hämtar objektets medietyp.
     *
     * @return medietypen som sträng
     */
    public String getType() {
        return type;
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
     * Jämför två Media‑objekt baserat på titel (alfabetiskt, ej
     * skiftlägeskänsligt).
     *
     * @param other det andra mediaobjektet att jämföra med
     * @return negativt tal, noll eller positivt tal beroende på sorteringsordning
     */
    @Override
    public int compareTo(Media other) {
        return this.title.compareToIgnoreCase(other.title);
    };

    /**
     * Returnerar en textrepresentation av objektet.
     * Implementeras av underklasser för att inkludera specifik information.
     *
     * @return en formaterad sträng med objektets information
     */
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
