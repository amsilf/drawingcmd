package com.alexander.makarov.drawing.command


import spock.lang.Specification
import spock.lang.Timeout

class FillAreaCommandSpecification extends Specification {

    def "I cannot draw on empty canvas" () {
        given:
            def fillAreaArguments = new String[]{ "B", "2", "2", "o" }
            def fillAreaCommand = new FillAreaCommand(fillAreaArguments);
        when:
            def executionResult = fillAreaCommand.execute(null)
        then:
            executionResult.message == "Can not draw on empty canvas! Please, use create canvas command before any drawing."
    }

    def "I need at least two arguments to draw" () {
        given:
            def canvas = new CreateCanvasCommand(new String[]{ "C", "20", "20" }).execute().canvas
            def fillAreaArguments = new String[]{ "B", "50" }
            def fillAreaCommand = new FillAreaCommand(fillAreaArguments);
        when:
            def executionResult = fillAreaCommand.execute(canvas)
        then:
            executionResult.message == "Command must have at least 2 arguments to proceed - (x0, y0), (x1, y1); default colour is 'o'"
    }

    def "Point coordinates have to be expressed as integers" () {
        given:
            def canvas = new CreateCanvasCommand(new String[]{ "C", "20", "20" }).execute().canvas
            def fillAreaArguments = new String[]{ "B", "10.1", "10", "o" }
            def fillAreaCommand = new FillAreaCommand(fillAreaArguments);
        when:
            def executionResult = fillAreaCommand.execute(canvas)
        then:
            executionResult.message == "Coordinates must be expressed as integers"
    }

    def "I cannot draw outside of the canvas" () {
        given:
            def canvas = new CreateCanvasCommand(new String[]{ "C", "20", "20" }).execute().canvas
            def fillAreaArguments = new String[]{ "B", "50", "50", "o" }
            def fillAreaCommand = new FillAreaCommand(fillAreaArguments);
        when:
            def executionResult = fillAreaCommand.execute(canvas)
        then:
            executionResult.message == "Point is outside of the canvas"
    }

    def "I fill the whole canvas"() {
        given:
            def canvas = new CreateCanvasCommand(new String[]{ "C", "10", "10" }).execute().canvas
            def fillAreaArguments = new String[]{ "B", "1", "1", "o" }
            def fillAreaCommand = new FillAreaCommand(fillAreaArguments);
        when:
            def executionResult = fillAreaCommand.execute(canvas)
        then:
            def localCanvas = executionResult.getCanvas()
            for (int i = 1; i < localCanvas.getWidth(); i++) {
                for (int j = 1; j < localCanvas.getHeight(); j++) {
                    localCanvas.getDotColour(i, j) == 'o' as char;
                }
            }
    }

    def "I fill up only one dot" () {
        given:
            def canvas = new CreateCanvasCommand(new String[]{ "C", "1", "1" }).execute().canvas

            def fillAreaArguments = new String[]{ "B", "1", "1", "o" }
            def fillAreaCommand = new FillAreaCommand(fillAreaArguments);
        when:
            def executionResult = fillAreaCommand.execute(canvas)
        then:
            executionResult.canvas.getDotColour(1, 1) == 'o' as char
    }

    def "I am able to repaint the same area" () {
        given:
            def canvas = new CreateCanvasCommand(new String[]{ "C", "1", "1" }).execute().canvas

            def fillAreaArguments = new String[]{ "B", "1", "1", "o" }
            def fillAreaWithOColourCommand = new FillAreaCommand(fillAreaArguments)
        when:
            canvas = fillAreaWithOColourCommand.execute(canvas).canvas
            def fillAreaWithRColourCommand = new FillAreaCommand(new String[] { "B", "1", "1", "r" })
            def executionResult = fillAreaWithRColourCommand.execute(canvas)
        then:
            executionResult.canvas.getDotColour(1, 1) == 'r' as char
    }

    @Timeout(1)
    def "FillArea command performance is good enough" () {
        given:
            def canvas = new CreateCanvasCommand(new String[]{ "C", "100", "100" }).execute().canvas
            def fillAreaArguments = new String[]{ "B", "1", "1", "o" }
            def fillAreaCommand = new FillAreaCommand(fillAreaArguments)
        when:
            def executionResult = fillAreaCommand.execute(canvas)
        then:
            executionResult.isSuccessful()
    }

}