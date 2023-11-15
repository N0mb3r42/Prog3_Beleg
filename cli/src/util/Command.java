package util;

public class Command {
    public enum Mode{ CREATE,READ,UPDATE,DELETE,PERSISTANCE,NOMODE}
    public Mode mode;
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
                }
                case ":r" -> {
                    this.mode = Mode.READ;
                    System.out.println("Switch to Read Mode");
                }
                case ":u" -> {
                    this.mode = Mode.UPDATE;
                    System.out.println("Switch to Update Mode");
                }
                case ":d" -> {
                    this.mode = Mode.DELETE;
                    System.out.println("Switch to Delete Mode");
                }
                case ":e" -> {
                    this.mode = Mode.NOMODE;
                }
                //TODO: Persitance Mode when needed
            }
        }
    }
}