package programmeringprojekt;

import java.security.PublicKey;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private List<Borrowable> borrowedItems;

    /***KONSTRUKTOR***/
    public User(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /***GETTERS***/
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Borrowable> getBorrowedItems() {
        return borrowedItems;
    }

    /***SETTERS***/
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /***LÄGG TILL OCH TA BORT LÅNADE FÖREMÅL***/
    public void addBorrowedItem(Borrowable item){
        borrowedItems.add(item);
    }

    public void returnBorrowedItem(Borrowable item){
        borrowedItems.remove(item);
    }
    
    /***toString***/
    public String toString(){
        return "USER | ID: " + id + " | Name: " + name +
         " | Email: " + email + " | Borrowed items: " + borrowedItems;
    }
}
