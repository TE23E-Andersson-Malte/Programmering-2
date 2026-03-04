public class App {
    public static void main(String[] args) throws Exception {
        Djur[] djurfält = new Djur[5];

        djurfält[0] = new Hund(0, 0);
        djurfält[1] = new Hund(0, 0);

        djurfält[2] = new Katt(0, 0);
        djurfält[3] = new Katt(0, 0);
        
        djurfält[4] = new Mus(0, 0);

        for (Djur djur : djurfält) {
            IO.println(djur.Läte());
        }

        IO.println("----------");

        for (Djur djur : djurfält) {
            if (djur instanceof Hund) {
                ((Hund) djur).Spåra("kanin");
            } else if (djur instanceof Katt){
                ((Katt) djur).Leka("garnnystan");
            } else if (djur instanceof Mus){
                ((Mus) djur).Klättra();
            }
            IO.print("\n");
        }

    }
}
