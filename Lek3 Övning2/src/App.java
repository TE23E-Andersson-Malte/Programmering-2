public class App {
    public static void main(String[] args) throws Exception {
        
        Figure[] figures = new Figure[2];
        figures[0] = new Tree("green", 110);
        figures[1] = new Rock("gray", 90);

        IO.println("---ritar ut objekten---\n");
        for (Figure element : figures){
            element.draw();
            IO.println("\n---------------------\n");
        }


    }
}
