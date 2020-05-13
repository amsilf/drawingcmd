package com.alexander.makarov.drawing.command;

import com.credit.swiss.drawing.dataobjects.Canvas;
import com.credit.swiss.drawing.dataobjects.CommandExecutionResult;

public class DrawLineCommand extends AbstractCommand {

    public static final String COMMAND_LETTER = "L";

    private int x0 = -1;
    private int y0 = -1;

    private int x1 = -1;
    private int y1 = -1;

    private boolean isVertical = false;

    protected DrawLineCommand(String[] args) {
        super(args);
    }

    @Override
    protected String verifyAndParseArguments(Canvas canvas, String[] args) {
        if (args.length > 5) {
            System.err.println("[WARN]: There is more then two arguments in input, but only first two arguments will be used");
        }

        if (canvas == null) {
            return "Can not draw on empty canvas! Please, use create canvas command before any drawing.";
        }

        if (args.length < 5) {
            return "Command must have at least 4 arguments to proceed - (x0, y0), (x1, y1), e.g. L 1 2 6 2";
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
            return "Wrong line coordinates - start position is higher then end";
        }

        if (canvas.isPointOutsideCanvas(x0, y0) || canvas.isPointOutsideCanvas(x1, y1)) {
            return "Either start or end is outside of canvas borders";
        }

        if (y0 == y1) {
            isVertical = false;
            return VERIFIED;
        } else if (x0 == x1) {
            isVertical = true;
            return VERIFIED;
        } else {
            return "Currently, only either vertical or horizontal lines are supported";
        }
    }

    @Override
    protected CommandExecutionResult run(Canvas canvas) {
        if (isVertical) {
            for (int i = y0; i <= y1; i++) {
                canvas.drawDot(x0, i, 'x');
            }
        } else {
            for (int i = x0; i <= x1; i++) {
                canvas.drawDot(i, y0, 'x');
            }
        }
        canvas.drawDot(x1, y1, 'x');

        return new CommandExecutionResult(canvas, "Line has been successfully drawn", true);
    }
}
