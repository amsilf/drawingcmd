package com.alexander.makarov.drawing.command

import spock.lang.Shared
import spock.lang.Specification

class DrawLineCommandSpecification extends Specification {

    @Shared canvas = new CreateCanvasCommand(new String[]{ "C", "20", "20" }).execute().getCanvas()

    def "I cannot draw on empty canvas" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "2", "6", "2" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(null)
        then:
            executionResult.getMessage() == "Can not draw on empty canvas! Please, use create canvas command before any drawing."
    }

    def "I need at least two parameters to draw a line" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "1", "30" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.message == "Command must have at least 4 arguments to proceed - (x0, y0), (x1, y1), e.g. L 1 2 6 2"
    }

    def "I cannot draw outside of the canvas" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "1", "30", "1" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.message == "Either start or end is outside of canvas borders"
    }

    def "I cannot use non integers to specify a line" () {
        given:
            def drawLineArguments = new String[]{ "L", "1.1", "2", "2", "1" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.message == "Coordinates must be expressed as integers"
    }

    def "Line start cannot be further then end" () {
        given:
            def drawLineArguments = new String[]{ "L", "10", "1", "1", "1" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.message == "Wrong line coordinates - start position is higher then end"
    }

    def "I can draw longest horizontal line" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "1", "20", "1" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.isSuccessful()
            executionResult.getCanvas().getDotColour(1, 1) == 'x' as char
            executionResult.getCanvas().getDotColour(20, 1) == 'x' as char
    }

    def "I can draw longest vertical line" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "1", "1", "20" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.isSuccessful()
            executionResult.getCanvas().getDotColour(1, 1) == 'x' as char
            executionResult.getCanvas().getDotColour(1, 20) == 'x' as char
    }

    def "I cannot draw diagonal line" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "1", "2", "2" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.message == "Currently, only either vertical or horizontal lines are supported"
    }

    def "I can draw a dot" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "1", "1", "1" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments)
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            executionResult.isSuccessful()
            executionResult.getCanvas().getDotColour(1, 1) == 'x' as char
    }

    def "I draw horizontal line from (1, 2) to (6, 2)" () {
        given:
            def drawLineArguments = new String[]{ "L", "1", "2", "6", "2" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments);
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            for (int y=2, x=0; x <= 6; x++) {
                executionResult.getCanvas().getDotColour(x, y) == 'x' as char
            }
    }

    def "I draw vertical line from (6, 3) to (6, 4)" () {
        given:
            def drawLineArguments = new String[]{ "L", "6", "3", "6", "4" }
            def drawLineCommand = new DrawLineCommand(drawLineArguments);
        when:
            def executionResult = drawLineCommand.execute(canvas)
        then:
            for (int x=6, y=3; y <= 4; y++) {
                executionResult.getCanvas().getDotColour(x, y) == 'x' as char
            }
    }

}
