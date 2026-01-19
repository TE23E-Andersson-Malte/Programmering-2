public class App {
    public static void main(String[] args) throws Exception {
        Boolean kör = true;

        while (kör) {
            IO.println("""
                    1. Addition
                    2. Subtraktion
                    3. Multiplikation
                    4. Division
                    5. Avsluta
                    """);
            int val = Integer.parseInt(IO.readln("Ange val: "));
            switch (val) {
                case 1:
                    int add1 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int add2 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int addition = add1 + add2;
                    IO.println(add1 + " + " + add2 + " = " + addition);
                    break;
                case 2:
                    int sub1 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int sub2 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int subtraktion = sub1 - sub2;
                    IO.println(sub1 + " - " + sub2 + " = " + subtraktion);
                    break;
                case 3:
                    int mul1 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int mul2 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int multiplikation = mul1 * mul2;
                    IO.println(mul1 + " * " + mul2 + " = " + multiplikation);
                    break;
                    case 4:
                    int div1 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int div2 = Integer.parseInt(IO.readln("Ange tal 1: "));
                    int division = div1 / div2;
                    IO.println(div1 + " / " + div2 + " = " + division);
                    break;
                default:
                    break;
            }
        }
    }
}
