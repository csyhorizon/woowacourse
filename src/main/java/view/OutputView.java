package view;

import common.InputMessage;

public class OutputView {
    public void showPrompt(InputMessage message) {
        System.out.println(message.getMessage());
    }

    public void printErrorMessage(String message) {
        System.err.println(message);
    }
}
