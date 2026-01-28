public class App {
    public static void main(String[] args) throws Exception {
        
        BilRegister register = new BilRegister();
        
        //Lägg till bilar
        Bil bil = new Bil("Volvo", "140", "ABC123", 1999);
        register.läggTillBil(bil);

        register.läggTillBil(new Bil("Saab", "9-5", "XYZ789", 2010));
    
        register.läggTillBil(new Bil("Tesla", "Model Y", "DEF456", 2023));

        //Skriv ut bilar
        IO.println("Alla bilar i registret: ");
        IO.println(register.toString());

        register.taBortBil("DEF456");
        IO.println("Bilar i registret efter borttagning:");
        IO.println(register.toString());

        //Testa lägg till
        try {
            register.läggTillBil(bil);
        } catch (Exception e) {
            IO.println("Fel: " + e.getMessage());
        }

        //Hitta bil
        Bil hittadBil = register.hittaBil("ABC123");
        IO.println("Hittad bil: " + hittadBil);
    }
}
