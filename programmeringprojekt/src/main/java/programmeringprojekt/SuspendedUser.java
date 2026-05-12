package programmeringprojekt;

public class SuspendedUser {
    private String userId;
    private String id;

    public SuspendedUser(String id, String userId){
        this.id = id;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String toString(){
        return "SUSPENDED USER | ID: " + id +
         " | User ID: " + userId;
    }
}
