package viewControl;

import java.util.Scanner;
import util.Command;
import verwaltungsImp.verkaufsAutomat;

public class Console {
    private verkaufsAutomat automat;
    public Console(verkaufsAutomat automat){ this.automat=automat; }
    public void execute() {
        try (Scanner s = new Scanner(System.in)) {
            Command c = new Command();
            do {
                System.out.println("What do you want to do?: ");
                String input = s.next();
                if (input.startsWith(":")){
                    c.nextCommand(input);
                }else{
                    switch (c.mode) {
                        case CREATE:

                            break;
                        case READ:

                            break;
                        case UPDATE:

                            break;
                        case DELETE:

                            break;
                        case NOMODE:
                            System.out.println("No Mode selected");
                            break;
                    }
                }
            } while (true);
        }
    }
}
