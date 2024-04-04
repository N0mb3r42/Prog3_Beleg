package util;

public class Command {
    public enum Mode{ CREATE,READ,UPDATE,DELETE,NOMODE,PERSIST}
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
                    return "Switch to Create Mode\n";
                }
                case ":r" -> {
                    this.mode = Mode.READ;
                    return "Switch to Read Mode\n";
                }
                case ":u" -> {
                    this.mode = Mode.UPDATE;
                    return "Switch to Update Mode\n";
                }
                case ":d" -> {
                    this.mode = Mode.DELETE;
                    return "Switch to Delete Mode\n";
                }
                case ":e" -> {
                    this.mode = Mode.NOMODE;
                    return "Exiting...\n";
                }
                case ":p" -> {
                    this.mode = Mode.PERSIST;
                    return "Switch to Persitence Mode\n";
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