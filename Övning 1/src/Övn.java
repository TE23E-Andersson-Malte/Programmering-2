public class Övn {
    /**
     * public static void main(String[] args) throws Exception {
     * int ålder = 0;
     * while (true) {
     * try {
     * ålder = Integer.parseInt(IO.readln("Ange ålder: "));
     * if (ålder >= 18)
     * IO.println("Du är myndig");
     * else
     * IO.println("Du är omyndig");
     * break;
     * } catch (Exception e) {
     * IO.println("Du angav inte siffror, gör om");
     * continue;
     * }
     * }
     * }
     **/

    // Övning Array flera undantag
    public static void main(String[] args) {
        int[] array = new int[4];
        int index = 0;

        while (true) {
            try {
                index = Integer.parseInt(IO.readln("Ange element i array: "));
                IO.println("Plats " + index + " = " + array[index]);
                break;
            } catch (NumberFormatException e) {
                IO.println("FEL: Du använde inte siffror");
                continue;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                IO.println("FEL: Du angav fel index");
                continue;
            }
        }
    }
}
