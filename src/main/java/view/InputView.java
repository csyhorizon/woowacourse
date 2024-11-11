package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String getInput() {
        return Console.readLine();
    }

    public boolean getCheckInput() {
        String CheckType = Console.readLine();
        return CheckType.equalsIgnoreCase("Y");
    }
}
