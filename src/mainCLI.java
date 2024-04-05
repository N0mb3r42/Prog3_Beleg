import verwaltungsImp.verkaufsAutomat;

import viewControl.Console;

public class mainCLI {
    public static void main(String[] args) {
        verkaufsAutomat automat = new verkaufsAutomat(Integer.parseInt(args[0]));
        Console cli = new Console(automat);
        cli.execute();

    }
}
