package programmeringprojekt.loans;

public class Loan {
    private String userId;
    private String itemId;

    public Loan(String userId, String itemId){
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
     public String toString(){
        return "LOAN | User: " + userId + "| Item: " + itemId;
     }
}
