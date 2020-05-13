package com.alexander.makarov.drawing.command;

import com.credit.swiss.drawing.dataobjects.Canvas;
import com.credit.swiss.drawing.dataobjects.CommandExecutionResult;

public class CreateCanvasCommand extends AbstractCommand {

    public static final String COMMAND_LETTER = "C";

    private int width = -1;
    private int height = -1;

    public CreateCanvasCommand(String[] args) {
        super(args);
    }

    @Override
    protected String verifyAndParseArguments(Canvas canvas, String[] args) {
        if (args.length > 3) {
            System.err.println("[WARN]: There is more then two arguments in input, but only first two arguments will be used");
        }

        if (args.length < 3) {
            return "Command must have at least 2 arguments to proceed - width and height";
        }

        String strWidth  = args[1];
        String strHeight = args[2];

        try {
            width = toInteger(strWidth);
            height = toInteger(strHeight);
        } catch (Exception exception) {
            return "Coordinates must be expressed as integers";
        }

        if (width <= 0 || height <= 0) {
            return "Arguments must be positive";
        }

        return VERIFIED;
    }

    @Override
    protected CommandExecutionResult run(Canvas canvas) {
        return new CommandExecutionResult(new Canvas(width, height), "Canvas has been successfully created", true);
    }
}
