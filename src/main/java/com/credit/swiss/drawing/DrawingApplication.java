package com.credit.swiss.drawing;

import com.alexander.makarov.drawing.command.AbstractCommand;
import com.alexander.makarov.drawing.command.QuiteCommand;
import com.credit.swiss.drawing.dataobjects.CommandExecutionResult;

import java.util.Scanner;

import static com.alexander.makarov.drawing.command.CommandFactory.getCommand;

// Merged invoker and client classes from classical GoF command pattern
public class DrawingApplication {

    private static final String ENTER_COMMAND_MESSAGE = "enter command: ";

    public void run() {
        Scanner scanner = new Scanner(System.in);

        CommandExecutionResult executionResult = new CommandExecutionResult(null, "", false);
        AbstractCommand command = null;

        while (!(command instanceof QuiteCommand)) {
            System.out.print(ENTER_COMMAND_MESSAGE);

            String input = scanner.nextLine();
            command = getCommand(input);

            executionResult = command.execute(executionResult.getCanvas());
            if (executionResult.getCanvas() != null) {
                executionResult.getCanvas().paint();
            }

            if (executionResult.isSuccessful()) {
                System.out.println("[SUCCESS]: " + executionResult.getMessage());
            } else {
                System.out.println("[FAILURE]: " + executionResult.getMessage());
            }
        }
    }

}
