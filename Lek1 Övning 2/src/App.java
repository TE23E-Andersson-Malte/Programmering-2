
public class App {
    public static void main(String[] args) throws Exception {
        String namn = IO.readln("Ange ditt namn: ");
        int ålder = 0;
        boolean vip = false;

        while (true) {
            try {
                ålder = Integer.parseInt(IO.readln("Ange din ålder: "));
                String svar = IO.readln("Är du medlem i VIP-klubben? (true/false): ");
                vip = Boolean.parseBoolean(svar);
                break;
            } catch (NumberFormatException e) {
                IO.println("Ange en acceptabel ålder");
                continue;
            }
        }
        if (ålder >= 18 && vip)
            IO.println("Välkommen in " + namn + " <3");
        else
            IO.println("Tyvärr " + namn + ", du får inte komma in :P");
    }
}