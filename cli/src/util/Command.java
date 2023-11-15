package util;

public class Command {
    public enum Mode{ CREATE,READ,UPDATE,DELETE,PERSISTANCE }
    public Mode mode;
    public String text;
    public Command(){
        this.mode = null;
        this.text = "";
    }

    public void nextCommand(String text){
        if (text.startsWith(":")){
            String mod = text.substring(0,2);
            switch (mod) {
                case ":c" -> {
                    this.mode = Mode.CREATE;
                    this.text = text.replace(":c", "");
                }
                case ":r" -> {
                    this.mode = Mode.READ;
                    this.text = text.replace(":r", "");
                }
                case ":u" -> {
                    this.mode = Mode.UPDATE;
                    this.text = text.replace(":u", "");
                }
                case ":d" -> {
                    this.mode = Mode.DELETE;
                    this.text = text.replace(":d", "");
                }
                //TODO: Persitance Mode when needed
                default -> this.text = text;
            }
        }



    }

}


/*
public enum Operator{ PLUS,MINUS,ERROR }
    public final Operator operator;
    public final int number;
    public Command(String text){
        String op=text.substring(0,1);
        int n=0;
        try { n=Integer.parseInt(text.substring(1)); } catch (NumberFormatException e){ op=""; }
        switch (op){
            case "+":
                this.operator=Operator.PLUS;
                break;
            case "-":
                this.operator=Operator.MINUS;
                break;
            default:
                this.operator=Operator.ERROR;
                break;
        }
        this.number=n;
    }
}
 */