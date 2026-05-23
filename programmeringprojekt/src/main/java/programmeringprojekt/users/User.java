package programmeringprojekt.users;

/**
 * Klassen User representerar en kund/användare i bibliotekssytemet
 * och innehåller grundläggande information som namn och email, samt en lista
 * med lånade objekt
 * User-objekt används av LibrarySystem för att hantera registrering, sökning,
 * borttagning och sortering av kunder
 * Klassen implementerar gränssnittet comparable vilket gör det möjligt att
 * sortera användare efter namn
 * 
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class User implements Comparable<User> {
    private String id;
    private String name;
    private String email;

    /**
     * Skapar en ny användare med angivet id, namn och email.
     *
     * @param id    unikt id för användaren
     * @param name  användarens namn
     * @param email användarens emailadress
     * @throws IllegalArgumentException om namn eller email är null eller tomt
     */
    public User(String id, String name, String email) {
        this.id = id;
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Namn får inte vara tomt");
        }
        this.name = name;
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email får inte vara tomt");
        }
        this.email = email;
    }

    /**
     * Hämtar användarens id.
     *
     * @return användarens id
     */
    public String getId() {
        return id;
    }

    /**
     * Hämtar användarens namn.
     *
     * @return användarens namn
     */
    public String getName() {
        return name;
    }

    /**
     * Hämtar användarens emailadress.
     *
     * @return användarens email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Uppdaterar användarens namn.
     *
     * @param name det nya namnet
     * @throws IllegalArgumentException om namnet är null eller tomt
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Namn får inte vara tomt");
        }
        this.name = name;
    }

    /**
     * Uppdaterar användarens emailadress.
     *
     * @param email den nya emailadressen
     * @throws IllegalArgumentException om email är null eller tomt
     */
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email får inte vara tomt");
        }
        this.email = email;
    }

    /**
     * Returnerar en textrepresentation av användaren,
     * inklusive id, namn och email.
     *
     * @return en formaterad sträng med användarens information
     */
    public String toString() {
        return "USER | ID: " + id + " | Name: " + name +
                " | Email: " + email;
    }

    /**
     * Jämför två användare baserat på namn (alfabetiskt, ej skiftlägeskänsligt).
     *
     * @param other den andra användaren att jämföra med
     * @return ett negativt tal, noll eller positivt tal beroende på
     *         sorteringsordning
     */
    @Override
    public int compareTo(User other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
