package com.alexander.makarov.drawing.command;

public class CommandFactory {

    private static final String COMMAND_SEPARATOR = " ";

    public static AbstractCommand getCommand(String input) {
        String[] arguments = input.split(COMMAND_SEPARATOR);
        String commandLetter = arguments[0];
        switch (commandLetter) {
            case CreateCanvasCommand
                    .COMMAND_LETTER:
                return new CreateCanvasCommand(arguments);
            case DrawLineCommand.COMMAND_LETTER:
                return new DrawLineCommand(arguments);
            case DrawRectangleCommand
                    .COMMAND_LETTER:
                return new DrawRectangleCommand(arguments);
            case FillAreaCommand
                    .COMMAND_LETTER:
                return new FillAreaCommand(arguments);
            case QuiteCommand
                    .COMMAND_LETTER:
                return new QuiteCommand(arguments);
        }

        throw new RuntimeException("Unknown command exception: " + input);
    }

}
