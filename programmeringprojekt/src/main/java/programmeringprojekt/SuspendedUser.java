package programmeringprojekt;

public class SuspendedUser {
    private String customer_id;
    private String id;

    public SuspendedUser(String id, String customer_id){
        this.id = id;
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customer_id;
    }

    public String toString(){
        return "SUSPENDED USER | ID: " + id +
         " | Customer ID: " + customer_id;
    }
}
