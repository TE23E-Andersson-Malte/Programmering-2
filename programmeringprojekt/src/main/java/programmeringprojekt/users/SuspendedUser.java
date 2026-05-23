package programmeringprojekt.users;

/**
 * Klassen SuspendedUser representerar en avstängd kund i bibliotekssystemet
 * Den lagrar en koppling till en befintlig användare genom customer_id
 * (tidigare userId)
 * vilket gör det möjlit för LibrarySystem att avgöra om en kund är avstängd och
 * därmed inte får långa material
 * Klassen används även av LibrarySystem för kontroll av lånerättigheter samt
 * hämtning, skapande och borttagning av avstängda kunder
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class SuspendedUser {
    private String customer_id;
    private String id;

    /**
     * Skapar ett nytt SuspendedUser‑objekt.
     * Tar emot ett unikt id för avstängningen samt ett customer_id
     * som refererar till den användare som är avstängd.
     *
     * @param id          unikt id för avstängningsposten
     * @param customer_id id för användaren som är avstängd
     * @throws IllegalArgumentException om customer_id är null eller tomt
     */
    public SuspendedUser(String id, String customer_id) {
        this.id = id;
        if (customer_id == null || customer_id.isBlank()) {
            throw new IllegalArgumentException("AnvändarID får inte vara tomt");
        }
        this.customer_id = customer_id;
    }

    /**
     * Hämtar id för avstängningsposten.
     *
     * @return avstängningens id
     */
    public String getId() {
        return id;
    }

    /**
     * Hämtar id för den användare som är avstängd.
     *
     * @return kundens id
     */
    public String getCustomerId() {
        return customer_id;
    }

    /**
     * Returnerar en textrepresentation av den avstängda kunden,
     * inklusive avstängnings‑id och kopplat customer_id.
     *
     * @return en formaterad sträng med objektets information
     */
    public String toString() {
        return "SUSPENDED USER | ID: " + id +
                " | Customer ID: " + customer_id;
    }
}
