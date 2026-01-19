public class App {
    public static void main(String[] args) throws Exception {
        Boolean kör = true;

        while (kör) {

            try {
                IO.println("""
                        1. Addition
                        2. Subtraktion
                        3. Multiplikation
                        4. Division
                        5. Avsluta
                        """);
                int val = Integer.parseInt(IO.readln("Ange val: "));

                double[] tal = {};
                switch (val) {
                    case 1:
                        tal = frågaTal();
                        addition(tal[0], tal[1]);
                        break;
                    case 2:
                        tal = frågaTal();
                        subtraktion(tal[0], tal[1]);
                        break;
                    case 3:
                        tal = frågaTal();
                        multiplikation(tal[0], tal[1]);
                        break;
                    case 4:
                        tal = frågaTal();
                        division(tal[0], tal[1]);
                        break;
                    case 5:
                        kör = false;
                        IO.println("Avslutar programmet...");
                        break;
                    default:
                        IO.println("Fel val!");
                }
            } catch (Exception e) {
                IO.println("Ange ett giltigt tal, försök igen!");
            }
        }
    }

    /******
     * METODER
     ******/

    public static double[] frågaTal() {
        double tal1 = Double.parseDouble(IO.readln("Ange tal 1: "));
        double tal2 = Double.parseDouble(IO.readln("Ange tal 2: "));
        return new double[] { tal1, tal2 };
    }

    public static void addition(double tal1, double tal2) {
        double resultat = tal1 + tal2;
        IO.println(tal1 + " + " + tal2 + " = " + resultat);
    }

    public static void subtraktion(double tal1, double tal2) {
        double resultat = tal1 - tal2;
        IO.println(tal1 + " - " + tal2 + " = " + resultat);
    }

    public static void multiplikation(double tal1, double tal2) {
        double resultat = tal1 * tal2;
        IO.println(tal1 + " * " + tal2 + " = " + resultat);
    }

    public static void division(double tal1, double tal2) {
        double resultat = tal1 / tal2;
        IO.println(tal1 + " / " + tal2 + " = " + resultat);
    }
}