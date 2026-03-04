public class App {
    public static void main(String[] args) throws Exception {
        // TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
        // click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
        // Array av basklasstypen Fiende
        Fiende[] monster = new Fiende[2];

        monster[0] = new Troll("Grovfot", 80, 100);
        monster[1] = new Magiker("Zargon", 50, 25);

        System.out.println("--- Börjar striden! ---");

        // Polymorfism: Båda anropar utförAttack(), men med unika effekter
        for (Fiende f : monster) {
            f.förflyttning();
            f.attack();
        }
    }
}
