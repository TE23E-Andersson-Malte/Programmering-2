public class App {
    public static void main(String[] args) throws Exception {
        
        Bil min_bil = new Bil("Saab", "900", "ABC 771", 1978);
        System.out.println(min_bil);

        min_bil.ökaHastighet(90);
        IO.println("Efter att ha ökat hastigheten: " + min_bil.getHastighet() + "km/h");
    
        min_bil.bromsa(30);
        IO.println("Efter att ha minskat hastigheten: " + min_bil.getHastighet() + "km/h");

        try {
            min_bil.ökaHastighet(-10);
        } catch (Exception e) {
            IO.println("Fel: " + e.getMessage());
        }

        try {
            Bil min_nya_bil = new Bil("Volvo", "", "GREGER", 1999);
        } catch (Exception e) {
            IO.println("Fel när bilen skapades: " + e.getMessage());
        }
    }
}
