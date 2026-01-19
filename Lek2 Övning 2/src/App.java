public class App {
    public static void main(String[] args) throws Exception {
        try {
            Person p1 = new Person(-1990, "Anna");
        } catch (IllegalArgumentException e) {
            IO.println("Fel när p1 skapades: " + e.getMessage());
        }
        try {
            Person p2 = new Person(1990, "");
        } catch (IllegalArgumentException e) {
            IO.println("Fel när p2 skapades: " + e.getMessage());
        }
        try {
            Person p3 = new Person(2000, null);
        } catch (IllegalArgumentException e) {
            IO.println("Fel när p3 skapades: " + e.getMessage());
        }
        try {
            Person p4 = new Person(1985, "Lisa");
            p4.setName("");
        } catch (IllegalArgumentException e) {
            IO.println("Fel vid namnändring: " + e.getMessage());
        }
        try {
            Person p5 = new Person(1995, "Erik");
            IO.println("Lyckades skapa: " + p5);
        } catch (IllegalArgumentException e) {
            IO.println("Detta borde inte hända: " + e.getMessage());
        }
    }
}