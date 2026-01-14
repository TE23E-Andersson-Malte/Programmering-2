import java.util.Random;

void main() {
    Random dice = new Random();
    int slumptal = dice.nextInt(10) + 1;

    IO.println("Willkommen allez zu GISSNINGSLEKEN");

    int gissat_tal = 0;

    while (true) {
        String gissning = IO.readln("Gissa på ett tal mellan 1-10: ");

        try {
            gissat_tal = Integer.parseInt(gissning);
            break;
        } catch (Exception e) {
            IO.println("Använd tal 1-10");
            continue;
        }
    }

    IO.println("Du gissade " + gissat_tal);

    if (gissat_tal < slumptal)
        IO.println("Ditt tal är mindre än slumptalet");
    else if (gissat_tal == slumptal)
        IO.println("Korrekt svar");
    else
        IO.println("Ditt tal är större än slumptalet");

    IO.println("Talet var " + slumptal);
}