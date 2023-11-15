package viewControl;

import java.util.Scanner;
import util.Command;

public class Console {
    public void execute(){
        try (Scanner s = new Scanner(System.in)){
            Command c = new Command();
            do {
                System.out.println("Enter Command: ");
                c.nextCommand(s.next());
            }while (true);
        }
    }
}
