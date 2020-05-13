package com.alexander.makarov.drawing.command;

import com.credit.swiss.drawing.dataobjects.Canvas;
import com.credit.swiss.drawing.dataobjects.CommandExecutionResult;
import com.credit.swiss.drawing.dataobjects.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FillAreaCommand extends AbstractCommand {

    public static final String COMMAND_LETTER = "B";

    public FillAreaCommand(String[] args) {
        super(args);
    }

    private int x0;
    private int y0;

    private char colour = 'o';

    @Override
    protected String verifyAndParseArguments(Canvas canvas, String[] args) {

        if (canvas == null) {
            return "Can not draw on empty canvas! Please, use create canvas command before any drawing.";
        }

        if (args.length > 4) {
            System.err.println("[WARN]: There is more then two arguments in input, but only first two arguments will be used");
        }

        if (args.length < 3) {
            return "Command must have at least 2 arguments to proceed - (x0, y0), (x1, y1); default colour is 'o'";
        }

        try {
            x0 = toInteger(args[1]);
            y0 = toInteger(args[2]);
        } catch (Exception exception) {
            return "Coordinates must be expressed as integers";
        }

        if (canvas.isPointOutsideCanvas(x0, y0)) {
            return "Point is outside of the canvas";
        }

        if (canvas.getDotColour(x0, y0) == 'x' || !canvas.isDrawingNotHitBorder(x0, y0)) {
            return "Space isn't empty";
        }

        if (args[3].toCharArray().length == 1) {
            colour = args[3].toCharArray()[0];
        } else {
            return "Color must be expressed as one character";
        }

        return VERIFIED;
    }

    @Override
    protected CommandExecutionResult run(Canvas canvas) {

        if (canvas.isDotAvailableForColouring(x0, y0)) {
            canvas.drawDot(x0, y0, colour);
            Queue<Point> queue = new LinkedList<>();
            queue.add(new Point(x0, y0));

            colourNeighbours(queue, colour, canvas);
        }

        return new CommandExecutionResult(canvas, "Area has been filled", true);
    }

    private void colourNeighbours(Queue<Point> queue, char colour, Canvas canvas) {

        while(!queue.isEmpty()) {
            Point point = queue.poll();

            int x = point.getX();
            int y = point.getY();

            List<Point> neighbours = new ArrayList<Point>(){{
                Point wNeighbour = new Point(x - 1, y);
                Point eNeighbour = new Point(x + 1, y);
                Point nNeighbour = new Point(x, y - 1);
                Point sNeighbour = new Point(x, y + 1);

                Point nwNeighbour = new Point(x - 1, y - 1);
                Point neNeighbour = new Point(x - 1, y + 1);

                Point swNeighbour = new Point(x + 1, y - 1);
                Point seNeighbour = new Point(x + 1, y + 1);

                add(wNeighbour); add(eNeighbour);
                add(nNeighbour); add(sNeighbour);
                add(nwNeighbour); add(neNeighbour);
                add(swNeighbour); add(seNeighbour);
            }};

            neighbours.forEach(n -> addToQueueIfEligible(queue, canvas, n, colour));
            boolean b = canvas.drawDotIfSpaceIsEmpty(x, y, colour);
        }

    }

    private void addToQueueIfEligible(Queue<Point> queue, Canvas canvas, Point point, char colour) {
        if ( !canvas.isPointOutsideCanvas(point.getX(), point.getY())
                && canvas.isDotAvailableForColouring(point.getX(), point.getY())
                && canvas.isDrawingNotHitBorder(point.getX(), point.getY())
                && !queue.contains(point)
                && canvas.getDotColour(point.getX(), point.getY()) != colour) {
            queue.add(point);
        }
    }

}
