package programmeringprojekt;

public class SuspendedUser {
    private String reason;
    private String userId;
    private String id;

    public SuspendedUser(String id, String userId, String reason){
        this.id = id;
        this.userId = userId;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String toString(){
        return "SUSPENDED USER | ID: " + id +
         " | User ID: " + userId + " | Reason: " + reason;
    }
}
