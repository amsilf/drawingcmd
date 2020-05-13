package com.alexander.makarov.drawing.command;

import com.credit.swiss.drawing.dataobjects.Canvas;
import com.credit.swiss.drawing.dataobjects.CommandExecutionResult;

public class DrawRectangleCommand extends AbstractCommand {

    public static final String COMMAND_LETTER = "R";

    public DrawRectangleCommand(String[] args) {
        super(args);
    }

    private int x0;
    private int y0;

    private int x1;
    private int y1;

    @Override
    protected String verifyAndParseArguments(Canvas canvas, String[] args) {

        if (canvas == null) {
            return "Can not draw on empty canvas! Please, use create canvas command before any drawing.";
        }

        if (args.length > 5) {
            System.err.println("[WARN]: There is more then two arguments in input, but only first two arguments will be used");
        }

        if (args.length < 5) {
            return "Command must have at least 4 arguments to proceed - (x0, y0), (x1, y1), e.g. R 1 1 2 2";
        }

        try {
            x0 = toInteger(args[1]);
            y0 = toInteger(args[2]);
            x1 = toInteger(args[3]);
            y1 = toInteger(args[4]);
        } catch (Exception exception) {
            return "Coordinates must be expressed as integers";
        }

        if (x0 > x1 || y0 > y1) {
            return "Draw rectangle by specifying upper left and bottom right corners";
        }

        if (canvas.isPointOutsideCanvas(x0, y0) || canvas.isPointOutsideCanvas(x1, y1)) {
            return "Either start or end is outside of canvas borders";
        }

        return VERIFIED;
    }

    @Override
    protected CommandExecutionResult run(Canvas canvas) {

        // vertical lines
        for (int i = y0; i <= y1; i++) {
            canvas.drawDot(x0, i, 'x');
            canvas.drawDot(x1, i, 'x');
        }

        // horizontal lines
        for (int i = x0; i <= x1; i++) {
            canvas.drawDot(i, y0, 'x');
            canvas.drawDot(i, y1, 'x');
        }

        return new CommandExecutionResult(canvas, "Rectangle has drawn", true);
    }
}
