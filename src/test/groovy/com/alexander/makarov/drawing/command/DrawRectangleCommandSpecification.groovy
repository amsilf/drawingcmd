package com.alexander.makarov.drawing.command

import spock.lang.Shared
import spock.lang.Specification

class DrawRectangleCommandSpecification extends Specification {

    @Shared canvas = new CreateCanvasCommand(new String[]{ "C", "20", "20" }).execute().getCanvas()

    def "I cannot draw on empty canvas" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "2", "2", "1", "1" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(null)
        then:
            executionResult.message == "Can not draw on empty canvas! Please, use create canvas command before any drawing."
    }

    def "I need at least four parameters to draw a Rectangle" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "2", "2", "1" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(canvas)
        then:
            executionResult.message == "Command must have at least 4 arguments to proceed - (x0, y0), (x1, y1), e.g. R 1 1 2 2"
    }

    def "Command arguments must be integers" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "1.1", "1", "2", "2" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(canvas)
        then:
            executionResult.message == "Coordinates must be expressed as integers"
    }

    def "I cannot draw outside of the canvas" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "1", "1", "50", "50" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(canvas)
        then:
            executionResult.message == "Either start or end is outside of canvas borders"
    }

    def "I can draw largest rectangle" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "1", "1", "20", "20" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(canvas)
        then:
            executionResult.isSuccessful()
            executionResult.getCanvas().getDotColour(1, 1) == 'x' as char
            executionResult.getCanvas().getDotColour(1, 20) == 'x' as char
            executionResult.getCanvas().getDotColour(20, 1) == 'x' as char
            executionResult.getCanvas().getDotColour(20, 20) == 'x' as char
    }

    def "I cannot shuffle command arguments" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "2", "2", "1", "1" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(canvas)
        then:
            executionResult.message == "Draw rectangle by specifying upper left and bottom right corners"
    }

    def "I can draw a dot" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "14", "1", "14", "1" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(canvas)
        then:
            executionResult.isSuccessful()
            executionResult.canvas.getDotColour(14, 1) == 'x' as char
    }

    def "I draw rectangle from (14, 1) to (18, 3)" () {
        given:
            def drawRectangleArguments = new String[]{ "R", "14", "1", "18", "3" }
            def drawRectangleCommand = new DrawRectangleCommand(drawRectangleArguments);
        when:
            def executionResult = drawRectangleCommand.execute(canvas)
        then:
            // check horizontal line
            def currCanvas = executionResult.getCanvas()
            for (int x = 14; x <= 18; x++) {
                currCanvas.getDotColour(x, 1) == 'x' as char
                currCanvas.getDotColour(x, 3) == 'x' as char
            }

            // check vertical line
            for (int y = 1; y <= 3; y++) {
                currCanvas.getDotColour(14, y) == 'x' as char
                currCanvas.getDotColour(18, y) == 'x' as char
            }
    }


}
