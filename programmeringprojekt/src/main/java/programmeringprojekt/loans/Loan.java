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

    public String getItemId() {
        return itemId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "LOAN | User: " + userId + "| Item: " + itemId;
    }
}
