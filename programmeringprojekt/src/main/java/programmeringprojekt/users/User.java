package programmeringprojekt.users;

import java.util.ArrayList;

import programmeringprojekt.interfaces.Borrowable;

/*
Malte Andersson
Klassen User representerar en kund/användare i bibliotekssytemet
och innehåller grundläggande information som namn och email, samt en lista med lånade objekt
User-objekt används av LibrarySystem för att hantera registrering, sökning, borttagning och sortering av kunder
Klassen implementerar gränssnittet comparable vilket gör det möjligt att sortera användare efter namn
*/

public class User implements Comparable<User> {
    private String id;
    private String name;
    private String email;
    private transient ArrayList<Borrowable> borrowedItems = new ArrayList<>();

    /*** KONSTRUKTOR ***/
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

    public User() {
    };

    /*** GETTERS ***/
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Borrowable> getBorrowedItems() {
        return borrowedItems;
    }

    /*** SETTERS ***/
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Namn får inte vara tomt");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email får inte vara tomt");
        }
        this.email = email;
    }

    /*** LÄGG TILL OCH TA BORT LÅNADE FÖREMÅL ***/
    public void addBorrowedItem(Borrowable item) {
        borrowedItems.add(item);
    }

    public void returnBorrowedItem(Borrowable item) {
        borrowedItems.remove(item);
    }

    /*** toString ***/
    public String toString() {
        return "USER | ID: " + id + " | Name: " + name +
                " | Email: " + email + " | Borrowed items: " + borrowedItems;
    }

    /*** Compare to ***/
    @Override
    public int compareTo(User other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
