package util;

public class Command {
    public enum Mode{ CREATE,READ,UPDATE,DELETE,NOMODE}
    private Mode mode;
    public Command(){
        this.mode = Mode.NOMODE;
    }

    public void nextCommand(String text){
        if (text.startsWith(":")){
            String mod = text.substring(0,2);
            switch (mod) {
                case ":c" -> {
                    this.mode = Mode.CREATE;
                    System.out.println("Switch to Create Mode");
                    System.out.println("Input [Kuchensorte] [Hersteller] [Preis] [Naehrwert] [Haltbarkeit] [[Allergene as List | only a ',' if no Allergene]] [Zutat]");
                }
                case ":r" -> {
                    this.mode = Mode.READ;
                    System.out.println("Switch to Read Mode");
                    System.out.append("Type 'all' to show all Cakes, type an Integer to inspect specific Cake");
                }
                case ":u" -> {
                    this.mode = Mode.UPDATE;
                    System.out.println("Switch to Update Mode");
                    System.out.println("type an Integer to Update a specific Cake");
                }
                case ":d" -> {
                    this.mode = Mode.DELETE;
                    System.out.println("Switch to Delete Mode");
                    System.out.println("type an Integer to Delete a specific Cake");
                }
                case ":e" -> {
                    this.mode = Mode.NOMODE;
                }
                default -> {
                    System.out.println("Command not known!");
                }
                //TODO: Persitance Mode when needed
            }
        }
    }
    public Mode getMode(){
        return this.mode;
    }
}