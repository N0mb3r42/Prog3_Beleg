package util;

public class Command {
    public enum Mode{ CREATE,READ,UPDATE,DELETE,NOMODE}
    private Mode mode;
    public Command(){
        this.mode = Mode.NOMODE;
    }

    public String nextCommand(String text){
        if (text.startsWith(":")){
            String mod = text.substring(0,2);
            switch (mod) {
                case ":c" -> {
                    this.mode = Mode.CREATE;
                    return "Switch to Create Mode\nInput [Kuchensorte] [Hersteller] [Preis] [Naehrwert] [Haltbarkeit] [[Allergene as List | only a ',' if no Allergene]] [Zutat]\n";
                }
                case ":r" -> {
                    this.mode = Mode.READ;
                    return "Switch to Read Mode\nType 'all' to show all Cakes, type an Integer to inspect specific Cake\n";
                }
                case ":u" -> {
                    this.mode = Mode.UPDATE;
                    return "Switch to Update Mode\ntype an Integer to Update a specific Cake\n";
                }
                case ":d" -> {
                    this.mode = Mode.DELETE;
                    return "Switch to Delete Mode\ntype an Integer to Delete a specific Cake\n";
                }
                case ":e" -> {
                    this.mode = Mode.NOMODE;
                    return "Exiting...\n";
                }
                default -> {
                    return "Command not known!\n";
                }
                //TODO: Persitance Mode when needed
            }
        }
        return null;
    }
    public Mode getMode(){
        return this.mode;
    }
}