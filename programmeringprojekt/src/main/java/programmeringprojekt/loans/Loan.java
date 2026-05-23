package programmeringprojekt.loans;

/**
 * Representerar ett lån i bibliotekssystemet.
 * Ett Loan kopplar samman en användare med ett lånat objekt genom userId och
 * itemId.
 *
 * Huvudansvar:
 * - Lagra information om vilken användare som lånat vilket objekt
 * - Säkerställa att userId och itemId är giltiga vid skapande
 *
 * Användning:
 * Loan-objekt skapas av LoanManager och används för att hålla reda på aktiva
 * lån.
 *
 * @author Malte Andersson
 * @version 1.0
 * @since 2026
 */

public class Loan {
    private String userId;
    private String itemId;

    /**
     * Skapar ett nytt Loan‑objekt som kopplar en användare till ett lånat objekt.
     *
     * @param userId id för användaren som lånar objektet
     * @param itemId id för objektet som lånas
     * @throws IllegalArgumentException om userId eller itemId är null eller tomt
     */
    public Loan(String userId, String itemId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserID får inte vara tomt");
        }
        this.userId = userId;
        if (itemId == null || itemId.isBlank()) {
            throw new IllegalArgumentException("ItemID får inte vara tomt");
        }
        this.itemId = itemId;
    }

    /**
     * Hämtar id för det lånade objektet.
     *
     * @return objektets id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Hämtar id för användaren som lånat objektet.
     *
     * @return användarens id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returnerar en textrepresentation av lånet,
     * inklusive userId och itemId.
     *
     * @return en formaterad sträng med låneinformation
     */
    @Override
    public String toString() {
        return "LOAN | User: " + userId + "| Item: " + itemId;
    }
}
